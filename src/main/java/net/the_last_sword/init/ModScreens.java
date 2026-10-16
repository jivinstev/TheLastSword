package net.the_last_sword.init;

import net.eca.util.item_extension.ItemExtensionManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.gui.DragonCrystalEnchantingTableScreen;
import net.the_last_sword.client.gui.DragonCrystalSmithingTableScreen;
import net.the_last_sword.client.gui.SummonWraithGuiScreen;
import net.the_last_sword.compat.CompatCheck;
import net.the_last_sword.compat.irons_spellbooks.EndSpellScrollItemExtension;

//菜单屏幕注册
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModScreens {

    //注册菜单屏幕（MenuScreens.register 为私有，改用 RegisterMenuScreensEvent）
    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.DRAGON_CRYSTAL_SMITHING_TABLE.get(), DragonCrystalSmithingTableScreen::new);
        event.register(ModMenus.DRAGON_CRYSTAL_ENCHANTING_TABLE.get(), DragonCrystalEnchantingTableScreen::new);
        event.register(ModMenus.SUMMON_WRAITH_GUI.get(), SummonWraithGuiScreen::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            if (CompatCheck.isSpellcastingLoaded()) {
                ItemExtensionManager.register(new EndSpellScrollItemExtension());
            }
        });
    }
}
