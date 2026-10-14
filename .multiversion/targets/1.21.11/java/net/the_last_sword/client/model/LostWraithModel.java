package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.LostWraithEntity;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LostWraithModel extends GeoModel<LostWraithEntity> {
    @Override
    public Identifier getAnimationResource(LostWraithEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "animations/lost_wraith.animation.json");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "geo/lost_wraith.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/lost_wraith.png");
    }

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //LostWraithRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
