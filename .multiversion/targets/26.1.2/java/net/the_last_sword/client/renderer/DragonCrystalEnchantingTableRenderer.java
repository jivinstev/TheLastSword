package net.the_last_sword.client.renderer;

import net.the_last_sword.block.entity.DragonCrystalEnchantingTableBlockEntity;
import net.the_last_sword.client.model.DragonCrystalEnchantingTableModel;
import com.geckolib.renderer.GeoBlockRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class DragonCrystalEnchantingTableRenderer<R extends net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState & GeoRenderState>
        extends GeoBlockRenderer<DragonCrystalEnchantingTableBlockEntity, R> {

    //GeckoLib 5.5: the block renderer takes the BlockEntityRendererProvider.Context
    public DragonCrystalEnchantingTableRenderer(net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context context) {
        super(context, new DragonCrystalEnchantingTableModel());
    }
}
