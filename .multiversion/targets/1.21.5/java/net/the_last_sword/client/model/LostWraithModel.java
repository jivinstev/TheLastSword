package net.the_last_sword.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.the_last_sword.entity.LostWraithEntity;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class LostWraithModel extends GeoModel<LostWraithEntity> {
    @Override
    public ResourceLocation getAnimationResource(LostWraithEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "lost_wraith");
    }

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "lost_wraith");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/entities/lost_wraith.png");
    }

    //GeckoLib 5: the head angles come from the render state (DataTickets.ENTITY_PITCH / ENTITY_YAW) instead of EntityModelData
    @Override
    @SuppressWarnings("rawtypes")
    public void setCustomAnimations(AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotX((Float) animationState.getData(DataTickets.ENTITY_PITCH) * Mth.DEG_TO_RAD);
            head.setRotY((Float) animationState.getData(DataTickets.ENTITY_YAW) * Mth.DEG_TO_RAD);
        }
    }
}
