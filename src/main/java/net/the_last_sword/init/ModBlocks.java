package net.the_last_sword.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.block.DragonCrystalEnchantingTableBlock;
import net.the_last_sword.block.DragonCrystalSmithingTableBlock;
import net.the_last_sword.block.DragonSoulLanternBlock;
import net.the_last_sword.compat.CompatCheck;
import net.the_last_sword.compat.lucky_block.TheLastEndLuckyBlock;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
        DeferredRegister.create(Registries.BLOCK, TheLastSwordMod.MOD_ID);

    public static final Supplier<Block> DRAGON_CRYSTAL_SMITHING_TABLE = BLOCKS.register("dragon_crystal_smithing_table",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_smithing_table") ? null : new DragonCrystalSmithingTableBlock()
    );

    public static final Supplier<Block> DRAGON_SOUL_LANTERN = BLOCKS.register("dragon_soul_lantern",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_soul_lantern") ? null : new DragonSoulLanternBlock()
    );

    public static final Supplier<Block> DRAGON_CRYSTAL_ENCHANTING_TABLE = BLOCKS.register("dragon_crystal_enchanting_table",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_enchanting_table") ? null : new DragonCrystalEnchantingTableBlock()
    );

    //幸运方块联动（仅在 lucky 本体 mod 加载时注册）
    public static Supplier<Block> THE_LAST_END_LUCKY_BLOCK;

    public static void register(IEventBus eventBus) {
        registerConditionalBlocks();
        BLOCKS.register(eventBus);
    }

    private static void registerConditionalBlocks() {
        if (CompatCheck.isLuckyBlockLoaded()) {
            THE_LAST_END_LUCKY_BLOCK = BLOCKS.register("the_last_end_lucky_block", () -> net.the_last_sword.mv.RegIds.enter("the_last_end_lucky_block") ? null : new TheLastEndLuckyBlock());
        }
    }
}
