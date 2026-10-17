package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.the_last_sword.client.model.DragonArmorModel;
import net.the_last_sword.item.DragonArmorItem;
import net.the_last_sword.util.PhasingState;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<DragonArmorItem, R> {

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

    //1.21.9+: the armour is submitted through the wearer's collector, which EntityRenderDispatcherMixin wraps for a phasing
    //wearer (PhasingSubmitNodeCollector halves its alpha there), so the old compensation for armour that bypassed the
    //phasing buffer is gone with GeckoLib 5.4's preRender(R, PoseStack, ...) -- halving here too would quarter it

}
