package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.item.DragonArmorItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonArmorModel extends GeoModel<DragonArmorItem> {

    @Override
    public Identifier getAnimationResource(DragonArmorItem object) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "animations/dragon_armor.animation.json");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "geo/dragon_armor.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/item/dragon_armor.png");
    }
}
