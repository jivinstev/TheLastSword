package net.the_last_sword.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.renderer.DragonCultPaladinArmorRenderer;
import net.the_last_sword.client.renderer.DragonCultPriestArmorRenderer;
import net.the_last_sword.client.renderer.DragonCultistArmorRenderer;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

import java.util.function.Supplier;

//客户端专用：盔甲渲染扩展，避免服务端加载客户端类
public final class DragonCultArmorClientExtensions {

    private DragonCultArmorClientExtensions() {
    }

    public static IClientItemExtensions paladin() {
        return create(DragonCultPaladinArmorRenderer::new);
    }

    public static IClientItemExtensions priest() {
        return create(DragonCultPriestArmorRenderer::new);
    }

    public static IClientItemExtensions cultist() {
        return create(DragonCultistArmorRenderer::new);
    }

    private static IClientItemExtensions create(Supplier<GeoArmorRenderer<?>> factory) {
        return new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType,
                                               Model original) {
                if (this.renderer == null) {
                    this.renderer = factory.get();
                }
                Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
                EquipmentSlot equipmentSlot = equippable != null ? equippable.slot() : EquipmentSlot.CHEST;
                Minecraft minecraft = Minecraft.getInstance();
                this.renderer.prepForRender(minecraft.player, itemStack, equipmentSlot,
                    original instanceof HumanoidModel<?> humanoid ? humanoid : null,
                    minecraft.renderBuffers().bufferSource(), 0.0F, 0.0F, 0.0F);
                return this.renderer;
            }
        };
    }
}
