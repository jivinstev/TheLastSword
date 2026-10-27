package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.the_last_sword.client.model.TheLastEndSwordWraithModel;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;

public class TheLastEndSwordWraithLayer<R extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<TheLastEndSwordWraithEntity, Void, R> {
    private static final Identifier LAYER = Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/the_last_end_sword_wraith_light.png");

    public TheLastEndSwordWraithLayer(GeoRenderer<TheLastEndSwordWraithEntity, Void, R> entityRenderer) {
        super(entityRenderer);
    }

    //GeckoLib 5.4: the layer hook takes the pass's RenderPassInfo; buildRenderTask is gone (GeoModelPass)
    @Override
    public void submitRenderTask(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector renderTasks) {
        R renderState = renderPassInfo.renderState();
        //外观在提取阶段记录（渲染状态不再持有实体）
        if (renderState.getGeckolibData(TheLastEndSwordWraithModel.APPEARANCE) != TheLastEndSwordWraithAppearance.DEFAULT) {
            return;
        }
        RenderType glowRenderType = net.minecraft.client.renderer.rendertype.RenderTypes.eyes(LAYER);
        GeoModelPass.submit(renderPassInfo, renderTasks.order(1), glowRenderType, renderPassInfo.packedLight(),
                OverlayTexture.NO_OVERLAY, -1);
    }
}
