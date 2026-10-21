package net.the_last_sword.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.compat.CompatCheck;
// ProjectE: no build for this Minecraft, ProjectECapabilities is not compiled (drops.txt)
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.item.AncientEnergyCore;
import net.the_last_sword.item.DisposableEnergyBattery;
import net.the_last_sword.item.DragonArmorItem;
import net.the_last_sword.item.ZeroPointEngine;
import net.the_last_sword.util.nbt.ItemEnergyStorage;

//能力注册（1.20.1 的 initCapabilities / getCapability 在 1.21.1 改为 RegisterCapabilitiesEvent）
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID)
public final class ModCapabilities {
    private ModCapabilities() {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.EnergyStorage.ITEM,
            (stack, context) -> ZeroPointEngine.createEnergyStorage(stack), ModItems.ZERO_POINT_ENGINE.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
            (stack, context) -> AncientEnergyCore.createEnergyStorage(stack), ModItems.ANCIENT_ENERGY_CORE.get());
        //一次性电池：新物品默认满电（同 1.20.1）
        event.registerItem(Capabilities.EnergyStorage.ITEM,
            (stack, context) -> new ItemEnergyStorage(stack, DisposableEnergyBattery::getMaxEnergy, true),
            ModItems.DISPOSABLE_ENERGY_BATTERY.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
            (stack, context) -> DragonArmorItem.createEnergyStorage(stack),
            ModItems.DRAGON_ARMOR_HELMET.get(), ModItems.DRAGON_ARMOR_CHESTPLATE.get(),
            ModItems.DRAGON_ARMOR_LEGGINGS.get(), ModItems.DRAGON_ARMOR_BOOTS.get());
        event.registerItem(Capabilities.EnergyStorage.ITEM,
            (stack, context) -> new ItemEnergyStorage(stack, TheLastSwordConfiguration::getCuriosExtremeLifeSupportMaxEnergySafely),
            ModItems.EXTREME_LIFE_SUPPORT_DEVICE.get());

        //附魔台：能量与分面物品栏
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.DRAGON_CRYSTAL_ENCHANTING_TABLE.get(),
            (blockEntity, side) -> blockEntity.getEnergyStorage());
        // TLS-049: on Forge 1.20.1 a null side fell through to BaseContainerBlockEntity's unsided handler (the whole
        // inventory), and the table's menu binds through exactly that query; returning null left the menu on a
        // detached 2-slot handler, so items put in were lost on close and EnchantmentApplyPacket read an empty slot.
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DRAGON_CRYSTAL_ENCHANTING_TABLE.get(),
            (blockEntity, side) -> side == null ? new InvWrapper(blockEntity) : blockEntity.getItemHandler(side));
        event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, ModBlockEntities.DRAGON_CRYSTAL_SMITHING_TABLE.get(),
            (blockEntity, side) -> side == null ? null : blockEntity.getItemHandler(side));
        //可选联动：ProjectE 的充能能力仅在其加载时注册
        { /* ProjectE: no build for this Minecraft (drops.txt) */ }
    }
}
