package net.the_last_sword.enchantment;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.enchantment.Enchantment;

/**
 * 现世斩断附魔。1.21 起附魔为数据包注册表，定义见
 * data/the_last_sword/enchantment/world_severance.json（稀有度极稀有、最高5级、主手、可附在任何耐久物品上）。
 */
public final class WorldSeveranceEnchantment {

    public static final ResourceKey<Enchantment> KEY = ResourceKey.create(Registries.ENCHANTMENT,
            ResourceLocation.fromNamespaceAndPath("the_last_sword", "world_severance"));

    private WorldSeveranceEnchantment() {
    }
}
