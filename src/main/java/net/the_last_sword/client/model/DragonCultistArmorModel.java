package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.item.DragonCultistArmorItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCultistArmorModel extends GeoModel<DragonCultistArmorItem> {

    @Override
    public Identifier getAnimationResource(DragonCultistArmorItem object) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cultist_armor");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cultist_armor");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/item/dragon_cultist_armor.png");
    }
}
