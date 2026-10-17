package net.the_last_sword.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.the_last_sword.test.UltraTestSwordItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// mixin_add (1.21.4+): 1.21.1's GameRenderer.renderConfusionOverlay is Gui.renderConfusionOverlay, and 26.1's
// extract-phase GUI names it extractConfusionOverlay(GuiGraphicsExtractor, float).
@Mixin(Gui.class)
public class GuiConfusionOverlayMixin {

    //防御模式：禁止恶心效果画面扭曲
    @Inject(method = "extractConfusionOverlay", at = @At("HEAD"), cancellable = true)
    private void theLastSword$cancelConfusionOverlay(GuiGraphicsExtractor guiGraphics, float strength, CallbackInfo ci) {
        Player player = Minecraft.getInstance().player;
        if (player != null && UltraTestSwordItem.hasDefenseSword(player)) {
            ci.cancel();
        }
    }
}
