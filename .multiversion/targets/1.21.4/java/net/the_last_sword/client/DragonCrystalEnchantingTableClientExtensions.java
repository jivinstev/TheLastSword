package net.the_last_sword.client;

import net.the_last_sword.client.renderer.DragonCrystalEnchantingTableDisplayItemRenderer;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.renderer.GeoItemRenderer;

//客户端专用：龙水晶附魔台展示物品的自定义渲染器
//1.21.4: IClientItemExtensions has no getCustomRenderer; GeckoLib serves the same renderer through a GeoRenderProvider
//(GeoItem.createGeoRenderer), drawn by the geckolib:geckolib special model named in items/dragon_crystal_enchanting_table.json
public final class DragonCrystalEnchantingTableClientExtensions {
    private DragonCrystalEnchantingTableClientExtensions() {
    }

    public static GeoRenderProvider provider() {
        return new GeoRenderProvider() {
            private DragonCrystalEnchantingTableDisplayItemRenderer renderer;

            @Override
            public GeoItemRenderer<?> getGeoItemRenderer() {
                if (this.renderer == null) {
                    this.renderer = new DragonCrystalEnchantingTableDisplayItemRenderer();
                }
                return this.renderer;
            }
        };
    }
}
