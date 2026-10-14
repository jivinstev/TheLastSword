package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.DragonCultPriestEntity;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;


public class DragonCultPriestModel extends GeoModel<DragonCultPriestEntity> {

    @Override
    public Identifier getAnimationResource(DragonCultPriestEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "animations/dragon_cult_priest.animation.json");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "geo/dragon_cult_priest.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/dragon_cult_priest.png");
    }

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //DragonCultPriestRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
