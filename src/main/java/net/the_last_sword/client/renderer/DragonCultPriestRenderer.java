package net.the_last_sword.client.renderer;

import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.constant.DataTickets;
import net.minecraft.util.Mth;
import net.the_last_sword.client.JustifiedDefenceFlash;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.the_last_sword.client.layer.DeathRayLayer;
import net.the_last_sword.client.model.DragonCultPriestModel;
import net.the_last_sword.entity.DragonCultPriestEntity;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer)
public class DragonCultPriestRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<DragonCultPriestEntity, R> {

    //拜龙教紫，替代末影龙原本的品红
    private static final int RAY_RED = 155;
    private static final int RAY_GREEN = 89;
    private static final int RAY_BLUE = 208;

    //死亡进度与半身高在提取阶段记录（渲染状态不再持有实体）
    private static final DataTicket<Float> DEATH_PROGRESS =
            DataTicket.create("the_last_sword_priest_death_progress", Float.class);
    private static final DataTicket<Float> HALF_HEIGHT =
            DataTicket.create("the_last_sword_priest_half_height", Float.class);

    public DragonCultPriestRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonCultPriestModel());
        this.shadowRadius = 0.5f;
        //GeckoLib 5.4 replaced preRender with a RenderPassInfo hook; the scale is constant, so set it once here
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    public void extractRenderState(DragonCultPriestEntity entity, R renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        int deathTick = entity.getDeathTick();
        renderState.addGeckolibData(DEATH_PROGRESS, deathTick <= 0 ? 0.0f
                : Math.min((deathTick + partialTick) / DragonCultPriestEntity.DEATH_ANIMATION_TICKS, 1.0f));
        renderState.addGeckolibData(HALF_HEIGHT, entity.getBbHeight() * 0.5f);
    }

    //模型渲染完毕后叠加死亡放射光柱
    //1.21.9+: render(state, pose, MultiBufferSource, light) is submit(state, pose, SubmitNodeCollector, camera); the rays
    //are queued as custom geometry on the lightning render type DeathRayLayer draws with, from the same pose
    @Override
    public void submit(R renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        super.submit(renderState, poseStack, collector, cameraState);

        float progress = renderState.getOrDefaultGeckolibData(DEATH_PROGRESS, 0.0f);
        if (progress <= 0.0f) {
            return;
        }
        float halfHeight = renderState.getOrDefaultGeckolibData(HALF_HEIGHT, 0.0f);
        collector.submitCustomGeometry(poseStack, net.minecraft.client.renderer.rendertype.RenderTypes.lightning(), (pose, vertices) -> {
            PoseStack rays = new PoseStack();
            rays.last().set(pose);
            DeathRayLayer.render(rays, renderType -> vertices, progress, halfHeight, RAY_RED, RAY_GREEN, RAY_BLUE);
        });
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public int getPackedOverlay(DragonCultPriestEntity entity, Void relatedObject, float u, float partialTick) {
        //1.21.4: a GeckoLib renderer never reaches LivingEntityRenderer.getOverlayCoords, where the Justified
        //Defence white flash is mixed in (LivingEntityRendererMixin); apply it here, as GuardianOfSealedSpireRenderer does
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
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
