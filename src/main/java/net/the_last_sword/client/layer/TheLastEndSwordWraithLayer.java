package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.model.TheLastEndSwordWraithModel;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TheLastEndSwordWraithLayer<R extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<TheLastEndSwordWraithEntity, Void, R> {
    private static final ResourceLocation LAYER = ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/entities/the_last_end_sword_wraith_light.png");

    public TheLastEndSwordWraithLayer(GeoRenderer<TheLastEndSwordWraithEntity, Void, R> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void render(R renderState, PoseStack poseStack, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, int renderColor) {
        //外观在提取阶段记录（渲染状态不再持有实体）
        if (renderState.getGeckolibData(TheLastEndSwordWraithModel.APPEARANCE) != TheLastEndSwordWraithAppearance.DEFAULT) {
            return;
        }
        RenderType glowRenderType = RenderType.eyes(LAYER);
        this.renderer.reRender(renderState, poseStack, bakedModel, bufferSource, glowRenderType, bufferSource.getBuffer(glowRenderType), packedLight, OverlayTexture.NO_OVERLAY, -1);
    }
}
