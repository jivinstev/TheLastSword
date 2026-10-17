package net.the_last_sword.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenEffectRenderer;
import net.the_last_sword.init.ModEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 1.21.6: renderScreenEffect is an instance method (boolean sleeping, float partialTick) and now ALSO draws the
// item-activation (totem) animation, which 1.21.1 drew elsewhere. Cancelling it whole would hide that animation too,
// so a phasing player gets only the activation animation, exactly as vanilla draws it at the end of the method.
@Mixin(ScreenEffectRenderer.class)
public class ScreenEffectRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private void renderItemActivationAnimation(PoseStack poseStack, float partialTick) {
    }

    //虚化效果：阻止渲染屏幕效果（方块覆盖纹理），实现透视效果
    @Inject(
        method = "renderScreenEffect",
        at = @At("HEAD"),
        cancellable = true
    )
    private void theLastSword$cancelScreenEffectWhenPhasing(boolean sleeping, float partialTick, CallbackInfo ci) {
        if (this.minecraft.player == null) {
            return;
        }

        //如果玩家有虚化效果，完全取消屏幕效果渲染（方块覆盖、水下、火焰等）
        if (this.minecraft.player.hasEffect(ModEffects.PHASING)) {
            if (!this.minecraft.options.hideGui) {
                this.renderItemActivationAnimation(new PoseStack(), partialTick);
            }
            ci.cancel();
        }
    }
}
