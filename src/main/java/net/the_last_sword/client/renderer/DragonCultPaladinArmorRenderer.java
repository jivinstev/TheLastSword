package net.the_last_sword.client.renderer;

import net.the_last_sword.client.model.DragonCultPaladinArmorModel;
import net.the_last_sword.item.DragonCultPaladinArmorItem;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCultPaladinArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<DragonCultPaladinArmorItem, R> {

    public DragonCultPaladinArmorRenderer() {
        super(new DragonCultPaladinArmorModel());
    }
}
