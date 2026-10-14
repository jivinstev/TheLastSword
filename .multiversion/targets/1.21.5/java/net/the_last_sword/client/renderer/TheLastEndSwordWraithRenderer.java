package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.layer.TheLastEndSwordWraithLayer;
import net.the_last_sword.client.layer.TheLastEndSwordWraithTheLastEndLayer;
import net.the_last_sword.client.model.TheLastEndSwordWraithModel;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
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
        this.addRenderLayer(new TheLastEndSwordWraithLayer<>(this));
        this.addRenderLayer(new TheLastEndSwordWraithTheLastEndLayer<>(this));
    }

    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int colour) {
        float scale = 0.55f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(renderState, poseStack, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, colour);
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
    protected void renderNameTag(R renderState, Component name, PoseStack poseStack,
                                 MultiBufferSource bufferSource, int packedLight) {
        super.renderNameTag(renderState, name, poseStack, bufferSource, packedLight);
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.28D, 0.0D);
        Integer endMark = renderState.getGeckolibData(END_MARK);
        Component marks = Component.translatable("entity.the_last_sword.the_last_end_sword_wraith.end_mark",
                endMark != null ? endMark : 0).withStyle(ChatFormatting.DARK_PURPLE);
        super.renderNameTag(renderState, marks, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }

    //受伤变红效果（等同原版 getOverlayCoords(entity, 0)）
    @Override
    public int getPackedOverlay(TheLastEndSwordWraithEntity entity, Void relatedObject, float u, float partialTick) {
        return OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
}
