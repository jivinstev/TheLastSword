package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.the_last_sword.configuration.TheLastSwordConfiguration;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

/**
 * 龙水晶盔甲
 * 基于NBT系统，使用ItemLevelHelper和ItemJustifiedDefenceHelper
 */
public abstract class DragonCrystalArmorItem extends TheLastEndArmorItem {

    public DragonCrystalArmorItem(ArmorType type, Properties props) {
        //耐久单位 74 × 各部位基数 [13, 15, 16, 11] = [962, 1110, 1184, 814]，即下界合金各部位耐久的 2 倍
        super(new ArmorMaterial(
            74,
            Map.of(ArmorType.HELMET, 4, ArmorType.CHESTPLATE, 6, ArmorType.LEGGINGS, 6, ArmorType.BOOTS, 4),
            22,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            5f,
            1f,
            TagKey.create(Registries.ITEM, ResourceLocation.parse("the_last_sword:repairs_dragon_crystal_armor")),
            ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse("the_last_sword:dragon_crystal_armor"))
        ), type, props.fireResistant().rarity(Rarity.RARE));
    }

    // ==================== 实现抽象方法 ====================

    @Override
    protected int[] getBaseArmorValues() {
        return new int[]{4, 6, 6, 4}; // [靴子, 护腿, 胸甲, 头盔]
    }

    @Override
    protected double getBaseToughness() {
        return 5.0;
    }

    @Override
    protected String getArmorName() {
        return "DragonCrystalArmor";
    }

    @Override
    protected void appendSpecificTooltip(ItemStack itemstack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.add(Component.translatable(getSpecificTooltipKey()));

        //水晶守护技能说明（显示刷新时间）
        int refreshInterval = TheLastSwordConfiguration.getCrystalGuardRefreshIntervalSafely();
        int seconds = refreshInterval / 20;
        list.add(Component.translatable("item_tooltip.the_last_sword.dragon_crystal_armor_skill", seconds));

        list.add(Component.translatable("item_tooltip_lore.the_last_sword.dragon_crystal_armor")
            .withStyle(ChatFormatting.GRAY));
    }

    protected abstract String getSpecificTooltipKey();

    // ==================== 全套检查方法 ====================

    //检查实体是否穿戴全套龙水晶盔甲
    public static boolean isFullSet(LivingEntity entity) {
        return !entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty() &&
               entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof DragonCrystalArmorItem.Helmet &&
               !entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty() &&
               entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonCrystalArmorItem.Chestplate &&
               !entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty() &&
               entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DragonCrystalArmorItem.Leggings &&
               !entity.getItemBySlot(EquipmentSlot.FEET).isEmpty() &&
               entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof DragonCrystalArmorItem.Boots;
    }

    // ==================== 4个子类：头盔、胸甲、护腿、靴子 ====================

    public static class Helmet extends DragonCrystalArmorItem {
        public Helmet() {
            super(ArmorType.HELMET, net.the_last_sword.mv.RegIds.stamp(new Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.HEAD;
        }

        @Override
        protected String getSpecificTooltipKey() {
            return "item_tooltip.the_last_sword.dragon_crystal_armor_helmet";
        }
    }

    public static class Chestplate extends DragonCrystalArmorItem {
        public Chestplate() {
            super(ArmorType.CHESTPLATE, net.the_last_sword.mv.RegIds.stamp(new Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.CHEST;
        }

        @Override
        protected String getSpecificTooltipKey() {
            return "item_tooltip.the_last_sword.dragon_crystal_armor_chestplate";
        }
    }

    public static class Leggings extends DragonCrystalArmorItem {
        public Leggings() {
            super(ArmorType.LEGGINGS, net.the_last_sword.mv.RegIds.stamp(new Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.LEGS;
        }

        @Override
        protected String getSpecificTooltipKey() {
            return "item_tooltip.the_last_sword.dragon_crystal_armor_leggings";
        }
    }

    public static class Boots extends DragonCrystalArmorItem {
        public Boots() {
            super(ArmorType.BOOTS, net.the_last_sword.mv.RegIds.stamp(new Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.FEET;
        }

        @Override
        protected String getSpecificTooltipKey() {
            return "item_tooltip.the_last_sword.dragon_crystal_armor_boots";
        }
    }
}
