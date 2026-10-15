package net.the_last_sword.client;

import com.mojang.blaze3d.platform.InputConstants;

//client-only helper; must only be touched behind a client dist check
public final class ClientScreenHelper {
    private ClientScreenHelper() {
    }

    public static boolean hasShiftDown() {
        return InputConstants.isKeyDown(InputConstants.KEY_LSHIFT)
                || InputConstants.isKeyDown(InputConstants.KEY_RSHIFT);
    }

    public static boolean hasControlDown() {
        return InputConstants.isKeyDown(InputConstants.KEY_LCONTROL)
                || InputConstants.isKeyDown(InputConstants.KEY_RCONTROL);
    }
}
