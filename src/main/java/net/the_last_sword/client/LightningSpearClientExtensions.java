package net.the_last_sword.client;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.renderer.LightningSpearItemRenderer;

public class LightningSpearClientExtensions implements IClientItemExtensions {
    private final BlockEntityWithoutLevelRenderer renderer = new LightningSpearItemRenderer();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return renderer;
    }
}
