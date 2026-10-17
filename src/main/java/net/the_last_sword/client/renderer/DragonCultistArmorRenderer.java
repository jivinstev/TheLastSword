package net.the_last_sword.client.renderer;

import net.the_last_sword.client.model.DragonCultistArmorModel;
import net.the_last_sword.item.DragonCultistArmorItem;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCultistArmorRenderer<R extends net.minecraft.client.renderer.entity.state.HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<DragonCultistArmorItem, R> {

    public DragonCultistArmorRenderer() {
        super(new DragonCultistArmorModel());
    }
}
