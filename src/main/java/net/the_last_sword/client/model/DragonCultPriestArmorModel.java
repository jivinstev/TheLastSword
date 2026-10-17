package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.item.DragonCultPriestArmorItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCultPriestArmorModel extends GeoModel<DragonCultPriestArmorItem> {

    @Override
    public Identifier getAnimationResource(DragonCultPriestArmorItem object) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cult_priest_armor");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cult_priest_armor");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/item/dragon_cult_priest_armor.png");
    }
}
