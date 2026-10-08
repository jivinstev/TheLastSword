package net.the_last_sword.util;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.the_last_sword.client.ClientScreenHelper;

//server-safe access to key state for tooltips
public final class TooltipKeys {
    private TooltipKeys() {
    }

    public static boolean hasShiftDown() {
        return FMLEnvironment.dist == Dist.CLIENT && ClientScreenHelper.hasShiftDown();
    }

    public static boolean hasControlDown() {
        return FMLEnvironment.dist == Dist.CLIENT && ClientScreenHelper.hasControlDown();
    }
}
