package net.the_last_sword.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

//客户端专用：提示框辅助
public final class ClientTooltipHelper {

    private ClientTooltipHelper() {
    }

    public static boolean hasShiftDown() {
        com.mojang.blaze3d.platform.Window window = Minecraft.getInstance().getWindow();
        return InputConstants.isKeyDown(window, GLFW.GLFW_KEY_LEFT_SHIFT)
                || InputConstants.isKeyDown(window, GLFW.GLFW_KEY_RIGHT_SHIFT);
    }
}
