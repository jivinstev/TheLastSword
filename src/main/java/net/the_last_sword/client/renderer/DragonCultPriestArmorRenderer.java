package net.the_last_sword.client.renderer;

import net.the_last_sword.client.model.DragonCultPriestArmorModel;
import net.the_last_sword.item.DragonCultPriestArmorItem;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCultPriestArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<DragonCultPriestArmorItem, R> {

    public DragonCultPriestArmorRenderer() {
        super(new DragonCultPriestArmorModel());
    }
}
