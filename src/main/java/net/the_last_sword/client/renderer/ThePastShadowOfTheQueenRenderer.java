package net.the_last_sword.client.renderer;

import net.the_last_sword.client.JustifiedDefenceFlash;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
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

public class ThePastShadowOfTheQueenRenderer extends GeoEntityRenderer<ThePastShadowOfTheQueenEntity> {
    public ThePastShadowOfTheQueenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThePastShadowOfTheQueenModel());
        addRenderLayer(new ThePastShadowOfTheQueenItemLayer(this));
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(EntityRenderState state, PoseStack pose, MultiBufferSource buffers, int light) {
        QueenRenderState queen = (QueenRenderState) state;
        ThePastShadowOfTheQueenEntity entity = queen.entity;
        float partialTick = queen.partialTick;
        super.render(state, pose, buffers, light);
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
    public RenderType getRenderType(ThePastShadowOfTheQueenEntity animatable, ResourceLocation texture, MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, ThePastShadowOfTheQueenEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        float scale = 57.5F / 44.5F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, colour);
    }

    @Override
    protected float getDeathMaxRotation(ThePastShadowOfTheQueenEntity entityLivingBaseIn, float partialTick) {
        return 0.0F;
    }

    //受伤变红效果
    @Override
    public int getPackedOverlay(ThePastShadowOfTheQueenEntity entity, float u, float partialTick) {
        //1.21.4: a GeckoLib renderer never reaches LivingEntityRenderer.getOverlayCoords, where the Justified
        //Defence white flash is mixed in (LivingEntityRendererMixin); apply it here, as GuardianOfSealedSpireRenderer does
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }

    @Override
    public QueenRenderState createRenderState() {
        return new QueenRenderState();
    }

    @Override
    public void extractRenderState(ThePastShadowOfTheQueenEntity entity, EntityRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        QueenRenderState queen = (QueenRenderState) state;
        queen.entity = entity;
        queen.partialTick = partialTick;
    }

    public static class QueenRenderState extends LivingEntityRenderState {
        public ThePastShadowOfTheQueenEntity entity;
        public float partialTick;
    }
}
