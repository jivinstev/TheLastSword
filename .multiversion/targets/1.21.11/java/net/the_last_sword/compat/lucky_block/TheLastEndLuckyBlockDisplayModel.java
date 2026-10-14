package net.the_last_sword.compat.lucky_block;

import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class TheLastEndLuckyBlockDisplayModel extends GeoModel<TheLastEndLuckyBlockDisplayItem> {

    @Override
    public Identifier getAnimationResource(TheLastEndLuckyBlockDisplayItem animatable) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "animations/the_last_end_lucky_block.animation.json");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "geo/the_last_end_lucky_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/block/the_last_end_lucky_block.png");
    }
}
