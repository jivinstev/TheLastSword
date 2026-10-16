package net.the_last_sword.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.the_last_sword.mv.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.the_last_sword.mv.RegisterShadersEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.shader.TheLastEndEffect;
import net.the_last_sword.client.shader.TheLastEndShaderInstance;
import net.the_last_sword.client.shader.ZeroPointEngineShader;

import java.io.IOException;

//着色器注册
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModShaders {

    //注册着色器
    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        //注册 The Last End 着色器
        ShaderInstance shader = TheLastEndShaderInstance.create(
            event.getResourceProvider(),
            ResourceLocation.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "the_last_end"),
            DefaultVertexFormat.BLOCK
        );
        event.registerShader(shader, TheLastEndEffect::setShader);
        event.registerShader(new ZeroPointEngineProgram(event.getResourceProvider()), ZeroPointEngineShader::setShader);
    }

    // 1.21.2+: EcaShaderInstance no longer extends the fork's ShaderInstance, so ZeroPointEngineShader's apply() lives here
    private static final class ZeroPointEngineProgram extends ShaderInstance {
        private static final long ANIMATION_START_NANOS = System.nanoTime();
        private static final long GLOW_CYCLE_NANOS = 6_000_000_000L;
        private final com.mojang.blaze3d.shaders.Uniform localUvMin;
        private final com.mojang.blaze3d.shaders.Uniform localUvScale;
        private final com.mojang.blaze3d.shaders.Uniform glowPhase;

        ZeroPointEngineProgram(net.minecraft.server.packs.resources.ResourceProvider provider) throws IOException {
            super(provider, ResourceLocation.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "zero_point_engine"),
                    DefaultVertexFormat.NEW_ENTITY);
            localUvMin = getUniform("LocalUvMin");
            localUvScale = getUniform("LocalUvScale");
            glowPhase = getUniform("GlowPhase");
        }

        @Override
        public void apply() {
            net.eca.client.render.shader.EcaShaderInstance.applyLocalUvBoundsUniforms(localUvMin, localUvScale);
            if (glowPhase != null) {
                glowPhase.set((float) ((System.nanoTime() - ANIMATION_START_NANOS) % GLOW_CYCLE_NANOS)
                        / GLOW_CYCLE_NANOS);
            }
            super.apply();
        }
    }
}
