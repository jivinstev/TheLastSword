package net.the_last_sword.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Consumer;
import java.util.UUID;

//维度探索者 - feet槽位
public class DimensionExplorer extends Item implements ICurioItem {

    private static final UUID SPEED_UUID = UUID.fromString("a1b2c3d4-e5f6-7a8b-9c0d-1e2f3a4b5c6d");

    public DimensionExplorer() {
        super(net.the_last_sword.mv.RegIds.stamp(new Item.Properties()).stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    // Port, TLS-057: Curios 10+ reads only these (CurioModifierDefaults); the authors' per-slot override below has no slot use.
    @Override
    public top.theillusivec4.curios.api.CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioModifierDefaults.of(getAttributeModifiers(null, null, stack));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, Identifier uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();

        //移动速度+50%
        modifiers.put(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(Identifier.fromNamespaceAndPath("the_last_sword", "dimension_explorer_speed"), 0.5,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, display, tooltip, flag);
        int jumpLevel = TheLastSwordConfiguration.getCuriosDimensionExplorerJumpAmplifierSafely() + 1;
        int hasteLevel = TheLastSwordConfiguration.getCuriosDimensionExplorerHasteAmplifierSafely() + 1;
        int speedLevel = TheLastSwordConfiguration.getCuriosDimensionExplorerSpeedAmplifierSafely() + 1;
        String emergencyHealth = String.format("%.1f",
            TheLastSwordConfiguration.getCuriosDimensionExplorerEmergencyHealHealthSafely());
        int emergencyFood = TheLastSwordConfiguration.getCuriosDimensionExplorerEmergencyFoodLevelSafely();
        String effectSeconds = String.format("%.1f",
            TheLastSwordConfiguration.getCuriosDimensionExplorerEffectDurationSafely() / 20.0);
        String cooldownSeconds = String.format("%.1f",
            TheLastSwordConfiguration.getCuriosDimensionExplorerCooldownSafely() / 20.0);
        tooltip.accept(Component.translatable("item_tooltip.the_last_sword.dimension_explorer",
            jumpLevel, emergencyHealth, emergencyFood, hasteLevel, speedLevel, effectSeconds, cooldownSeconds));
        tooltip.accept(Component.translatable("item_tooltip_lore.the_last_sword.dimension_explorer")
            .withStyle(ChatFormatting.GRAY));
    }
}
