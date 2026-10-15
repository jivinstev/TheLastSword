package net.the_last_sword.compat.lucky_block;

import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class TheLastEndLuckyBlockDisplayModel extends GeoModel<TheLastEndLuckyBlockDisplayItem> {

    @Override
    public Identifier getAnimationResource(TheLastEndLuckyBlockDisplayItem animatable) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "the_last_end_lucky_block");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "the_last_end_lucky_block");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/block/the_last_end_lucky_block.png");
    }
}
