package net.the_last_sword.compat.lucky_block;

import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

//客户端专用: 持有自定义渲染器, 仅在客户端注册时被引用, 避免专用服务器加载客户端类
public final class TheLastEndLuckyBlockDisplayItemClient {

    private TheLastEndLuckyBlockDisplayItemClient() {
    }

    private static TheLastEndLuckyBlockDisplayItemRenderer renderer;

    //1.21.4 起 IClientItemExtensions 不再提供自定义物品渲染器, 渲染器改由 GeckoLib 的 GeoRenderProvider 提供
    public static TheLastEndLuckyBlockDisplayItemRenderer getRenderer() {
        if (renderer == null) {
            renderer = new TheLastEndLuckyBlockDisplayItemRenderer();
        }
        return renderer;
    }

    public static IClientItemExtensions createExtensions() {
        return new IClientItemExtensions() {
        };
    }
}
