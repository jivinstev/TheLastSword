package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.the_last_sword.entity.DragonCultistEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.model.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCultistModel extends GeoModel<DragonCultistEntity> {

    @Override
    public Identifier getAnimationResource(DragonCultistEntity entity) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "animations/dragon_cultist.animation.json");
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "geo/dragon_cultist.geo.json");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath("the_last_sword", "textures/entities/dragon_cultist.png");
    }

    //GeckoLib 5: the head angles come from the render state (DataTickets.ENTITY_PITCH / ENTITY_YAW) instead of EntityModelData
    @Override
    @SuppressWarnings("rawtypes")
    public void setCustomAnimations(GeoRenderState renderState) {
        GeoBone head = getBone("head");
        if (head != null) {
            head.setRotX((Float) renderState.getGeckolibData(DataTickets.ENTITY_PITCH) * Mth.DEG_TO_RAD);
            head.setRotY((Float) renderState.getGeckolibData(DataTickets.ENTITY_YAW) * Mth.DEG_TO_RAD);
        }
    }
}
