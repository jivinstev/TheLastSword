package net.the_last_sword.client.renderer;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.the_last_sword.client.model.DragonArmorModel;
import net.the_last_sword.item.DragonArmorItem;
import net.the_last_sword.util.PhasingState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<DragonArmorItem, R> {

    private static final DataTicket<Integer> PHASING_RESTORE_COLOR =
            DataTicket.create("the_last_sword_phasing_restore_color", Integer.class);

    public DragonArmorRenderer() {
        super(new DragonArmorModel());
    }

    //GeckoLib 5 has no entity ticket: record the wearer for this render pass (read by actuallyRender and by
    //DragonArmorItem's animation predicates through the same render state)
    @Override
    public void addRenderData(DragonArmorItem animatable, GeoArmorRenderer.RenderData renderData, R renderState, float partialTick) {
        renderState.addGeckolibData(DragonArmorItem.WEARER, renderData.entity());
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }

    // 护甲会重新获取全局缓冲，需要补偿被绕过的透明度包装，且不能重复减半。
    //1.21.9+: GeckoLib's armor layer submits through the layer's collector, which EntityRenderDispatcherMixin may
    //already have wrapped in PhasingSubmitNodeCollector (it halves every vertex): compensate only when it has not.
    //1.21.11 (GeckoLib 5.4): preRender(R, PoseStack, ...) is preRenderPass(RenderPassInfo, collector), and the pass's
    //colour is the render state's RENDER_COLOR (RenderPassInfo.renderColor()), so the halved alpha is written there for
    //this pass and the colour put back in postRenderPass (the wearer's render state is shared by every armour piece)
    @Override
    public void preRenderPass(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector collector) {
        R renderState = renderPassInfo.renderState();
        if (!(collector instanceof PhasingSubmitNodeCollector)
                && renderState.getGeckolibData(DragonArmorItem.WEARER) instanceof LivingEntity entity
                && PhasingState.isPhasing(entity)) {
            int renderColor = renderPassInfo.renderColor();
            renderState.addGeckolibData(PHASING_RESTORE_COLOR, renderColor);
            int alpha = (renderColor >>> 24) & 0xFF;
            alpha = Math.round(alpha * 0.5F);
            renderState.addGeckolibData(DataTickets.RENDER_COLOR, (renderColor & 0x00FFFFFF) | (alpha << 24));
        }
        super.preRenderPass(renderPassInfo, collector);
    }

    @Override
    public void postRenderPass(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector collector) {
        super.postRenderPass(renderPassInfo, collector);
        R renderState = renderPassInfo.renderState();
        Integer original = renderState.getGeckolibData(PHASING_RESTORE_COLOR);
        if (original != null) {
            renderState.addGeckolibData(DataTickets.RENDER_COLOR, original);
            renderState.getDataMap().remove(PHASING_RESTORE_COLOR);
        }
    }

}
