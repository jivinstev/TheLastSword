package net.the_last_sword.compat.irons_spellbooks;

import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.item.DragonArmorItem;
import net.the_last_sword.item.DragonCultPriestArmorItem;
import net.the_last_sword.item.DragonCrystalArmorItem;
import net.the_last_sword.item.DragonCrystalSword;
import net.the_last_sword.item.DragonSword;
import net.the_last_sword.item.PriestStaffItem;
import net.the_last_sword.item.TheLastSword;

@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID)
public final class IronsSpellsCompat {

    private static final ResourceLocation MAX_MANA_ID =
        ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "max_mana");
    private static final ResourceLocation SPELL_POWER_ID =
        ResourceLocation.fromNamespaceAndPath("irons_spellbooks", "spell_power");
    private static ResourceLocation modId(String path) {
        return ResourceLocation.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, path);
    }

    private static Holder<Attribute> attribute(ResourceLocation id) {
        return BuiltInRegistries.ATTRIBUTE.get(ResourceKey.create(Registries.ATTRIBUTE, id)).orElse(null);
    }

    @javax.annotation.Nullable
    private static EquipmentSlot slotOf(net.minecraft.world.item.ItemStack stack) {
        net.minecraft.world.item.equipment.Equippable equippable = stack.get(net.minecraft.core.component.DataComponents.EQUIPPABLE);
        return equippable != null ? equippable.slot() : null;
    }

    private static boolean isSlottedArmor(net.minecraft.world.item.ItemStack stack) {
        return stack.getItem() instanceof DragonCultPriestArmorItem || stack.getItem() instanceof DragonCrystalArmorItem
                || stack.getItem() instanceof DragonArmorItem;
    }

    private IronsSpellsCompat() {
    }

    @SubscribeEvent
    public static void onItemAttributeModifiers(ItemAttributeModifierEvent event) {
        //1.21.1's ArmorItem.getEquipmentSlot() was the armour type's slot; IItemExtension.getEquipmentSlot(stack) is a
        //null-by-default override hook (null = "let vanilla decide"), so the slot comes from the EQUIPPABLE component
        //the armour piece carries since 1.21.2; a stack without one (never an armour piece here) adds nothing
        if (isSlottedArmor(event.getItemStack()) && slotOf(event.getItemStack()) == null) {
            return;
        }
        if (event.getItemStack().getItem() instanceof DragonCultPriestArmorItem armor) {
            addArmorModifiers(event, EquipmentSlotGroup.bySlot(slotOf(event.getItemStack())));
            return;
        }

        if (event.getItemStack().getItem() instanceof DragonCrystalArmorItem armor) {
            addScaledArmorModifiers(event, slotOf(event.getItemStack()), EquipmentSlotGroup.bySlot(slotOf(event.getItemStack())), "dragon_crystal_armor", 0.25);
            return;
        }

        if (event.getItemStack().getItem() instanceof DragonArmorItem armor) {
            addScaledArmorModifiers(event, slotOf(event.getItemStack()), EquipmentSlotGroup.bySlot(slotOf(event.getItemStack())), "dragon_armor", 0.5);
            return;
        }

        if (event.getItemStack().getItem() instanceof PriestStaffItem) {
            addStaffModifiers(event, EquipmentSlotGroup.MAINHAND);
            return;
        }

        if (event.getItemStack().getItem() instanceof DragonCrystalSword) {
            addSwordModifiers(event, EquipmentSlotGroup.MAINHAND, "dragon_crystal_sword", 0.25);
        } else if (event.getItemStack().getItem() instanceof DragonSword) {
            addSwordModifiers(event, EquipmentSlotGroup.MAINHAND, "dragon_sword", 0.5);
        } else if (event.getItemStack().getItem() instanceof TheLastSword) {
            addSwordModifiers(event, EquipmentSlotGroup.MAINHAND, "the_last_sword", 1.0);
        }
    }

    private static void addArmorModifiers(ItemAttributeModifierEvent event, EquipmentSlotGroup group) {
        Holder<Attribute> maxMana = attribute(MAX_MANA_ID);
        if (maxMana == null) {
            return;
        }

        event.addModifier(maxMana, new AttributeModifier(
            modId("dragon_cult_priest_armor_max_mana"),
            0.25,
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        ), group);
    }

    private static void addScaledArmorModifiers(ItemAttributeModifierEvent event, EquipmentSlot slot, EquipmentSlotGroup group, String prefix, double bonus) {
        Holder<Attribute> maxMana = attribute(MAX_MANA_ID);
        if (maxMana != null) {
            event.addModifier(maxMana, new AttributeModifier(
                modId(prefix + "_max_mana_" + slot.getName()),
                bonus,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ), group);
        }

        Holder<Attribute> spellPower = attribute(SPELL_POWER_ID);
        if (spellPower != null) {
            event.addModifier(spellPower, new AttributeModifier(
                modId(prefix + "_spell_power_" + slot.getName()),
                bonus,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ), group);
        }
    }

    private static void addStaffModifiers(ItemAttributeModifierEvent event, EquipmentSlotGroup group) {
        Holder<Attribute> maxMana = attribute(MAX_MANA_ID);
        if (maxMana != null) {
            event.addModifier(maxMana, new AttributeModifier(
                modId("priest_staff_max_mana"),
                100.0,
                AttributeModifier.Operation.ADD_VALUE
            ), group);
        }

        Holder<Attribute> spellPower = attribute(SPELL_POWER_ID);
        if (spellPower != null) {
            event.addModifier(spellPower, new AttributeModifier(
                modId("priest_staff_spell_power"),
                0.25,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ), group);
        }
    }

    private static void addSwordModifiers(ItemAttributeModifierEvent event, EquipmentSlotGroup group, String prefix, double bonus) {
        Holder<Attribute> maxMana = attribute(MAX_MANA_ID);
        if (maxMana != null) {
            event.addModifier(maxMana, new AttributeModifier(
                modId(prefix + "_max_mana"),
                bonus,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ), group);
        }

        Holder<Attribute> spellPower = attribute(SPELL_POWER_ID);
        if (spellPower != null) {
            event.addModifier(spellPower, new AttributeModifier(
                modId(prefix + "_spell_power"),
                bonus,
                AttributeModifier.Operation.ADD_MULTIPLIED_BASE
            ), group);
        }
    }
}
