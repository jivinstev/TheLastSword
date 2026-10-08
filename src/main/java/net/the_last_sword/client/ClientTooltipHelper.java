package net.the_last_sword.client;

import net.minecraft.client.gui.screens.Screen;

//客户端专用：提示框辅助
public final class ClientTooltipHelper {

    private ClientTooltipHelper() {
    }

    public static boolean hasShiftDown() {
        return Screen.hasShiftDown();
    }
}
