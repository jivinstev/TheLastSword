package net.the_last_sword.compat.cataclysm;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

//Cataclysm奖章条件注册类 - 只在Cataclysm加载时才会被使用
public class CataclysmItemsRegistry {

    //远古残骸奖章
    public static Supplier<Item> registerAncientRemnantMedal(DeferredRegister<Item> registry) {
        return registry.register("ancient_remnant_medal", CataclysmMedals.AncientRemnantMedal::new);
    }

    //末影守卫者奖章
    public static Supplier<Item> registerEnderGuardianMedal(DeferredRegister<Item> registry) {
        return registry.register("ender_guardian_medal", CataclysmMedals.EnderGuardianMedal::new);
    }

    //伊格尼斯奖章
    public static Supplier<Item> registerIgnisMedal(DeferredRegister<Item> registry) {
        return registry.register("ignis_medal", CataclysmMedals.IgnisMedal::new);
    }

    //马莱迪克斯奖章
    public static Supplier<Item> registerMaledictusMedal(DeferredRegister<Item> registry) {
        return registry.register("maledictus_medal", CataclysmMedals.MaledictusMedal::new);
    }

    //下界合金巨兽奖章
    public static Supplier<Item> registerNetheriteMonstrosityMedal(DeferredRegister<Item> registry) {
        return registry.register("netherite_monstrosity_medal", CataclysmMedals.NetheriteMonstrosityMedal::new);
    }

    //先驱者奖章
    public static Supplier<Item> registerTheHarbingerMedal(DeferredRegister<Item> registry) {
        return registry.register("the_harbinger_medal", CataclysmMedals.TheHarbingerMedal::new);
    }

    //利维坦奖章
    public static Supplier<Item> registerTheLeviathanMedal(DeferredRegister<Item> registry) {
        return registry.register("the_leviathan_medal", CataclysmMedals.TheLeviathanMedal::new);
    }

    //斯库拉奖章
    public static Supplier<Item> registerScyllaMedal(DeferredRegister<Item> registry) {
        return registry.register("scylla_medal", CataclysmMedals.ScyllaMedal::new);
    }

    private CataclysmItemsRegistry() {}
}
