package net.the_last_sword.compat.curios;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import top.theillusivec4.curios.api.CurioAttributeModifiers;

// Port (not the authors' code), TLS-057: from Curios 10 (1.21.4) a curio's attribute modifiers are read only through
// ICurioItem.getAttributeModifiers(ItemStack) -- its curios:attribute_modifiers item data, or, when the stack has none,
// ICurioItem.getDefaultCurioAttributeModifiers(stack). The per-slot getAttributeModifiers(SlotContext, id, stack) the
// authors override (Curios 9) is never called. Each accessory hands its modifiers on through
// getDefaultCurioAttributeModifiers, for any slot (the authors' code never looked at the slot); Curios suffixes each
// modifier id with the slot it is worn in.
public final class CurioModifierDefaults {

    private CurioModifierDefaults() {
    }

    public static CurioAttributeModifiers of(Multimap<Holder<Attribute>, AttributeModifier> modifiers) {
        CurioAttributeModifiers.Builder builder = CurioAttributeModifiers.builder();
        modifiers.forEach(builder::addModifier);
        return builder.build();
    }
}
