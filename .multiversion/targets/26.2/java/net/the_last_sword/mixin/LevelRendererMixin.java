package net.the_last_sword.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.the_last_sword.init.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

// 26.2: LevelRenderer.cullTerrain(Camera, Frustum, boolean isSpectator) is gone. Its only use of the spectator flag
// (smart culling off while a spectator's camera is inside a solid block) moved into Camera.extractRenderState, which
// now sets CameraRenderState.smartCull from minecraft.player.isSpectator(); SectionOcclusionGraph.update reads that.
// Redirecting that one check is what the 1.21.1 setupRender/cullTerrain argument change did. (Class keeps its name:
// the mixin config lists it.)
@Mixin(Camera.class)
public class LevelRendererMixin {

    //修改setupRender方法的isSpectator参数，让虚化玩家也能透视
    @Redirect(
        method = "extractRenderState",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isSpectator()Z", ordinal = 0)
    )
    private boolean modifyIsSpectator(LocalPlayer localPlayer) {
        //如果玩家有虚化效果，视为旁观者模式进行渲染
        if (Minecraft.getInstance().getCameraEntity() instanceof Player player) {
            if (player.hasEffect(ModEffects.PHASING)) {
                return true;
            }
        }
        return localPlayer.isSpectator();
    }
}
