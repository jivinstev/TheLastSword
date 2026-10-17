package net.the_last_sword.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;

//客户端专用：提示框辅助
public final class ClientTooltipHelper {

    private ClientTooltipHelper() {
    }

    public static boolean hasShiftDown() {
        return InputConstants.isKeyDown(InputConstants.KEY_LSHIFT)
                || InputConstants.isKeyDown(InputConstants.KEY_RSHIFT);
    }
}
