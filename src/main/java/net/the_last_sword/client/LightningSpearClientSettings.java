package net.the_last_sword.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.configuration.LightningSpearSettings;
import net.the_last_sword.configuration.TheLastSwordConfiguration;

@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
public final class LightningSpearClientSettings {
    private static LightningSpearSettings serverSettings;

    private LightningSpearClientSettings() {
    }

    public static LightningSpearSettings get() {
        return serverSettings == null ? TheLastSwordConfiguration.getLightningSpearSettings() : serverSettings;
    }

    public static void receive(LightningSpearSettings settings) {
        serverSettings = settings;
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        serverSettings = null;
    }
}
