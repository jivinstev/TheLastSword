package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.item.DragonCultPaladinArmorItem;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCultPaladinArmorModel extends GeoModel<DragonCultPaladinArmorItem> {

    @Override
    public Identifier getAnimationResource(DragonCultPaladinArmorItem object) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cult_paladin_armor");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cult_paladin_armor");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/item/dragon_cult_paladin_armor.png");
    }
}
