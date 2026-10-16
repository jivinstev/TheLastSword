package net.the_last_sword.client.model;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.the_last_sword.entity.GuardianOfSealedSpireEntity;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GuardianOfSealedSpireModel extends GeoModel<GuardianOfSealedSpireEntity> {

    @Override
    public ResourceLocation getAnimationResource(GuardianOfSealedSpireEntity entity) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "guardian_of_sealed_spire");
    }

    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "guardian_of_sealed_spire");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/entities/guardian_of_sealed_spire.png");
    }

    //GeckoLib 5: the head angles come from the render state (DataTickets.ENTITY_PITCH / ENTITY_YAW) instead of EntityModelData
    //(raw xRot/yRot there; GeckoLib 4 negated both into EntityModelData, as DefaultedEntityGeoModel still does)
    @Override
    @SuppressWarnings("rawtypes")
    public void setCustomAnimations(AnimationState animationState) {
        GeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            head.setRotX(-(Float) animationState.getData(DataTickets.ENTITY_PITCH) * Mth.DEG_TO_RAD);
            head.setRotY(-(Float) animationState.getData(DataTickets.ENTITY_YAW) * Mth.DEG_TO_RAD);
        }
    }
}
