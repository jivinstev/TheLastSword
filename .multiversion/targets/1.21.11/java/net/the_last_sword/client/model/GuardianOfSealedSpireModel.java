package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.GuardianOfSealedSpireEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GuardianOfSealedSpireModel extends GeoModel<GuardianOfSealedSpireEntity> {

    @Override
    public Identifier getAnimationResource(GuardianOfSealedSpireEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "animations/guardian_of_sealed_spire.animation.json");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "geo/guardian_of_sealed_spire.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/guardian_of_sealed_spire.png");
    }

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //GuardianOfSealedSpireRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
