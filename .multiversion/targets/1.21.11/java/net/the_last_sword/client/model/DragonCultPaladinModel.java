package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.DragonCultPaladinEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCultPaladinModel extends GeoModel<DragonCultPaladinEntity> {

    @Override
    public Identifier getAnimationResource(DragonCultPaladinEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "animations/dragon_cult_paladin.animation.json");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "geo/dragon_cult_paladin.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/dragon_cult_paladin.png");
    }

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //DragonCultPaladinRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
