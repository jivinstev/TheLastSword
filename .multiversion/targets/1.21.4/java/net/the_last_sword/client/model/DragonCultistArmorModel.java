package net.the_last_sword.client.model;

import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.item.DragonCultistArmorItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class DragonCultistArmorModel extends GeoModel<DragonCultistArmorItem> {

    @Override
    public ResourceLocation getAnimationResource(DragonCultistArmorItem object) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "animations/dragon_cultist_armor.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(DragonCultistArmorItem object, GeoRenderer<DragonCultistArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "geo/dragon_cultist_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DragonCultistArmorItem object, GeoRenderer<DragonCultistArmorItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/item/dragon_cultist_armor.png");
    }
}
