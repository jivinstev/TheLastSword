package net.the_last_sword.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.effect.PhasingEffect;
import net.the_last_sword.effect.VoidEnchantingEffect;
import net.the_last_sword.effect.WorldSeveranceEffect;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS =
        DeferredRegister.create(Registries.MOB_EFFECT, TheLastSwordMod.MOD_ID);

    //虚空附魔效果
    public static final DeferredHolder<MobEffect, MobEffect> VOID_ENCHANTING = EFFECTS.register(
        "void_enchanting",
        () -> net.the_last_sword.mv.RegIds.enter("void_enchanting") ? null : new VoidEnchantingEffect()
    );

    //虚化效果
    public static final DeferredHolder<MobEffect, MobEffect> PHASING = EFFECTS.register(
        "phasing",
        () -> net.the_last_sword.mv.RegIds.enter("phasing") ? null : new PhasingEffect()
    );

    public static final DeferredHolder<MobEffect, MobEffect> WORLD_SEVERANCE = EFFECTS.register(
        "world_severance", () -> net.the_last_sword.mv.RegIds.enter("world_severance") ? null : new WorldSeveranceEffect()
    );

    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
