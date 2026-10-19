package net.the_last_sword.client.renderer;

import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.constant.DataTickets;
import net.the_last_sword.client.JustifiedDefenceFlash;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.the_last_sword.client.layer.ThePastShadowOfTheQueenItemLayer;
import net.the_last_sword.client.model.ThePastShadowOfTheQueenModel;
import net.the_last_sword.entity.ThePastShadowOfTheQueenEntity;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.3: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer); GeckoLib
//creates the state itself (createRenderState is final), so the entity is parked on it as a GeckoLib data ticket
public class ThePastShadowOfTheQueenRenderer<R extends EntityRenderState & GeoRenderState>
        extends GeoEntityRenderer<ThePastShadowOfTheQueenEntity, R> {
    //提取阶段记录裂隙所需的数值（渲染状态不再持有实体）; a negative age means no rift
    private static final DataTicket<Float> SUMMON_AGE = DataTicket.create("the_last_sword_queen_summon_age", Float.class);
    private static final DataTicket<Float> SUMMON_YAW = DataTicket.create("the_last_sword_queen_summon_yaw", Float.class);
    private static final DataTicket<Float> BLINK_AGE = DataTicket.create("the_last_sword_queen_blink_age", Float.class);
    private static final DataTicket<Float> BODY_YAW = DataTicket.create("the_last_sword_queen_body_yaw", Float.class);

    public ThePastShadowOfTheQueenRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new ThePastShadowOfTheQueenModel());
        withRenderLayer(renderer -> new ThePastShadowOfTheQueenItemLayer<>(renderManager, this));
        this.shadowRadius = 0.5f;
        //GeckoLib 5.4 replaced preRender with a RenderPassInfo hook; the scale is constant, so set it once here
        float scale = 57.5F / 44.5F;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
    }

    //1.21.9+: render(...) is submit(...); the rifts are queued as custom geometry on QueenBlinkRenderUtil's rift type
    //(all rift() and summonRift() draw), from the entity's pose, after the model -- as 1.21.1 drew them after super.render
    @Override
    public void submit(R queen, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        super.submit(queen, pose, collector, camera);
        float summonAge = queen.getOrDefaultGeckolibData(SUMMON_AGE, -1.0F);
        float blinkAge = queen.getOrDefaultGeckolibData(BLINK_AGE, -1.0F);
        if (summonAge < 0 && blinkAge < 0) {
            return;
        }
        float summonYaw = queen.getOrDefaultGeckolibData(SUMMON_YAW, 0.0F);
        float bodyYaw = queen.getOrDefaultGeckolibData(BODY_YAW, 0.0F);
        collector.submitCustomGeometry(pose, QueenBlinkRenderUtil.riftRenderType(), (last, vertices) -> {
            PoseStack rift = new PoseStack();
            rift.last().set(last);
            MultiBufferSource buffers = renderType -> vertices;
            if (summonAge >= 0) {
                QueenBlinkRenderUtil.summonRift(rift, buffers, summonAge, summonYaw);
            }
            if (blinkAge >= 0) {
                QueenBlinkRenderUtil.rift(rift, buffers, blinkAge, bodyYaw);
            }
        });
    }

    // 1.21.1's ThePastShadowOfTheQueenEntity.getBoundingBoxForCulling: 1.21.2 asks the renderer instead
    @Override
    protected AABB getBoundingBoxForCulling(ThePastShadowOfTheQueenEntity entity) {
        AABB box = super.getBoundingBoxForCulling(entity);
        return entity.getSummonTick() >= 0 ? box.inflate(3, 6, 3) : box;
    }

    @Override
    public RenderType getRenderType(R queen, Identifier texture) {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(getTextureLocation(queen));
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    //受伤变红效果
    @Override
    public int getPackedOverlay(ThePastShadowOfTheQueenEntity entity, Void relatedObject, float u, float partialTick) {
        //1.21.4: a GeckoLib renderer never reaches LivingEntityRenderer.getOverlayCoords, where the Justified
        //Defence white flash is mixed in (LivingEntityRendererMixin); apply it here, as GuardianOfSealedSpireRenderer does
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }

    @Override
    public void extractRenderState(ThePastShadowOfTheQueenEntity entity, R queen, float partialTick) {
        super.extractRenderState(entity, queen, partialTick);
        queen.addGeckolibData(SUMMON_AGE, entity.getSummonTick() >= 0 ? entity.getSummonTick() + partialTick : -1.0F);
        queen.addGeckolibData(SUMMON_YAW, entity.getSummonYaw());
        queen.addGeckolibData(BLINK_AGE, entity.getBlinkTick() >= 0 ? entity.getBlinkTick() + partialTick : -1.0F);
        queen.addGeckolibData(BODY_YAW, Mth.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot));
    }

    //GeckoLib 5.4: the head look the model's setCustomAnimations applied (pitch/yaw from the render state, in degrees)
    //is a bone-snapshot adjustment on the renderer, made for every frame after the animation
    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
        Float pitch = renderPassInfo.getGeckolibData(DataTickets.ENTITY_PITCH);
        Float yaw = renderPassInfo.getGeckolibData(DataTickets.ENTITY_YAW);
        if (pitch == null || yaw == null) {
            return;
        }
        snapshots.ifPresent("head", head -> head.setRotX(pitch * Mth.DEG_TO_RAD).setRotY(yaw * Mth.DEG_TO_RAD));
    }
}
