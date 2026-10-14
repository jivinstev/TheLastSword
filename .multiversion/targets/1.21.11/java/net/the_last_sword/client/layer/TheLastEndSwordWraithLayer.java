package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.the_last_sword.client.model.TheLastEndSwordWraithModel;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class TheLastEndSwordWraithLayer<R extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<TheLastEndSwordWraithEntity, Void, R> {
    private static final Identifier LAYER = Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/the_last_end_sword_wraith_light.png");

    public TheLastEndSwordWraithLayer(GeoRenderer<TheLastEndSwordWraithEntity, Void, R> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void submitRenderTask(R renderState, PoseStack poseStack, BakedGeoModel bakedModel, SubmitNodeCollector renderTasks,
                                 CameraRenderState cameraState, int packedLight, int packedOverlay, int renderColor,
                                 boolean didRenderModel) {
        //外观在提取阶段记录（渲染状态不再持有实体）
        if (renderState.getGeckolibData(TheLastEndSwordWraithModel.APPEARANCE) != TheLastEndSwordWraithAppearance.DEFAULT) {
            return;
        }
        RenderType glowRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.eyes(LAYER);
        this.renderer.buildRenderTask(renderState, poseStack, bakedModel, this.renderer.getGeoModel(), renderTasks.order(1),
                cameraState, glowRenderType, packedLight, OverlayTexture.NO_OVERLAY, -1, null);
    }
}
