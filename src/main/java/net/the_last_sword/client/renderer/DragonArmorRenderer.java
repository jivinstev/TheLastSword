package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.the_last_sword.client.model.DragonArmorModel;
import net.the_last_sword.item.DragonArmorItem;
import net.the_last_sword.util.PhasingState;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<DragonArmorItem, R> {

    public DragonArmorRenderer() {
        super(new DragonArmorModel());
    }

    //GeckoLib 5 has no entity ticket: record the wearer for this render pass (read by actuallyRender and by
    //DragonArmorItem's animation predicates through the same render state)
    @Override
    public void addRenderData(DragonArmorItem animatable, GeoArmorRenderer.RenderData renderData, R renderState) {
        renderState.addGeckolibData(DragonArmorItem.WEARER, renderData.entity());
    }

    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    public void actuallyRender(R renderState, PoseStack poseStack, BakedGeoModel model,
                               RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                               boolean isReRender, int packedLight, int packedOverlay, int renderColor) {
        // 护甲会重新获取全局缓冲，需要补偿被绕过的透明度包装，且不能重复减半。
        if (!(bufferSource instanceof PhasingBufferSource)
                && renderState.getGeckolibData(DragonArmorItem.WEARER) instanceof LivingEntity entity
                && PhasingState.isPhasing(entity)) {
            int alpha = (renderColor >>> 24) & 0xFF;
            alpha = Math.round(alpha * 0.5F);
            renderColor = (renderColor & 0x00FFFFFF) | (alpha << 24);
        }
        super.actuallyRender(renderState, poseStack, model, renderType, bufferSource, buffer, isReRender,
                packedLight, packedOverlay, renderColor);
    }

}
