package net.the_last_sword.client.shader;

import net.eca.mv.Uniform;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import net.eca.client.render.shader.EcaShaderInstance;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.the_last_sword.mv.ShaderInstance;
import net.minecraft.resources.Identifier;
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

    // 1.21.11: RenderStateShard is gone; 1.21.1's VIEW_OFFSET_Z_LAYERING shard (push the model-view, nudge it towards the
    // camera, pop after the draw) is vanilla's LayeringTransform.VIEW_OFFSET_Z_LAYERING modifier, applied around the draw
    // as RenderType.draw does, since LegacyGl draws with RenderSystem's model-view matrix
    private static final net.the_last_sword.mv.LegacyRenderType.Shard VIEW_OFFSET_Z_LAYERING =
            new net.the_last_sword.mv.LegacyRenderType.Shard() {
                @Override
                public void setupRenderState() {
                    org.joml.Matrix4fStack modelView = com.mojang.blaze3d.systems.RenderSystem.getModelViewStack();
                    modelView.pushMatrix();
                    java.util.function.Consumer<org.joml.Matrix4f> modifier =
                            net.minecraft.client.renderer.rendertype.LayeringTransform.VIEW_OFFSET_Z_LAYERING.getModifier();
                    if (modifier != null) {
                        modifier.accept(modelView);
                    }
                }

                @Override
                public void clearRenderState() {
                    com.mojang.blaze3d.systems.RenderSystem.getModelViewStack().popMatrix();
                }
            };

    public static final RenderType ITEM = net.the_last_sword.mv.LegacyRenderType.create(
            "zero_point_engine_glow", DefaultVertexFormat.ENTITY, com.mojang.renderpearl.api.pipeline.PrimitiveTopology.QUADS,
            256, false, false, net.the_last_sword.mv.LegacyRenderType.state()
                    .setShaderState(new net.the_last_sword.mv.ShaderShard(() -> shader))
                    .setTextureState(net.the_last_sword.mv.LegacyRenderType.BLOCK_SHEET_MIPPED)
                    .setTransparencyState(net.the_last_sword.mv.LegacyRenderType.TRANSLUCENT_TRANSPARENCY)
                    .setDepthTestState(net.the_last_sword.mv.LegacyRenderType.LEQUAL_DEPTH_TEST)
                    .setCullState(net.the_last_sword.mv.LegacyRenderType.NO_CULL)
                    .setWriteMaskState(net.the_last_sword.mv.LegacyRenderType.COLOR_WRITE)
                    .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                    .createCompositeState(false));

    public ZeroPointEngineShader(ResourceProvider provider) throws IOException {
        super(provider, Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "zero_point_engine"),
                DefaultVertexFormat.ENTITY);
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
