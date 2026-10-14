package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.the_last_sword.client.layer.ThePastShadowOfTheQueenItemLayer;
import net.the_last_sword.client.model.ThePastShadowOfTheQueenModel;
import net.the_last_sword.entity.ThePastShadowOfTheQueenEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.3: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer); GeckoLib
//creates the state itself (createRenderState is final), so the entity is parked on it as a GeckoLib data ticket
public class ThePastShadowOfTheQueenRenderer<R extends EntityRenderState & GeoRenderState>
        extends GeoEntityRenderer<ThePastShadowOfTheQueenEntity, R> {
    private static final DataTicket<ThePastShadowOfTheQueenEntity> QUEEN_ENTITY =
            DataTicket.create("the_last_sword_queen_entity", ThePastShadowOfTheQueenEntity.class);

    public ThePastShadowOfTheQueenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThePastShadowOfTheQueenModel());
        withRenderLayer(new ThePastShadowOfTheQueenItemLayer<>(this));
        this.shadowRadius = 0.5f;
    }

    @Override
    public void submit(R queen, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        ThePastShadowOfTheQueenEntity entity = queen.getGeckolibData(QUEEN_ENTITY);
        float partialTick = queen.partialTick;
        super.submit(queen, pose, collector, camera);
        MultiBufferSource buffers = Minecraft.getInstance().renderBuffers().bufferSource();
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
    public void preRender(R queen, PoseStack poseStack, BakedGeoModel model, SubmitNodeCollector collector, CameraRenderState camera, int packedLight, int packedOverlay, int colour) {
        float scale = 57.5F / 44.5F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(queen, poseStack, model, collector, camera, packedLight, packedOverlay, colour);
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

    @Override
    public void extractRenderState(ThePastShadowOfTheQueenEntity entity, R queen, float partialTick) {
        super.extractRenderState(entity, queen, partialTick);
        queen.addGeckolibData(QUEEN_ENTITY, entity);
    }
}
