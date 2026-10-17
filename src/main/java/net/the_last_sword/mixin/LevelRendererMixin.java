package net.the_last_sword.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.player.Player;
import net.the_last_sword.init.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    //修改setupRender方法的isSpectator参数，让虚化玩家也能透视
    //1.21.9: setupRender(Camera, Frustum, boolean captured, boolean isSpectator) is cullTerrain(Camera, Frustum, boolean
    //isSpectator); the spectator flag is now its only boolean and gates the same solid-block occlusion check
    @ModifyVariable(
        method = "cullTerrain",
        at = @At("HEAD"),
        ordinal = 0,
        argsOnly = true
    )
    private boolean modifyIsSpectator(boolean isSpectator, Camera camera, Frustum frustum, boolean originalIsSpectator) {
        //如果玩家有虚化效果，视为旁观者模式进行渲染
        if (camera.getEntity() instanceof Player player) {
            if (player.hasEffect(ModEffects.PHASING)) {
                return true;
            }
        }
        return isSpectator;
    }
}
