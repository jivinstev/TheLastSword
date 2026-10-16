package net.the_last_sword.compat.projecte;

import moze_intel.projecte.api.ItemInfo;
import moze_intel.projecte.api.components.DataComponentProcessor;
import moze_intel.projecte.api.components.IDataComponentProcessor;
import moze_intel.projecte.emc.EMCMappingHandler;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.component.CustomData;
import net.the_last_sword.util.nbt.ItemLevelHelper;

// 1.21.1：ProjectE 的 NBT 处理器改为数据组件处理器；锻造等级存于 CUSTOM_DATA 组件。
@DataComponentProcessor(priority = Integer.MAX_VALUE)
public class SmithingLevelEmcProcessor implements IDataComponentProcessor {
    @Override
    public String getName() {
        return "DragonCrystalSmithingLevels";
    }

    @Override
    public String getDescription() {
        return "Preserves smithing levels and uses their mapped EMC before other modifiers.";
    }

    @Override
    public String getTranslationKey() {
        return "data_component_processor.the_last_sword.smithing_levels";
    }

    @Override
    public boolean hasPersistentComponents() {
        return true;
    }

    @Override
    public void collectPersistentComponents(ItemInfo info, DataComponentPatch.Builder builder) {
        CompoundTag tag = levelTag(info);
        if (tag == null) {
            return;
        }
        CompoundTag persistent = new CompoundTag();
        persistent.putInt(ItemLevelHelper.LEVEL_TAG, tag.getInt(ItemLevelHelper.LEVEL_TAG));
        builder.set(DataComponents.CUSTOM_DATA, CustomData.of(persistent));
    }

    @Override
    public long recalculateEMC(ItemInfo info, long currentEMC) {
        if (EMCMappingHandler.hasEmcValue(info)) {
            return currentEMC;
        }
        ItemInfo normalized = normalizeLevel(info);
        // 无法推导的等级不能回退成零级价格。
        if (normalized != info && EMCMappingHandler.getStoredEmcValue(normalized) <= 0) {
            return 0;
        }
        // 基础值已在查询入口选择，不能覆盖此前执行的耐久修正。
        return currentEMC;
    }

    public static ItemInfo normalizeLevel(ItemInfo info) {
        CompoundTag tag = levelTag(info);
        if (tag == null) {
            return info;
        }
        int level = tag.getInt(ItemLevelHelper.LEVEL_TAG);
        DataComponentPatch normalized = DataComponentPatch.EMPTY;
        if (level != 0) {
            CompoundTag levelOnly = new CompoundTag();
            levelOnly.putInt(ItemLevelHelper.LEVEL_TAG, level);
            normalized = DataComponentPatch.builder().set(DataComponents.CUSTOM_DATA, CustomData.of(levelOnly)).build();
        }
        return ItemInfo.fromItem(info.getItem(), normalized);
    }

    private static CompoundTag levelTag(ItemInfo info) {
        CustomData data = info.getOrNull(DataComponents.CUSTOM_DATA);
        if (data == null) {
            return null;
        }
        CompoundTag tag = data.copyTag();
        return tag.contains(ItemLevelHelper.LEVEL_TAG, Tag.TAG_INT) ? tag : null;
    }
}
