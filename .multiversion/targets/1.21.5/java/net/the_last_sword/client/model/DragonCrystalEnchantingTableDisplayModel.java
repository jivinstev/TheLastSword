package net.the_last_sword.client.model;

import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.item.display.DragonCrystalEnchantingTableDisplayItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCrystalEnchantingTableDisplayModel extends GeoModel<DragonCrystalEnchantingTableDisplayItem> {

    @Override
    public ResourceLocation getAnimationResource(DragonCrystalEnchantingTableDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_crystal_enchanting_table");
    }

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_crystal_enchanting_table");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/block/dragon_crystal_enchanting_table.png");
    }
}
