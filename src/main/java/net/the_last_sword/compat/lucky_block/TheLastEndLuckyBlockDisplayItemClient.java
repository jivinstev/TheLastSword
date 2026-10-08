package net.the_last_sword.compat.lucky_block;

import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

//客户端专用: 持有自定义渲染器, 仅在 initializeClient 中被引用, 避免专用服务器加载客户端类
public final class TheLastEndLuckyBlockDisplayItemClient {

    private TheLastEndLuckyBlockDisplayItemClient() {
    }

    public static IClientItemExtensions createExtensions() {
        return new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new TheLastEndLuckyBlockDisplayItemRenderer();

            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }
        };
    }
}
