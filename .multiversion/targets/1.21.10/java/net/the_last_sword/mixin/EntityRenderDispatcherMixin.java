package net.the_last_sword.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.the_last_sword.client.renderer.PhasingSubmitNodeCollector;
import net.the_last_sword.mv.EntityOf;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.LivingEntity;
import net.the_last_sword.util.PhasingState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// 1.21.9: render(entity, x, y, z, yaw, partialTick, PoseStack, MultiBufferSource, light) is
// submit(EntityRenderState, CameraRenderState, x, y, z, PoseStack, SubmitNodeCollector): the entity is drawn later from
// what it submits, so the phasing wrapper moves from the buffer source to the collector (PhasingSubmitNodeCollector).
@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    // 在模型提交顶点前统一处理，避免模型调用被重定向后参数索引发生变化。
    @ModifyVariable(method = "submit", at = @At("HEAD"), argsOnly = true, ordinal = 0)
    private SubmitNodeCollector theLastSword$phasingBuffers(SubmitNodeCollector original,
            EntityRenderState state, CameraRenderState cameraState, double x, double y, double z,
            PoseStack poseStack, SubmitNodeCollector collector) {
        if (EntityOf.get(state) instanceof LivingEntity living && PhasingState.isPhasing(living)) {
            return PhasingSubmitNodeCollector.wrap(original);
        }
        return original;
    }
}
