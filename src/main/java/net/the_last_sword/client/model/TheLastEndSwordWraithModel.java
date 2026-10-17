package net.the_last_sword.client.model;

import net.minecraft.resources.Identifier;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;

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

    //GeckoLib 5.4: setCustomAnimations is gone from GeoModel; the head look (pitch/yaw from the render state) is
    //TheLastEndSwordWraithRenderer.adjustModelBonesForRender, applied to the frame's bone snapshots
}
