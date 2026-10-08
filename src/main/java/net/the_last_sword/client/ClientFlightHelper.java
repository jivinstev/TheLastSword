package net.the_last_sword.client;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

//客户端专用：飞行惯性控制，仅在客户端侧调用
public class ClientFlightHelper {
    public static boolean isNoMoveInput(Player player) {
        return player instanceof LocalPlayer localPlayer
                && localPlayer.input.forwardImpulse == 0
                && localPlayer.input.leftImpulse == 0
                && !localPlayer.input.jumping
                && !localPlayer.input.shiftKeyDown;
    }
}
