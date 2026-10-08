package net.the_last_sword.client;

import net.minecraft.client.gui.screens.Screen;

//client-only helper; must only be touched behind a client dist check
public final class ClientScreenHelper {
    private ClientScreenHelper() {
    }

    public static boolean hasShiftDown() {
        return Screen.hasShiftDown();
    }

    public static boolean hasControlDown() {
        return Screen.hasControlDown();
    }
}
