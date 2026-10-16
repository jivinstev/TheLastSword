package net.the_last_sword.client.shader;

import net.eca.mv.Uniform;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.eca.client.render.shader.EcaShaderInstance;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.the_last_sword.mv.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.the_last_sword.TheLastSwordMod;

import java.io.IOException;

public final class ZeroPointEngineShader extends EcaShaderInstance {
    private static final long ANIMATION_START_NANOS = System.nanoTime();
    private static final long GLOW_CYCLE_NANOS = 6_000_000_000L;
    private static ShaderInstance shader;
    private final Uniform localUvMin;
    private final Uniform localUvScale;
    private final Uniform glowPhase;

    public static final RenderType ITEM = net.the_last_sword.mv.LegacyRenderType.create(
            "zero_point_engine_glow", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS,
            256, false, false, net.the_last_sword.mv.LegacyRenderType.state()
                    .setShaderState(new net.the_last_sword.mv.ShaderShard(() -> shader))
                    .setTextureState(RenderStateShard.BLOCK_SHEET_MIPPED)
                    .setTransparencyState(net.the_last_sword.mv.LegacyRenderType.TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(net.the_last_sword.mv.LegacyRenderType.LEQUAL_DEPTH_TEST)
                    .setCullState(net.the_last_sword.mv.LegacyRenderType.NO_CULL)
                    .setWriteMaskState(net.the_last_sword.mv.LegacyRenderType.COLOR_WRITE)
                    .setLayeringState(RenderStateShard.VIEW_OFFSET_Z_LAYERING)
                    .createCompositeState(false));

    public ZeroPointEngineShader(ResourceProvider provider) throws IOException {
        super(provider, ResourceLocation.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "zero_point_engine"),
                DefaultVertexFormat.NEW_ENTITY);
        localUvMin = getUniform("LocalUvMin");
        localUvScale = getUniform("LocalUvScale");
        glowPhase = getUniform("GlowPhase");
    }

    public static void setShader(ShaderInstance instance) {
        shader = instance;
    }

    @Override
    public void apply() {
        // 图集中的位置会在资源重载后变化，始终使用当前绘制批次的局部坐标。
        applyLocalUvBoundsUniforms(localUvMin, localUvScale);
        if (glowPhase != null) {
            // 单调时间让界面中的光晕持续流动，循环归一化避免长时间运行后精度下降。
            glowPhase.set((float) ((System.nanoTime() - ANIMATION_START_NANOS) % GLOW_CYCLE_NANOS)
                    / GLOW_CYCLE_NANOS);
        }
        super.apply();
    }
}
