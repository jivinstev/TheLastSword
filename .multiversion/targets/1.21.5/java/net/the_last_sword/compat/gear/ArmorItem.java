package net.the_last_sword.compat.gear;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;

/** The 1.21.4 ArmorItem contract: the material and slot type become components exactly as Item.Properties.humanoidArmor builds them. */
public class ArmorItem extends Item {
    public ArmorItem(ArmorMaterial material, ArmorType type, Item.Properties properties) {
        super(properties.humanoidArmor(material, type));
    }
}
