package net.the_last_sword.mixin;

import net.minecraft.client.renderer.SubmitNodeCollector;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.state.level.PlayerRenderState;
import net.the_last_sword.init.ModEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 26.3: submit(float partialTick, SubmitNodeCollector, PlayerRenderState, CameraRenderState, boolean hideGui) reads
// the extracted render states, and ScreenEffectRenderer no longer holds the Minecraft instance (the player is
// Minecraft.getInstance().player, as the 26.2 field was); renderItemActivationAnimation takes the PlayerRenderState.
// 26.2: renderScreenEffect is submit(...) with the same arguments.
// 26.1: renderScreenEffect(boolean isFirstPerson, boolean isSleeping, float partialTick, SubmitNodeCollector, boolean hideGui);
// the activation animation is drawn when !hideGui, as vanilla does at the end of the method.
// 1.21.9: renderScreenEffect and renderItemActivationAnimation also take the SubmitNodeCollector.
// 1.21.6: renderScreenEffect is an instance method (boolean sleeping, float partialTick) and now ALSO draws the
// item-activation (totem) animation, which 1.21.1 drew elsewhere. Cancelling it whole would hide that animation too,
// so a phasing player gets only the activation animation, exactly as vanilla draws it at the end of the method.
@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Shadow
    private void renderItemActivationAnimation(PlayerRenderState playerRenderState, PoseStack poseStack, float partialTick,
            SubmitNodeCollector collector) {
    }

    //虚化效果：阻止渲染屏幕效果（方块覆盖纹理），实现透视效果
    @Inject(
        method = "submit",
        at = @At("HEAD"),
        cancellable = true
    )
    private void theLastSword$cancelScreenEffectWhenPhasing(float partialTick, SubmitNodeCollector collector,
            PlayerRenderState playerRenderState, CameraRenderState cameraRenderState, boolean hideGui, CallbackInfo ci) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            return;
        }

        //如果玩家有虚化效果，完全取消屏幕效果渲染（方块覆盖、水下、火焰等）
        if (minecraft.player.hasEffect(ModEffects.PHASING)) {
            if (!hideGui) {
                this.renderItemActivationAnimation(playerRenderState, new PoseStack(), partialTick, collector);
            }
            ci.cancel();
        }
    }
}
