package net.the_last_sword.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.the_last_sword.client.gui.DragonCrystalEnchantingTableScreen;
import net.the_last_sword.client.gui.DragonCrystalSmithingTableScreen;
import net.the_last_sword.client.gui.SummonWraithGuiScreen;

//菜单屏幕注册
public class ModScreens {

    //注册菜单屏幕（MenuScreens.register 为私有，改用 RegisterMenuScreensEvent）
    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.DRAGON_CRYSTAL_SMITHING_TABLE.get(), DragonCrystalSmithingTableScreen::new);
        event.register(ModMenus.DRAGON_CRYSTAL_ENCHANTING_TABLE.get(), DragonCrystalEnchantingTableScreen::new);
        event.register(ModMenus.SUMMON_WRAITH_GUI.get(), SummonWraithGuiScreen::new);
    }
}
