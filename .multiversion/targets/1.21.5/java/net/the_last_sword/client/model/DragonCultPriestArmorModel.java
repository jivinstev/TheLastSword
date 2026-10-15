package net.the_last_sword.client.model;

import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.item.DragonCultPriestArmorItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCultPriestArmorModel extends GeoModel<DragonCultPriestArmorItem> {

    @Override
    public ResourceLocation getAnimationResource(DragonCultPriestArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_cult_priest_armor");
    }

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_cult_priest_armor");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/item/dragon_cult_priest_armor.png");
    }
}
