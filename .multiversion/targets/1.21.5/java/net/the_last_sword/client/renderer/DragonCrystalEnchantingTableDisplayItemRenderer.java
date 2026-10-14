package net.the_last_sword.client.renderer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.model.DragonCrystalEnchantingTableDisplayModel;
import net.the_last_sword.item.display.DragonCrystalEnchantingTableDisplayItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCrystalEnchantingTableDisplayItemRenderer extends GeoItemRenderer<DragonCrystalEnchantingTableDisplayItem> {

    public DragonCrystalEnchantingTableDisplayItemRenderer() {
        super(new DragonCrystalEnchantingTableDisplayModel());
    }

    @Override
    public RenderType getRenderType(GeoRenderState renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(renderState));
    }
}
