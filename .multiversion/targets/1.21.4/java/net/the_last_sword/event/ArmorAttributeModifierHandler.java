package net.the_last_sword.event;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.item.DragonCultPriestArmorItem;
import net.the_last_sword.item.TheLastEndArmorItem;

import java.util.Objects;

/**
 * Multi-version layer (1.21.4), not the authors' code. On 1.21.1 an ArmorItem had no ATTRIBUTE_MODIFIERS component, so
 * NeoForge asked Item.getDefaultAttributeModifiers(stack) -- where the level-based armour stats and the priest armour
 * bonuses live. From 1.21.2 ArmorMaterial.humanoidProperties always sets that component and NeoForge reads it
 * instead, so the override never runs. This applies it again: for a stack still carrying the item's own default
 * component (a stack someone gave other modifiers keeps them, as on 1.21.1), the override's modifiers replace the
 * defaults. HIGHEST priority, so other listeners still see and adjust the result.
 */
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class ArmorAttributeModifierHandler {
    private ArmorAttributeModifierHandler() {
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        Item item = stack.getItem();
        if (!(item instanceof TheLastEndArmorItem) && !(item instanceof DragonCultPriestArmorItem)) {
            return;
        }
        if (!Objects.equals(stack.get(DataComponents.ATTRIBUTE_MODIFIERS), item.components().get(DataComponents.ATTRIBUTE_MODIFIERS))) {
            return;
        }
        ItemAttributeModifiers modifiers = item.getDefaultAttributeModifiers(stack);
        event.clearModifiers();
        for (ItemAttributeModifiers.Entry entry : modifiers.modifiers()) {
            event.addModifier(entry.attribute(), entry.modifier(), entry.slot());
        }
    }
}
