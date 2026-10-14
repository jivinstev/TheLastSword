package net.the_last_sword.client.renderer;

import net.the_last_sword.block.entity.DragonCrystalEnchantingTableBlockEntity;
import net.the_last_sword.client.model.DragonCrystalEnchantingTableModel;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class DragonCrystalEnchantingTableRenderer<R extends net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState & GeoRenderState>
        extends GeoBlockRenderer<DragonCrystalEnchantingTableBlockEntity, R> {

    public DragonCrystalEnchantingTableRenderer() {
        super(new DragonCrystalEnchantingTableModel());
    }
}
