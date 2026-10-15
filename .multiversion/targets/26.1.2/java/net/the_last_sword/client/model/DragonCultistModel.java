package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.DragonCultistEntity;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCultistModel extends GeoModel<DragonCultistEntity> {

    @Override
    public Identifier getAnimationResource(DragonCultistEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cultist");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "dragon_cultist");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/dragon_cultist.png");
    }

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //DragonCultistRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
