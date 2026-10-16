package net.the_last_sword.mixin;

import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.emc.EMCMappingHandler;
import net.the_last_sword.compat.projecte.SmithingLevelEmcProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Pseudo
// 1.21.1：ProjectE 的 NBTManager 改为 DataComponentManager（同名 getEmcValue）。
@Mixin(targets = "moze_intel.projecte.emc.components.DataComponentManager", remap = false)
public abstract class SmithingLevelEmcMixin {
    // 只有高阶形态有价值时，也要允许带附魔或名称的物品进入后续修正流程。
    @Redirect(method = "getEmcValue", at = @At(value = "INVOKE",
            target = "Lmoze_intel/projecte/emc/EMCMappingHandler;getStoredEmcValue(Lmoze_intel/projecte/api/ItemInfo;)J"),
            remap = false)
    private static long theLastSword$lookupLevel(ItemInfo info) {
        if (EMCMappingHandler.hasEmcValue(info)) {
            return EMCMappingHandler.getStoredEmcValue(info);
        }
        return EMCMappingHandler.getStoredEmcValue(SmithingLevelEmcProcessor.normalizeLevel(info));
    }
}
