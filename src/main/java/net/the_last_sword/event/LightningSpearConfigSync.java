package net.the_last_sword.event;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.configuration.LightningSpearSettings;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.network.LightningSpearConfigPacket;
import net.the_last_sword.network.NetworkHandler;

@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID)
public final class LightningSpearConfigSync {
    private static LightningSpearSettings lastSettings;
    private static int ticks;

    private LightningSpearConfigSync() {
    }

    @SubscribeEvent
    public static void onLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            NetworkHandler.sendToPlayer(new LightningSpearConfigPacket(
                TheLastSwordConfiguration.getLightningSpearSettings()), player);
        }
    }

    @SubscribeEvent
    public static void onTick(ServerTickEvent.Post event) {
        if (++ticks < 20) {
            return;
        }
        ticks = 0;
        LightningSpearSettings settings = TheLastSwordConfiguration.getLightningSpearSettings();
        if (settings.equals(lastSettings)) {
            return;
        }
        lastSettings = settings;
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (server != null) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                NetworkHandler.sendToPlayer(new LightningSpearConfigPacket(settings), player);
            }
        }
    }

    @SubscribeEvent
    public static void onStop(ServerStoppedEvent event) {
        lastSettings = null;
        ticks = 0;
    }
}
