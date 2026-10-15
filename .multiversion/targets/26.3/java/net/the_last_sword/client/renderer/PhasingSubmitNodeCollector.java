package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.resources.model.geometry.ItemQuads;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HitboxRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.gizmos.DrawableGizmoPrimitives;
import net.minecraft.client.renderer.state.level.QuadParticleRenderState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.UvMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

/**
 * 1.21.9+: entities no longer draw into a MultiBufferSource; they SUBMIT models and geometry to a SubmitNodeCollector that
 * is drawn later. This is PhasingBufferSource for that pipeline, installed by EntityRenderDispatcherMixin for a phasing
 * entity: every entity-format (NEW_ENTITY) model, model part and custom geometry gets PhasingBufferSource's translucent
 * render type and half its alpha (the tint colour's alpha for models, each vertex's for custom geometry, as
 * PhasingBufferSource halved each vertex). Name tags, shadows, hitboxes, flames, text and items pass through, as they
 * did there (other vertex formats, or not entity render types).
 */
public class PhasingSubmitNodeCollector implements SubmitNodeCollector {
    private final OrderedSubmitNodeCollector delegate;
    @Nullable
    private final SubmitNodeCollector root;

    private PhasingSubmitNodeCollector(OrderedSubmitNodeCollector delegate, @Nullable SubmitNodeCollector root) {
        this.delegate = delegate;
        this.root = root;
    }

    public static SubmitNodeCollector wrap(SubmitNodeCollector collector) {
        return collector instanceof PhasingSubmitNodeCollector ? collector : new PhasingSubmitNodeCollector(collector, collector);
    }

    private static boolean phased(RenderType type) {
        return type.format() == DefaultVertexFormat.ENTITY;
    }

    private static int halfAlpha(int argb) {
        return (((argb >>> 24) / 2) << 24) | (argb & 0x00FFFFFF);
    }

    @Override
    public OrderedSubmitNodeCollector order(int order) {
        SubmitNodeCollector base = this.root != null ? this.root : (SubmitNodeCollector) this.delegate;
        return new PhasingSubmitNodeCollector(base.order(order), base);
    }

    @Override
    public void submitShadow(PoseStack poseStack, float radius, List<EntityRenderState.ShadowPiece> pieces) {
        this.delegate.submitShadow(poseStack, radius, pieces);
    }

    @Override
    public void submitNameTag(PoseStack poseStack, @Nullable Vec3 pos, int offset, Component name, boolean seeThrough,
                              int light, CameraRenderState cameraState) {
        this.delegate.submitNameTag(poseStack, pos, offset, name, seeThrough, light, cameraState);
    }

    @Override
    public void submitText(PoseStack poseStack, float x, float y, FormattedCharSequence text, boolean shadow,
                           Font.DisplayMode mode, int light, int color, int background, int outline) {
        this.delegate.submitText(poseStack, x, y, text, shadow, mode, light, color, background, outline);
    }

    @Override
    public void submitFlame(PoseStack poseStack, EntityRenderState state, Quaternionf rotation) {
        this.delegate.submitFlame(poseStack, state, rotation);
    }

    @Override
    public void submitLeash(PoseStack poseStack, EntityRenderState.LeashState leash) {
        this.delegate.submitLeash(poseStack, leash);
    }

    @Override
    public void submitTextBackground(PoseStack poseStack, float x0, float y0, float x1, float y1, int color,
                                     Font.DisplayMode mode, int light) {
        this.delegate.submitTextBackground(poseStack, x0, y0, x1, y1, color, mode, light);
    }

    // 26.3: submitModelPart is a default that routes through submitModel, so model parts are phased there.
    @Override
    public <S> void submitModel(Model<? super S> model, S state, PoseStack poseStack, RenderType type, int light,
                                int overlay, int tint, @Nullable UvMapping uvMapping, int outline) {
        if (phased(type)) {
            this.delegate.submitModel(model, state, poseStack, PhasingBufferSource.translucentType(type), light, overlay,
                    halfAlpha(tint), uvMapping, outline);
        } else {
            this.delegate.submitModel(model, state, poseStack, type, light, overlay, tint, uvMapping, outline);
        }
    }

    @Override
    public <S> void submitCrumblingOverlay(Model<? super S> model, S state, PoseStack poseStack, RenderType type,
                                           int light, int overlay, int tint,
                                           ModelFeatureRenderer.CrumblingOverlay crumbling) {
        if (phased(type)) {
            this.delegate.submitCrumblingOverlay(model, state, poseStack, PhasingBufferSource.translucentType(type), light,
                    overlay, halfAlpha(tint), crumbling);
        } else {
            this.delegate.submitCrumblingOverlay(model, state, poseStack, type, light, overlay, tint, crumbling);
        }
    }

    @Override
    public void submitShapeOutline(PoseStack poseStack, VoxelShape shape, RenderType type, int color, float width,
                                   boolean afterTerrain) {
        this.delegate.submitShapeOutline(poseStack, shape, type, color, width, afterTerrain);
    }

    @Override
    public void submitBreakingBlockModel(PoseStack poseStack, List<BlockStateModelPart> parts, int progress,
                                         boolean isBlockTranslucent) {
        this.delegate.submitBreakingBlockModel(poseStack, parts, progress, isBlockTranslucent);
    }

    @Override
    public void submitMovingBlock(PoseStack poseStack, MovingBlockRenderState state, int light) {
        this.delegate.submitMovingBlock(poseStack, state, light);
    }

    @Override
    public void submitBlockModel(PoseStack poseStack, RenderType type, List<BlockStateModelPart> parts, int[] tints,
                                 int light, int overlay, int outline) {
        this.delegate.submitBlockModel(poseStack, type, parts, tints, light, overlay, outline);
    }

    @Override
    public void submitItem(PoseStack poseStack, ItemDisplayContext context, int light, int overlay, int outline,
                           int[] tints, ItemQuads quads, ItemStackRenderState.FoilType foil) {
        this.delegate.submitItem(poseStack, context, light, overlay, outline, tints, quads, foil);
    }

    @Override
    public void submitCustomGeometry(PoseStack poseStack, RenderType type, SubmitNodeCollector.CustomGeometryRenderer renderer) {
        if (!phased(type)) {
            this.delegate.submitCustomGeometry(poseStack, type, renderer);
            return;
        }
        this.delegate.submitCustomGeometry(poseStack, PhasingBufferSource.translucentType(type),
                (pose, vertices) -> renderer.render(pose, new HalfAlpha(vertices)));
    }

    @Override
    public void submitQuadParticleGroup(QuadParticleRenderState particles) {
        this.delegate.submitQuadParticleGroup(particles);
    }

    @Override
    public void submitGizmoPrimitives(DrawableGizmoPrimitives.Group group, CameraRenderState cameraState, boolean flag) {
        this.delegate.submitGizmoPrimitives(group, cameraState, flag);
    }

    /** Halves every vertex's alpha, as PhasingBufferSource's consumer did. */
    private static final class HalfAlpha implements VertexConsumer {
        private final VertexConsumer delegate;

        HalfAlpha(VertexConsumer delegate) {
            this.delegate = delegate;
        }

        @Override
        public VertexConsumer addVertex(float x, float y, float z) {
            this.delegate.addVertex(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setColor(int red, int green, int blue, int alpha) {
            this.delegate.setColor(red, green, blue, alpha / 2);
            return this;
        }

        @Override
        public VertexConsumer setColor(int argb) {
            int a = (argb >>> 24) / 2;
            this.delegate.setColor((a << 24) | (argb & 0x00FFFFFF));
            return this;
        }

        @Override
        public VertexConsumer setUv(float u, float v) {
            this.delegate.setUv(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv1(int u, int v) {
            this.delegate.setUv1(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv2(int u, int v) {
            this.delegate.setUv2(u, v);
            return this;
        }

        @Override
        public VertexConsumer setUv3(float u, float v) {
            this.delegate.setUv3(u, v);
            return this;
        }

        @Override
        public VertexConsumer setNormal(float x, float y, float z) {
            this.delegate.setNormal(x, y, z);
            return this;
        }

        @Override
        public VertexConsumer setLineWidth(float width) {
            this.delegate.setLineWidth(width);
            return this;
        }
    }
}
