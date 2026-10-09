package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderType.CompositeRenderType;
import net.minecraft.resources.ResourceLocation;

public final class PhasingBufferSource implements MultiBufferSource {
    private final MultiBufferSource delegate;

    private PhasingBufferSource(MultiBufferSource delegate) {
        this.delegate = delegate;
    }

    public static MultiBufferSource wrap(MultiBufferSource source) {
        return source instanceof PhasingBufferSource ? source : new PhasingBufferSource(source);
    }

    @Override
    public VertexConsumer getBuffer(RenderType type) {
        // 名牌、线框与附魔光效使用不同的顶点格式，保留其原有绘制方式。
        if (type.format() != DefaultVertexFormat.NEW_ENTITY) {
            return delegate.getBuffer(type);
        }
        return new PhasingVertexConsumer(delegate, translucentType(type));
    }

    private static RenderType translucentType(RenderType type) {
        if (!(type instanceof CompositeRenderType composite)) {
            return type;
        }
        ResourceLocation texture = composite.state.textureState.cutoutTexture().orElse(null);
        if (texture == null) {
            return type;
        }
        if (type == RenderType.eyes(texture)) {
            return RenderType.entityTranslucentEmissive(texture);
        }
        // 仅替换标准不透明材质，保留自定义着色器及其渲染状态。
        if (type == RenderType.entitySolid(texture)
                || type == RenderType.entityCutout(texture)
                || type == RenderType.entityCutoutNoCull(texture, true)
                || type == RenderType.entityCutoutNoCull(texture, false)
                || type == RenderType.entityCutoutNoCullZOffset(texture, true)
                || type == RenderType.entityCutoutNoCullZOffset(texture, false)
                || type == RenderType.armorCutoutNoCull(texture)
                || type == RenderType.entitySmoothCutout(texture)
                || type == RenderType.entityDecal(texture)) {
            return RenderType.entityTranslucent(texture);
        }
        return type;
    }

    /**
     * The translucent types come from the shared buffer: asking it for another type closes the previous type's
     * builder. A renderer (GeckoLib) keeps this consumer while a layer asks for its own type, so on 1.21 -- where a
     * closed builder throws "Not building!" instead of quietly taking the vertex into the next batch, as on 1.20 --
     * the next vertex crashes the client. Fetch the buffer again at the start of every vertex: the same type is
     * the same builder (cheap); after another type it is a fresh one, and the whole vertex goes to the right batch.
     */
    private static final class PhasingVertexConsumer implements VertexConsumer {
        private final MultiBufferSource source;
        private final RenderType type;
        private VertexConsumer delegate;

        PhasingVertexConsumer(MultiBufferSource source, RenderType type) {
            this.source = source;
            this.type = type;
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            delegate = source.getBuffer(type);
            delegate.addVertex(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            delegate.setColor(red, green, blue, alpha / 2);
            return this;
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            delegate.setUv(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            delegate.setUv1(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            delegate.setUv2(u, v);
            return this;
        }

        @Override
        public VertexConsumer setNormal(float x, float y, float z) {
            delegate.setNormal(x, y, z);
            return this;
        }

    }
}
