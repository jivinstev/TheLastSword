package net.the_last_sword.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.renderer.WingsThatCoverTheWorldRenderer;
import net.the_last_sword.init.ModItems;
import top.theillusivec4.curios.api.client.ICurioRenderer;

// 1.21.4 only: 1.21.1's WingsThatCoverTheWorld.initializeClient handed the curio renderer out of an
// IClientItemExtensions; NeoForge 21.4 removed initializeClient, and Curios for 1.21.4 finds a curio's renderer only
// through CuriosRendererRegistry, so the same renderer is registered here. 26.3: Curios 17 deleted
// CuriosRendererRegistry, whose register was a delegate to ICurioRenderer.register (CATALOG V105).
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
public final class WingsThatCoverTheWorldClient {
    private WingsThatCoverTheWorldClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ICurioRenderer.register(ModItems.WINGS_THAT_COVER_THE_WORLD.get(),
                WingsThatCoverTheWorldRenderer::new));
    }
}
