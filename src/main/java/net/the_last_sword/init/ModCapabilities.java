package net.the_last_sword.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.compat.CompatCheck;
import net.the_last_sword.compat.projecte.ProjectECapabilities;
import net.the_last_sword.item.ZeroPointEngine;

//能力注册（1.20.1 的 initCapabilities / getCapability 在 1.21.1 改为 RegisterCapabilitiesEvent）
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public final class ModCapabilities {
    private ModCapabilities() {
    }

    @SubscribeEvent
    public static void register(RegisterCapabilitiesEvent event) {
        event.registerItem(Capabilities.EnergyStorage.ITEM,
            (stack, context) -> ZeroPointEngine.createEnergyStorage(stack), ModItems.ZERO_POINT_ENGINE.get());
        //可选联动：ProjectE 的充能能力仅在其加载时注册
        if (CompatCheck.isTransmutationLoaded()) {
            ProjectECapabilities.register(event);
        }
    }
}
