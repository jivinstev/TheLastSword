package net.the_last_sword;

import net.minecraft.core.Holder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/**
 * Resolves the data-driven registry entries 1.21 made into keys (NeoForge port).
 * Holders come from the registries of the CALLING side: the integrated server and the client keep
 * separate copies, and a holder from the wrong one fails to encode. Only the client's own thread
 * gets the client's copy; every other thread -- the server thread AND the server's world-generation
 * workers -- gets the server's (a client holder on a mob made during worldgen crashes the game when
 * the chunk is saved: "Saving entity NBT ... is not valid in current registry set").
 */
public final class ModHolders {
    private ModHolders() {}

    public static Holder<Enchantment> enchantment(ResourceKey<Enchantment> key) {
        return registries().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(key);
    }

    public static HolderLookup.Provider registries() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if (FMLEnvironment.dist == Dist.CLIENT && Client.onClientThread()) {
            HolderLookup.Provider client = Client.registries();
            if (client != null) return client;
        }
        if (server != null) return server.registryAccess();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            HolderLookup.Provider client = Client.registries();     // a multiplayer client: no server here
            if (client != null) return client;
        }
        throw new IllegalStateException("no registries yet: called before a world was loaded");
    }

    private static final class Client {
        static boolean onClientThread() {
            return Minecraft.getInstance().isSameThread();
        }

        static HolderLookup.Provider registries() {
            ClientLevel level = Minecraft.getInstance().level;
            return level == null ? null : level.registryAccess();
        }
    }
}
