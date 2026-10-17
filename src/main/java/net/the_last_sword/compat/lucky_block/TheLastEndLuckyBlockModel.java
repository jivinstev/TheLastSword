package net.the_last_sword.compat.lucky_block;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TheLastEndLuckyBlockModel extends GeoModel<TheLastEndLuckyBlockEntity> {

    @Override
    public ResourceLocation getAnimationResource(TheLastEndLuckyBlockEntity animatable) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "animations/the_last_end_lucky_block.animation.json");
    }

    @Override
    public ResourceLocation getModelResource(TheLastEndLuckyBlockEntity animatable, software.bernie.geckolib.renderer.GeoRenderer<TheLastEndLuckyBlockEntity> renderer) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "geo/the_last_end_lucky_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TheLastEndLuckyBlockEntity animatable, software.bernie.geckolib.renderer.GeoRenderer<TheLastEndLuckyBlockEntity> renderer) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/block/the_last_end_lucky_block.png");
    }
}
