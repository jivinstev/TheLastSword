package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.item.display.DragonCrystalEnchantingTableDisplayItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCrystalEnchantingTableDisplayModel extends GeoModel<DragonCrystalEnchantingTableDisplayItem> {

    @Override
    public Identifier getAnimationResource(DragonCrystalEnchantingTableDisplayItem animatable) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_crystal_enchanting_table");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_crystal_enchanting_table");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/block/dragon_crystal_enchanting_table.png");
    }
}
