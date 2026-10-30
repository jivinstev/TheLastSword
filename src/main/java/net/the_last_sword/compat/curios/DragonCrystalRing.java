package net.the_last_sword.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.init.ModAttributes;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.function.Consumer;
import java.util.UUID;

//龙水晶指环 - ring槽位
public class DragonCrystalRing extends Item implements ICurioItem {

    private static final UUID RING_UUID = UUID.fromString("c3d4e5f6-a7b8-6c7d-0e1f-2a3b4c5d6e7f");

    public DragonCrystalRing() {
        super(net.the_last_sword.mv.RegIds.stamp(new Item.Properties()).stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    // Port, TLS-057: Curios 10+ reads only these (CurioModifierDefaults); the authors' per-slot override below has no slot use.
    @Override
    public top.theillusivec4.curios.api.CurioAttributeModifiers getDefaultCurioAttributeModifiers(ItemStack stack) {
        return CurioModifierDefaults.of(getAttributeModifiers(null, null, stack));
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, Identifier uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();

        //最大肃正防御 +6
        modifiers.put(ModAttributes.MAX_JUSTIFIED_DEFENCE,
            new AttributeModifier(Identifier.fromNamespaceAndPath("the_last_sword", "dragon_crystal_ring_defence"), 6.0,
                AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, display, tooltip, flag);
        double multiplier = TheLastSwordConfiguration.getCuriosDragonCrystalRingDamageMultiplierSafely();
        String bonusPercent = String.format("%+.0f", (multiplier - 1.0) * 100);
        tooltip.accept(Component.translatable("item_tooltip.the_last_sword.dragon_crystal_ring", bonusPercent));
        tooltip.accept(Component.translatable("item_tooltip_lore.the_last_sword.dragon_crystal_ring")
            .withStyle(ChatFormatting.GRAY));
    }
}
