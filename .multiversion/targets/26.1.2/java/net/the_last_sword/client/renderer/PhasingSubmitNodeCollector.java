package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.MovingBlockRenderState;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.HitboxRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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
                              int light, double distanceSq, CameraRenderState cameraState) {
        this.delegate.submitNameTag(poseStack, pos, offset, name, seeThrough, light, distanceSq, cameraState);
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
    public <S> void submitModel(Model<? super S> model, S state, PoseStack poseStack, RenderType type, int light,
                                int overlay, int tint, @Nullable TextureAtlasSprite sprite, int outline,
                                @Nullable ModelFeatureRenderer.CrumblingOverlay crumbling) {
        if (phased(type)) {
            this.delegate.submitModel(model, state, poseStack, PhasingBufferSource.translucentType(type), light, overlay,
                    halfAlpha(tint), sprite, outline, crumbling);
        } else {
            this.delegate.submitModel(model, state, poseStack, type, light, overlay, tint, sprite, outline, crumbling);
        }
    }

    @Override
    public void submitModelPart(ModelPart part, PoseStack poseStack, RenderType type, int light, int overlay,
                                @Nullable TextureAtlasSprite sprite, boolean sheeted, boolean foil, int tint,
                                @Nullable ModelFeatureRenderer.CrumblingOverlay crumbling, int outline) {
        if (phased(type)) {
            this.delegate.submitModelPart(part, poseStack, PhasingBufferSource.translucentType(type), light, overlay, sprite,
                    sheeted, foil, halfAlpha(tint), crumbling, outline);
        } else {
            this.delegate.submitModelPart(part, poseStack, type, light, overlay, sprite, sheeted, foil, tint, crumbling, outline);
        }
    }

    @Override
    public void submitBreakingBlockModel(PoseStack poseStack, net.minecraft.client.renderer.block.dispatch.BlockStateModel model,
                                         long seed, int progress) {
        this.delegate.submitBreakingBlockModel(poseStack, model, seed, progress);
    }

    @Override
    public void submitMovingBlock(PoseStack poseStack, MovingBlockRenderState state) {
        this.delegate.submitMovingBlock(poseStack, state);
    }

    @Override
    public void submitBlockModel(PoseStack poseStack, RenderType type, List<BlockStateModelPart> parts, int[] tints,
                                 int light, int overlay, int outline) {
        this.delegate.submitBlockModel(poseStack, type, parts, tints, light, overlay, outline);
    }

    @Override
    public void submitItem(PoseStack poseStack, ItemDisplayContext context, int light, int overlay, int outline,
                           int[] tints, List<BakedQuad> quads, ItemStackRenderState.FoilType foil) {
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
    public void submitParticleGroup(SubmitNodeCollector.ParticleGroupRenderer renderer) {
        this.delegate.submitParticleGroup(renderer);
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
