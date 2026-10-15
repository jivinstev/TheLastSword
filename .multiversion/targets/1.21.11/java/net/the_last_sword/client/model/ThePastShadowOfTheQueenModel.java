package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.ThePastShadowOfTheQueenEntity;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class ThePastShadowOfTheQueenModel extends GeoModel<ThePastShadowOfTheQueenEntity> {
    @Override
    public Identifier getAnimationResource(ThePastShadowOfTheQueenEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "the_past_shadow_of_the_queen");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "the_past_shadow_of_the_queen");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/the_past_shadow_of_the_queen.png");
    }

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //ThePastShadowOfTheQueenRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
