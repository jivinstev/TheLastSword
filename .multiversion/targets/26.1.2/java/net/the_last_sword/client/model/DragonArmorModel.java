package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.item.DragonArmorItem;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonArmorModel extends GeoModel<DragonArmorItem> {

    @Override
    public Identifier getAnimationResource(DragonArmorItem object) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_armor");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_armor");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/item/dragon_armor.png");
    }
}
