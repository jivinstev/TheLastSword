package net.the_last_sword.compat.projecte;

import moze_intel.projecte.api.capabilities.PECapabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.the_last_sword.init.ModItems;

// 1.21.1：法杖的 ProjectE 充能能力（1.20.1 为 initCapabilities 返回的 ChargeProvider）。只在 ProjectE 加载时调用。
public final class ProjectECapabilities {
    private ProjectECapabilities() {
    }

    public static void register(RegisterCapabilitiesEvent event) {
        if (ModItems.STAFF_OF_UNIVERSAL_TRANSMUTATION != null) {
            event.registerItem(PECapabilities.CHARGE_ITEM_CAPABILITY,
                (stack, context) -> TransmutationStaffActions.CHARGE, ModItems.STAFF_OF_UNIVERSAL_TRANSMUTATION.get());
        }
    }
}
