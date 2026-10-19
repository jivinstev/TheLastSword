package net.the_last_sword.client.renderer;

import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.constant.DataTickets;
import net.minecraft.util.Mth;
import net.the_last_sword.client.JustifiedDefenceFlash;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.the_last_sword.client.layer.TheLastEndSwordWraithLayer;
import net.the_last_sword.client.layer.TheLastEndSwordWraithTheLastEndLayer;
import net.the_last_sword.client.model.TheLastEndSwordWraithModel;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer)
public class TheLastEndSwordWraithRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<TheLastEndSwordWraithEntity, R> {
    //名牌改为基于渲染状态绘制，终末印记在提取阶段记录（NeoForge 在提取后清空 renderData，故存入 GeckoLib 数据）
    private static final DataTicket<Integer> END_MARK =
            DataTicket.create("the_last_sword_wraith_end_mark", Integer.class);

    public TheLastEndSwordWraithRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new TheLastEndSwordWraithModel());
        this.shadowRadius = 0.5f;
        //GeckoLib 5.4 replaced preRender with a RenderPassInfo hook; the scale is constant, so set it once here
        float scale = 0.55f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        this.withRenderLayer(new TheLastEndSwordWraithLayer<>(this));
        this.withRenderLayer(new TheLastEndSwordWraithTheLastEndLayer<>(this));
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public boolean shouldShowName(TheLastEndSwordWraithEntity entity, double distToCameraSq) {
        return !entity.isAppearancePreview();
    }

    @Override
    public void extractRenderState(TheLastEndSwordWraithEntity entity, R renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        renderState.addGeckolibData(TheLastEndSwordWraithModel.APPEARANCE, entity.getAppearance());
        renderState.addGeckolibData(END_MARK, entity.getEndMark());
    }

    @Override
    protected void submitNameTag(R renderState, PoseStack poseStack, SubmitNodeCollector collector,
                                 CameraRenderState cameraState) {
        super.submitNameTag(renderState, poseStack, collector, cameraState);
        if (renderState.nameTag == null) {
            return;
        }
        Component name = renderState.nameTag;
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.28D, 0.0D);
        Integer endMark = renderState.getGeckolibData(END_MARK);
        renderState.nameTag = Component.translatable("entity.the_last_sword.the_last_end_sword_wraith.end_mark",
                endMark != null ? endMark : 0).withStyle(ChatFormatting.DARK_PURPLE);
        try {
            super.submitNameTag(renderState, poseStack, collector, cameraState);
        } finally {
            renderState.nameTag = name;
        }
        poseStack.popPose();
    }

    //受伤变红效果（等同原版 getOverlayCoords(entity, 0)）
    @Override
    public int getPackedOverlay(TheLastEndSwordWraithEntity entity, Void relatedObject, float u, float partialTick) {
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
