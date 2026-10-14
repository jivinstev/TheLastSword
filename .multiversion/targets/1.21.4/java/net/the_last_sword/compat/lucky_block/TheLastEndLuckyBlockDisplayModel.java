package net.the_last_sword.compat.lucky_block;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;

public class TheLastEndLuckyBlockDisplayModel extends GeoModel<TheLastEndLuckyBlockDisplayItem> {

    @Override
    public ResourceLocation getAnimationResource(TheLastEndLuckyBlockDisplayItem animatable) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "animations/the_last_end_lucky_block.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TheLastEndLuckyBlockDisplayItem animatable, GeoRenderer<TheLastEndLuckyBlockDisplayItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "geo/the_last_end_lucky_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TheLastEndLuckyBlockDisplayItem animatable, GeoRenderer<TheLastEndLuckyBlockDisplayItem> renderer) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/block/the_last_end_lucky_block.png");
    }
}
