package net.the_last_sword.client;

import net.minecraft.client.Minecraft;

//client-only helper; must only be touched behind a client dist check
//1.21.9+: Screen.hasShiftDown/hasControlDown are gone; Minecraft's own calls keep their behaviour (Cmd on macOS)
public final class ClientScreenHelper {
    private ClientScreenHelper() {
    }

    public static boolean hasShiftDown() {
        return Minecraft.getInstance().hasShiftDown();
    }

    public static boolean hasControlDown() {
        return Minecraft.getInstance().hasControlDown();
    }
}
