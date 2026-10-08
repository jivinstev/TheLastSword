package net.the_last_sword.client;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.renderer.DragonCrystalEnchantingTableDisplayItemRenderer;

//客户端专用：龙水晶附魔台展示物品的自定义渲染器
public class DragonCrystalEnchantingTableClientExtensions implements IClientItemExtensions {
    private final BlockEntityWithoutLevelRenderer renderer = new DragonCrystalEnchantingTableDisplayItemRenderer();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return renderer;
    }
}
