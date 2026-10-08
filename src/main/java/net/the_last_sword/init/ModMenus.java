package net.the_last_sword.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.gui.menu.DragonCrystalEnchantingTableMenu;
import net.the_last_sword.client.gui.menu.DragonCrystalSmithingTableMenu;
import net.the_last_sword.client.gui.menu.SummonWraithGuiMenu;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENUS =
        DeferredRegister.create(Registries.MENU, TheLastSwordMod.MOD_ID);

    public static final Supplier<MenuType<DragonCrystalSmithingTableMenu>> DRAGON_CRYSTAL_SMITHING_TABLE =
        MENUS.register("dragon_crystal_smithing_table",
            () -> IMenuTypeExtension.create(DragonCrystalSmithingTableMenu::new)
        );

    public static final Supplier<MenuType<DragonCrystalEnchantingTableMenu>> DRAGON_CRYSTAL_ENCHANTING_TABLE =
        MENUS.register("dragon_crystal_enchanting_table",
            () -> IMenuTypeExtension.create(DragonCrystalEnchantingTableMenu::new)
        );

    public static final Supplier<MenuType<SummonWraithGuiMenu>> SUMMON_WRAITH_GUI =
        MENUS.register("summon_wraith_gui",
            () -> IMenuTypeExtension.create(SummonWraithGuiMenu::new)
        );

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
