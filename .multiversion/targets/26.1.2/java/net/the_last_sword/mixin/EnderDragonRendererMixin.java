package net.the_last_sword.mixin;

import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.SubmitNodeCollector;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
import net.minecraft.client.renderer.entity.state.EnderDragonRenderState;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.the_last_sword.client.JustifiedDefenceFlash;
import net.the_last_sword.mv.EntityOf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 1.21.4: render takes the EnderDragonRenderState; the dragon is read off the state (EntityOf, parked by a
// render-state modifier, CATALOG M19).
// 1.21.9: EnderDragonRenderer.render(state, pose, MultiBufferSource, light) is submit(state, pose, SubmitNodeCollector,
// CameraRenderState); it still packs the overlay with OverlayTexture.pack(float, boolean), so the flash keeps its hook.
@Mixin(EnderDragonRenderer.class)
public class EnderDragonRendererMixin {
    @Unique
    private EnderDragon the_last_sword$renderedDragon;

    @Inject(
            method = "submit(Lnet/minecraft/client/renderer/entity/state/EnderDragonRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At("HEAD")
    )
    private void the_last_sword$captureRenderedDragon(EnderDragonRenderState state, PoseStack poseStack,
            SubmitNodeCollector collector, CameraRenderState cameraState, CallbackInfo ci) {
        the_last_sword$renderedDragon = EntityOf.get(state) instanceof EnderDragon dragon ? dragon : null;
    }

    @ModifyArg(
            method = "submit(Lnet/minecraft/client/renderer/entity/state/EnderDragonRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/texture/OverlayTexture;pack(FZ)I"),
            index = 0
    )
    private float the_last_sword$applyJustifiedDefenceFlash(float original) {
        if (the_last_sword$renderedDragon == null) {
            return original;
        }
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(the_last_sword$renderedDragon);
        return Math.max(flash, original);
    }
}
