package net.the_last_sword.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.the_last_sword.test.UltraTestSwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 1.21.4 only (mixin_add): 1.21.1's GameRenderer.renderConfusionOverlay is Gui.renderConfusionOverlay here.
@Mixin(Gui.class)
public class GuiConfusionOverlayMixin {

    //防御模式：禁止恶心效果画面扭曲
    @Inject(method = "renderConfusionOverlay", at = @At("HEAD"), cancellable = true)
    private void theLastSword$cancelConfusionOverlay(GuiGraphics guiGraphics, float strength, CallbackInfo ci) {
        Player player = net.the_last_sword.mv.ClientRefs.player();
        if (player != null && UltraTestSwordItem.hasDefenseSword(player)) {
            ci.cancel();
        }
    }
}
