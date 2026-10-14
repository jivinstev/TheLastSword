package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.the_last_sword.entity.DragonCultPaladinEntity;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
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

    //GeckoLib 5: the head angles come from the render state (DataTickets.ENTITY_PITCH / ENTITY_YAW) instead of EntityModelData
    @Override
    @SuppressWarnings("rawtypes")
    public void setCustomAnimations(AnimationState animationState) {
        var head = getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotX((Float) animationState.getData(DataTickets.ENTITY_PITCH) * Mth.DEG_TO_RAD);
            head.setRotY((Float) animationState.getData(DataTickets.ENTITY_YAW) * Mth.DEG_TO_RAD);
        }
    }
}
