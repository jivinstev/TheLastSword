package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.cache.model.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class TheLastEndSwordWraithModel extends GeoModel<TheLastEndSwordWraithEntity> {
    //GeckoLib 5: the model sees a render state, not the entity, so the renderer parks the appearance on it at extract time
    public static final DataTicket<TheLastEndSwordWraithAppearance> APPEARANCE =
            DataTicket.create("the_last_sword_wraith_appearance", TheLastEndSwordWraithAppearance.class);

    @Override
    public Identifier getAnimationResource(TheLastEndSwordWraithEntity entity) {
        return entity.getAppearance().getAnimation();
    }

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return appearanceOf(renderState).getModel();
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return appearanceOf(renderState).getTexture();
    }

    private static TheLastEndSwordWraithAppearance appearanceOf(GeoRenderState renderState) {
        TheLastEndSwordWraithAppearance appearance = renderState.getGeckolibData(APPEARANCE);
        return appearance != null ? appearance : TheLastEndSwordWraithAppearance.DEFAULT;
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
