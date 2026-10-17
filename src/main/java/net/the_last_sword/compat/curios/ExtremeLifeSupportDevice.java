package net.the_last_sword.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

//极限维生装置 - belt槽位
public class ExtremeLifeSupportDevice extends Item implements ICurioItem {

    private static final UUID ARMOR_TOUGHNESS_UUID = UUID.fromString("d4e5f6a7-b8c9-4d1e-8f3a-4b5c6d7e8f90");

    public ExtremeLifeSupportDevice() {
        super(net.the_last_sword.mv.RegIds.stamp(new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    //Forge Energy 能力 -> 已迁移至 RegisterCapabilitiesEvent (Capabilities.EnergyStorage.ITEM)

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    private static IEnergyStorage energyOf(ItemStack stack) {
        var handler = Capabilities.Energy.ITEM.getCapability(stack, net.neoforged.neoforge.transfer.access.ItemAccess.forStack(stack));
        return handler == null ? null : IEnergyStorage.of(handler);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage energy = energyOf(stack);
        if (energy == null) {
            return 0;
        }
        int maxEnergy = energy.getMaxEnergyStored();
        if (maxEnergy == 0) return 0;
        return Math.round(13.0F * energy.getEnergyStored() / maxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = energyOf(stack);
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
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, Identifier uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();

        //盔甲韧性+100
        modifiers.put(Attributes.ARMOR_TOUGHNESS,
            new AttributeModifier(Identifier.fromNamespaceAndPath("the_last_sword", "extreme_life_support_armor_toughness"),
                TheLastSwordConfiguration.getCuriosExtremeLifeSupportArmorToughnessSafely(),
                AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag) {
        super.appendHoverText(stack, level, display, consumer, flag);
        List<Component> tooltip = new ArrayList<>();
        //能量信息
        IEnergyStorage energy = energyOf(stack);
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
        tooltip.forEach(consumer);
    }

    //基于客户端玩家当前生命比例显示损伤百分比与额外Buff等级
    private static void appendCurrentValueLine(List<Component> tooltip) {
        Player player = ClientPlayer.get();
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

    //1.21.6+: @OnlyIn strips nothing (the layer removes it), so this method now loads on a dedicated server too, and
    //`Player p = Minecraft.getInstance().player` makes the verifier load LocalPlayer there. The client lookup lives in
    //a nested class, loaded only when a client tooltip calls it.
    private static final class ClientPlayer {
        static Player get() {
            return Minecraft.getInstance().player;
        }
    }
}
