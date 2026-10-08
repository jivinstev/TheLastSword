package net.the_last_sword.init;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.the_last_sword.enchantment.WorldSeveranceEnchantment;

public final class ModEnchantments {

    //1.21 起附魔是数据包注册表，这里只保存 ResourceKey
    public static final ResourceKey<Enchantment> WORLD_SEVERANCE = WorldSeveranceEnchantment.KEY;

    private ModEnchantments() {
    }

    public static void register(IEventBus eventBus) {
        // 附魔由 data/the_last_sword/enchantment/*.json 提供，无需注册
    }
}
