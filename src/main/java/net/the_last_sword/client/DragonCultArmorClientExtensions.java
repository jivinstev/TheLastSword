package net.the_last_sword.client;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.client.renderer.DragonCultPaladinArmorRenderer;
import net.the_last_sword.client.renderer.DragonCultPriestArmorRenderer;
import net.the_last_sword.client.renderer.DragonCultistArmorRenderer;
import com.geckolib.animatable.client.GeoRenderProvider;
import com.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Supplier;

//客户端专用：盔甲渲染扩展，避免服务端加载客户端类
//1.21.4: NeoForge's getHumanoidArmorModel no longer carries the entity or slot; GeckoLib 4.8 renders geo armor itself
//(its HumanoidArmorLayer hook calls GeoRenderProvider.getGeoArmorRenderer and prepForRender with the real entity),
//so these factories return the GeoRenderProvider the items hand to GeoItem.createGeoRenderer.
public final class DragonCultArmorClientExtensions {

    private DragonCultArmorClientExtensions() {
    }

    public static GeoRenderProvider paladin() {
        return create(DragonCultPaladinArmorRenderer::new);
    }

    public static GeoRenderProvider priest() {
        return create(DragonCultPriestArmorRenderer::new);
    }

    public static GeoRenderProvider cultist() {
        return create(DragonCultistArmorRenderer::new);
    }

    public static GeoRenderProvider create(Supplier<GeoArmorRenderer<?, ?>> factory) {
        return new GeoRenderProvider() {
            private GeoArmorRenderer<?, ?> renderer;

            @Override
            //GeckoLib 5.3: the provider is asked with the stack and slot only (no render state, layer or base model)
            public GeoArmorRenderer<?, ?> getGeoArmorRenderer(ItemStack itemStack, EquipmentSlot equipmentSlot) {
                if (this.renderer == null) {
                    this.renderer = factory.get();
                }
                return this.renderer;
            }
        };
    }
}
