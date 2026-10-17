package net.the_last_sword.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.the_last_sword.mv.ShaderInstance;
import net.minecraft.resources.Identifier;
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
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
public class ModShaders {

    //注册着色器
    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        //注册 The Last End 着色器
        ShaderInstance shader = TheLastEndShaderInstance.create(
            event.getResourceProvider(),
            Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "the_last_end"),
            DefaultVertexFormat.BLOCK
        );
        event.registerShader(shader, TheLastEndEffect::setShader);
        event.registerShader(new ZeroPointEngineProgram(event.getResourceProvider()), ZeroPointEngineShader::setShader);
    }

    // 1.21.2+: EcaShaderInstance no longer extends the fork's ShaderInstance, so ZeroPointEngineShader's apply() lives here
    private static final class ZeroPointEngineProgram extends ShaderInstance {
        private static final long ANIMATION_START_NANOS = System.nanoTime();
        private static final long GLOW_CYCLE_NANOS = 6_000_000_000L;
        private final net.the_last_sword.mv.Uniform localUvMin;
        private final net.the_last_sword.mv.Uniform localUvScale;
        private final net.the_last_sword.mv.Uniform glowPhase;

        ZeroPointEngineProgram(net.minecraft.server.packs.resources.ResourceProvider provider) throws IOException {
            super(provider, Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "zero_point_engine"),
                    DefaultVertexFormat.NEW_ENTITY);
            localUvMin = getUniform("LocalUvMin");
            localUvScale = getUniform("LocalUvScale");
            glowPhase = getUniform("GlowPhase");
        }

        // 1.21.5: ECA's applyLocalUvBoundsUniforms takes ECA's own layer Uniform (net.eca.mv.Uniform), not this
        // fork's, so it cannot be handed these. It only copies the draw's local UV bounds that ECA keeps in four
        // static fields; read those (the authors' library fields, unchanged since 1.21.1) and set the same values.
        private static final java.lang.reflect.Field[] LOCAL_UV = localUvFields();

        private static java.lang.reflect.Field[] localUvFields() {
            try {
                java.lang.reflect.Field[] f = new java.lang.reflect.Field[4];
                String[] names = {"localUvMinU", "localUvMinV", "localUvScaleU", "localUvScaleV"};
                for (int i = 0; i < 4; i++) {
                    f[i] = net.eca.client.render.shader.EcaShaderInstance.class.getDeclaredField(names[i]);
                    f[i].setAccessible(true);
                }
                return f;
            } catch (ReflectiveOperationException | RuntimeException e) {
                org.slf4j.LoggerFactory.getLogger(ModShaders.class).warn("the_last_sword: ECA local UV bounds unavailable", e);
                return null;
            }
        }

        private static void applyLocalUvBounds(net.the_last_sword.mv.Uniform min, net.the_last_sword.mv.Uniform scale) {
            if (LOCAL_UV == null) {
                return;
            }
            try {
                if (min != null) {
                    min.set(LOCAL_UV[0].getFloat(null), LOCAL_UV[1].getFloat(null));
                }
                if (scale != null) {
                    scale.set(LOCAL_UV[2].getFloat(null), LOCAL_UV[3].getFloat(null));
                }
            } catch (IllegalAccessException ignored) {
                // as the authors' helper: a failed upload keeps the previous values
            }
        }

        @Override
        public void apply() {
            applyLocalUvBounds(localUvMin, localUvScale);
            if (glowPhase != null) {
                glowPhase.set((float) ((System.nanoTime() - ANIMATION_START_NANOS) % GLOW_CYCLE_NANOS)
                        / GLOW_CYCLE_NANOS);
            }
            super.apply();
        }
    }
}
