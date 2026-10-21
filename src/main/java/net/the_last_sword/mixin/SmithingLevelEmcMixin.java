package net.the_last_sword.mixin;

import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.api.components.IDataComponentProcessor;
import moze_intel.projecte.config.MappingConfig;
import moze_intel.projecte.emc.EMCMappingHandler;
import net.the_last_sword.compat.projecte.SmithingLevelEmcProcessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Pseudo
// 1.21.1：ProjectE 的 NBTManager 改为 DataComponentManager（同名 getEmcValue）。
@Mixin(targets = "moze_intel.projecte.emc.components.DataComponentManager", remap = false)
public abstract class SmithingLevelEmcMixin {
    @Shadow @Final
    private static List<IDataComponentProcessor> processors;

    // 只有高阶形态有价值时，也要允许带附魔或名称的物品进入后续修正流程。
    @Inject(method = "getEmcValue", at = @At("HEAD"), cancellable = true, remap = false)
    private static void theLastSword$lookupLevel(ItemInfo info, CallbackInfoReturnable<Long> callback) {
        if (EMCMappingHandler.hasEmcValue(info)) {
            return;
        }
        ItemInfo normalized = SmithingLevelEmcProcessor.normalizeLevel(info);
        if (normalized == info) {
            return;
        }
        long emc = EMCMappingHandler.getStoredEmcValue(normalized);
        if (emc <= 0) {
            return;
        }
        // 使用等级基础价进入原处理器顺序，仍以完整标签计算耐久、附魔等修正。
        for (IDataComponentProcessor processor : processors) {
            if (!MappingConfig.isEnabled(processor)) {
                continue;
            }
            try {
                emc = processor.recalculateEMC(info, emc);
            } catch (ArithmeticException exception) {
                // 1.21.1：与 ProjectE 的 DataComponentManager.getEmcValue 一致，溢出时返回 0。
                callback.setReturnValue(0L);
                return;
            }
            if (emc <= 0) {
                callback.setReturnValue(0L);
                return;
            }
        }
        callback.setReturnValue(emc);
    }
}
