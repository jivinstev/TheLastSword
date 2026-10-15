package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.the_last_sword.client.layer.ThePastShadowOfTheQueenItemLayer;
import net.the_last_sword.client.model.ThePastShadowOfTheQueenModel;
import net.the_last_sword.entity.ThePastShadowOfTheQueenEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by QueenRenderState & GeoRenderState; GeckoLib's mixin makes every
//EntityRenderState a GeoRenderState at runtime (5.1 ships no interface injection for javac)
public class ThePastShadowOfTheQueenRenderer<R extends ThePastShadowOfTheQueenRenderer.QueenRenderState & GeoRenderState>
        extends GeoEntityRenderer<ThePastShadowOfTheQueenEntity, R> {
    public ThePastShadowOfTheQueenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThePastShadowOfTheQueenModel());
        addRenderLayer(new ThePastShadowOfTheQueenItemLayer<>(this));
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(R queen, PoseStack pose, MultiBufferSource buffers, int light) {
        ThePastShadowOfTheQueenEntity entity = queen.entity;
        float partialTick = queen.partialTick;
        super.render(queen, pose, buffers, light);
        if (entity.getSummonTick() >= 0) {
            QueenBlinkRenderUtil.summonRift(pose, buffers, entity.getSummonTick() + partialTick,
                    entity.getSummonYaw());
        }
        if (entity.getBlinkTick() >= 0) {
            float bodyYaw = Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
            QueenBlinkRenderUtil.rift(pose, buffers, entity.getBlinkTick() + partialTick, bodyYaw);
        }
    }

    // 1.21.1's ThePastShadowOfTheQueenEntity.getBoundingBoxForCulling: 1.21.2 asks the renderer instead
    @Override
    protected AABB getBoundingBoxForCulling(ThePastShadowOfTheQueenEntity entity) {
        AABB box = super.getBoundingBoxForCulling(entity);
        return entity.getSummonTick() >= 0 ? box.inflate(3, 6, 3) : box;
    }

    @Override
    public RenderType getRenderType(R queen, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(queen));
    }

    @Override
    public void preRender(R queen, PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int colour) {
        float scale = 57.5F / 44.5F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(queen, poseStack, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, colour);
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    //受伤变红效果
    @Override
    public int getPackedOverlay(ThePastShadowOfTheQueenEntity entity, Void relatedObject, float u, float partialTick) {
        return OverlayTexture.pack(0.0F, entity.hurtTime > 0 || entity.deathTime > 0);
    }

    //GeckoLib 5.1 builds its state in createBaseRenderState (createRenderState() is never asked)
    @Override
    @SuppressWarnings("unchecked")
    protected R createBaseRenderState(ThePastShadowOfTheQueenEntity entity) {
        return (R) new QueenRenderState();
    }

    @Override
    public void extractRenderState(ThePastShadowOfTheQueenEntity entity, R queen, float partialTick) {
        queen.entity = entity;
        queen.partialTick = partialTick;
        super.extractRenderState(entity, queen, partialTick);
    }

    public static class QueenRenderState extends LivingEntityRenderState {
        public ThePastShadowOfTheQueenEntity entity;
        public float partialTick;
    }
}
