package net.the_last_sword.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.the_last_sword.client.model.DragonCrystalEnchantingTableDisplayModel;
import net.the_last_sword.item.display.DragonCrystalEnchantingTableDisplayItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCrystalEnchantingTableDisplayItemRenderer extends GeoItemRenderer<DragonCrystalEnchantingTableDisplayItem> {

    public DragonCrystalEnchantingTableDisplayItemRenderer() {
        super(new DragonCrystalEnchantingTableDisplayModel());
    }

    @Override
    public RenderType getRenderType(GeoRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }
}
