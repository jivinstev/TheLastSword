package net.the_last_sword.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

//极限维生装置 - belt槽位
public class ExtremeLifeSupportDevice extends Item implements ICurioItem {

    private static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("d4e5f6a7-b8c9-4d1e-8f3a-4b5c6d7e8f90");

    public ExtremeLifeSupportDevice() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    //Forge Energy 能力 -> 已迁移至 RegisterCapabilitiesEvent (Capabilities.EnergyStorage.ITEM)

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return 0;
        }
        int maxEnergy = energy.getMaxEnergyStored();
        if (maxEnergy == 0) return 0;
        return Math.round(13.0F * energy.getEnergyStored() / maxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy == null) {
            return 0x8B00FF;
        }
        int maxEnergy = energy.getMaxEnergyStored();
        if (maxEnergy == 0) return 0x8B00FF;
        float ratio = (float) energy.getEnergyStored() / maxEnergy;
        if (ratio < 0.25F) {
            return 0xFF0000;
        } else if (ratio < 0.5F) {
            return 0xFF8C00;
        } else if (ratio < 0.75F) {
            return 0x9B30FF;
        } else {
            return 0xBF00FF;
        }
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();

        //盔甲韧性+100
        modifiers.put(Attributes.ARMOR_TOUGHNESS,
            new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_last_sword", "extreme_life_support_armor_toughness"),
                TheLastSwordConfiguration.getCuriosExtremeLifeSupportArmorToughnessSafely(),
                AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        //能量信息
        IEnergyStorage energy = stack.getCapability(Capabilities.EnergyStorage.ITEM);
        if (energy != null) {
            tooltip.add(Component.translatable("item_tooltip.the_last_sword.energy")
                .append(": §a" + energy.getEnergyStored() + " §r/ " + energy.getMaxEnergyStored() + " FE"));
        }
        int energyCost = TheLastSwordConfiguration.getCuriosExtremeLifeSupportEnergyCostSafely();
        String thresholdPercent = String.format("%.0f",
            TheLastSwordConfiguration.getCuriosExtremeLifeSupportTierThresholdSafely() * 100);
        tooltip.add(Component.translatable("item_tooltip.the_last_sword.extreme_life_support_device", energyCost, thresholdPercent));
        appendCurrentValueLine(tooltip);
        tooltip.add(Component.translatable("item_tooltip_lore.the_last_sword.extreme_life_support_device")
            .withStyle(ChatFormatting.GRAY));
    }

    //基于客户端玩家当前生命比例显示损伤百分比与额外Buff等级
    @OnlyIn(Dist.CLIENT)
    private static void appendCurrentValueLine(List<Component> tooltip) {
        Player player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        float maxHealth = player.getMaxHealth();
        if (maxHealth <= 0) {
            return;
        }
        double lostRatio = 1.0 - (player.getHealth() / maxHealth);
        if (lostRatio < 0) {
            lostRatio = 0;
        }
        double threshold = TheLastSwordConfiguration.getCuriosExtremeLifeSupportTierThresholdSafely();
        int bonusLevel = threshold > 0 ? (int) (lostRatio / threshold) : 0;
        String lostStr = String.format("%.1f", lostRatio * 100);
        tooltip.add(Component.translatable("item_tooltip.the_last_sword.extreme_life_support_device.current",
            lostStr, bonusLevel).withStyle(ChatFormatting.YELLOW));
    }
}
