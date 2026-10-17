package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.LostWraithEntity;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class LostWraithModel extends GeoModel<LostWraithEntity> {
    @Override
    public Identifier getAnimationResource(LostWraithEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "lost_wraith");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "lost_wraith");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/lost_wraith.png");
    }

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //LostWraithRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
