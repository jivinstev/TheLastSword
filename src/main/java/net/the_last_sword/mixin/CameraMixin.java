package net.the_last_sword.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.the_last_sword.client.QueenExecutionCamera;
import net.the_last_sword.client.QueenTripleSlashScreenShake;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    //26.1: Camera.setup(Level, Entity, boolean, boolean, float) is gone; its body is alignWithEntity(float), run from
    //Camera.update with the camera's own entity, so the same TAIL injection goes there
    @Shadow
    private Entity entity;

    @Shadow
    protected abstract void setRotation(float yaw, float pitch);

    @Shadow
    protected abstract void move(float forward, float up, float left);

    @Inject(method = "alignWithEntity", at = @At("TAIL"))
    private void theLastSword$applyExecutionCamera(float partialTick, CallbackInfo ci) {
        Entity entity = this.entity;
        if (entity == Minecraft.getInstance().player && QueenExecutionCamera.isActive()) {
            setRotation(QueenExecutionCamera.getYaw(), 0.0F);
            move(0.0F, 0.3F, 0.0F);
        }
        if (entity == Minecraft.getInstance().player && QueenTripleSlashScreenShake.isActive()) {
            Camera camera = (Camera) (Object) this;
            float yaw = camera.yRot() + QueenTripleSlashScreenShake.getYawOffset(partialTick);
            float pitch = Mth.clamp(camera.xRot()
                    + QueenTripleSlashScreenShake.getPitchOffset(partialTick), -90.0F, 90.0F);
            setRotation(yaw, pitch);
        }
    }
}
