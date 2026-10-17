package net.the_last_sword.compat.curios;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

//Curios饰品条件注册类 - 只在Curios加载时才会被使用
public class CuriosItemsRegistry {

    //龙水晶指环
    public static Supplier<Item> registerDragonCrystalRing(DeferredRegister<Item> registry) {
        return registry.register("dragon_crystal_ring", () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_ring") ? null : new DragonCrystalRing());
    }

    //龙水晶项链
    public static Supplier<Item> registerDragonCrystalNecklace(DeferredRegister<Item> registry) {
        return registry.register("dragon_crystal_necklace", () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_necklace") ? null : new DragonCrystalNecklace());
    }

    //龙水晶王冠
    public static Supplier<Item> registerDragonCrystalCrown(DeferredRegister<Item> registry) {
        return registry.register("dragon_crystal_crown", () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_crown") ? null : new DragonCrystalCrown());
    }

    //覆世之翼
    public static Supplier<Item> registerWingsThatCoverTheWorld(DeferredRegister<Item> registry) {
        return registry.register("wings_that_cover_the_world", () -> net.the_last_sword.mv.RegIds.enter("wings_that_cover_the_world") ? null : new WingsThatCoverTheWorld());
    }

    //极限维生装置
    public static Supplier<Item> registerExtremeLifeSupportDevice(DeferredRegister<Item> registry) {
        return registry.register("extreme_life_support_device", () -> net.the_last_sword.mv.RegIds.enter("extreme_life_support_device") ? null : new ExtremeLifeSupportDevice());
    }

    //维度探索者
    public static Supplier<Item> registerDimensionExplorer(DeferredRegister<Item> registry) {
        return registry.register("dimension_explorer", () -> net.the_last_sword.mv.RegIds.enter("dimension_explorer") ? null : new DimensionExplorer());
    }

    //给予者的痛苦
    public static Supplier<Item> registerTheGiversPain(DeferredRegister<Item> registry) {
        return registry.register("the_givers_pain", () -> net.the_last_sword.mv.RegIds.enter("the_givers_pain") ? null : new TheGiversPain());
    }

    private CuriosItemsRegistry() {}
}
