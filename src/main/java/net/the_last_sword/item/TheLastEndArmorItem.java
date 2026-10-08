package net.the_last_sword.item;

import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.the_last_sword.init.ModKeyMappings;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.init.ModAttributes;
import net.the_last_sword.util.nbt.ItemLevelHelper;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Locale;

/**
 * 终焉护甲抽象基类
 * 基于NBT系统，便于迁移到NeoForge 1.21+
 */
public abstract class TheLastEndArmorItem extends ArmorItem {

    protected TheLastEndArmorItem(Holder<ArmorMaterial> material, Type type, Properties properties) {
        super(material, type, properties);
    }

    //获取初始等级（子类可覆盖）
    protected int getDefaultLevel() {
        return 0; // 龙水晶盔甲初始等级0级
    }

    //获取物品等级（自动初始化）
    protected int getItemLevel(ItemStack stack) {
        ItemLevelHelper.ensureInitialized(stack, getDefaultLevel());
        return ItemLevelHelper.getLevel(stack);
    }

    //动态属性修饰符，根据等级计算（物品没有 ATTRIBUTE_MODIFIERS 组件时使用）
    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        EquipmentSlot slot = this.getEquipmentSlot();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
        String slotName = slot.getName();
        ItemAttributeModifiers.Builder modifiers = ItemAttributeModifiers.builder();

        int itemLevel = getItemLevel(stack);
        int slotIndex = slot.getIndex();

        //计算最终护甲值（基础值 + 等级加成，根据等级区间选择配置）
        int[] baseArmorValues = getBaseArmorValues();
        double armorIncrease = getArmorLevelIncrease(itemLevel);
        double finalArmor = baseArmorValues[slotIndex] + (itemLevel * armorIncrease);

        //计算最终韧性值（基础值 + 等级加成，根据等级区间选择配置）
        double baseToughness = getBaseToughness();
        double toughnessIncrease = getToughnessLevelIncrease(itemLevel);
        double finalToughness = baseToughness + (itemLevel * toughnessIncrease);

        //添加合并后的护甲修饰符
        modifiers.add(Attributes.ARMOR, new AttributeModifier(
            ResourceLocation.fromNamespaceAndPath("the_last_sword", "armor_modifier_" + slotName),
            finalArmor,
            AttributeModifier.Operation.ADD_VALUE
        ), group);

        //添加合并后的韧性修饰符
        modifiers.add(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(
            ResourceLocation.fromNamespaceAndPath("the_last_sword", "armor_toughness_" + slotName),
            finalToughness,
            AttributeModifier.Operation.ADD_VALUE
        ), group);

        //击退抗性
        float knockbackResistance = this.getMaterial().value().knockbackResistance();
        if (knockbackResistance > 0) {
            modifiers.add(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", "armor_knockback_resistance_" + slotName),
                knockbackResistance,
                AttributeModifier.Operation.ADD_VALUE
            ), group);
        }

        String idName = getArmorName().toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9._-]", "_");

        //生命值加成
        if (itemLevel > 0) {
            double healthIncrease = getHealthLevelIncrease(itemLevel);
            double healthBonus = itemLevel * healthIncrease;
            modifiers.add(Attributes.MAX_HEALTH, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", idName + "_health_" + slotName),
                healthBonus,
                AttributeModifier.Operation.ADD_VALUE
            ), group);
        }

        //肃正防御加成（动态计算，根据等级区间选择配置）
        if (itemLevel > 0) {
            double justifiedDefenceIncrease = getJustifiedDefenceLevelIncrease(itemLevel);
            double justifiedDefence = itemLevel * justifiedDefenceIncrease;
            if (justifiedDefence > 0) {
                modifiers.add(ModAttributes.MAX_JUSTIFIED_DEFENCE, new AttributeModifier(
                    ResourceLocation.fromNamespaceAndPath("the_last_sword", idName + "_max_shield_" + slotName),
                    justifiedDefence,
                    AttributeModifier.Operation.ADD_VALUE
                ), group);
            }
        }

        return modifiers.build();
    }

    //Tooltip显示等级和特定信息
    @Override
    public void appendHoverText(ItemStack itemstack, net.minecraft.world.item.Item.TooltipContext level, List<Component> list, TooltipFlag flag) {
        super.appendHoverText(itemstack, level, list, flag);

        //显示等级（包括0级）
        int itemLevel = getItemLevel(itemstack);
        list.add(Component.translatable("item_tooltip.the_last_sword.level").append(" " + itemLevel));

        //子类特定的tooltip
        appendSpecificTooltip(itemstack, level.level(), list, flag);

        //按键提示：打开防御配置
        list.add(Component.translatable("item_tooltip.the_last_sword.open_defence_config_key")
                .append(" ")
                .append(ModKeyMappings.OPEN_DEFENCE_CONFIG.getKey().getDisplayName().getString()));
    }

    //掉落物保护 - 不会被任何伤害摧毁
    @Override
    public boolean canBeHurtBy(ItemStack stack, DamageSource damageSource) {
        return false;
    }

    // ==================== 抽象方法：子类必须实现 ====================

    //获取基础护甲值数组 [靴子, 护腿, 胸甲, 头盔]
    protected abstract int[] getBaseArmorValues();

    //获取基础韧性值
    protected abstract double getBaseToughness();

    //获取护甲名称（用于属性修饰符）
    protected abstract String getArmorName();

    // ==================== 可选方法：子类可以覆盖 ====================

    //获取每级护甲增加值（根据等级区间选择配置）
    protected double getArmorLevelIncrease(int itemLevel) {
        return itemLevel < 6
            ? TheLastSwordConfiguration.getArmorIncreaseLowLevelSafely()
            : TheLastSwordConfiguration.getArmorIncreaseHighLevelSafely();
    }

    //获取每级韧性增加值（根据等级区间选择配置）
    protected double getToughnessLevelIncrease(int itemLevel) {
        return itemLevel < 6
            ? TheLastSwordConfiguration.getToughnessIncreaseLowLevelSafely()
            : TheLastSwordConfiguration.getToughnessIncreaseHighLevelSafely();
    }

    //获取每级生命值增加值（根据等级区间选择配置）
    protected double getHealthLevelIncrease(int itemLevel) {
        return itemLevel < 6
            ? TheLastSwordConfiguration.getHealthIncreaseLowLevelSafely()
            : TheLastSwordConfiguration.getHealthIncreaseHighLevelSafely();
    }

    //获取每级肃正防御增加值（根据等级区间选择配置）
    protected double getJustifiedDefenceLevelIncrease(int itemLevel) {
        return itemLevel < 6
            ? TheLastSwordConfiguration.getJustifiedDefenceIncreaseLowLevelSafely()
            : TheLastSwordConfiguration.getJustifiedDefenceIncreaseHighLevelSafely();
    }

    //添加子类特定的tooltip
    protected void appendSpecificTooltip(ItemStack itemstack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        // 子类可以覆盖此方法添加特定tooltip
    }
}
