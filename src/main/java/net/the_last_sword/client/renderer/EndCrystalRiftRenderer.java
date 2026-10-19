package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.phys.AABB;
import net.the_last_sword.entity.EndCrystalRift;

// 1.21.2+: EntityRenderer has no getTextureLocation to override (1.21.1's was only the abstract requirement);
// QueenBlinkRenderUtil.summonRift binds textures/entities/queen_rift.png itself. The culling box moved from the
// entity to the renderer, so getBoundingBoxForCulling below returns the entity's inflated box as 1.21.1 did.
public class EndCrystalRiftRenderer extends EntityRenderer<EndCrystalRift, EndCrystalRiftRenderer.RiftRenderState> {
    public static class RiftRenderState extends EntityRenderState {
        public float animationAge;
        public float yRot;
    }

    public EndCrystalRiftRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public RiftRenderState createRenderState() {
        return new RiftRenderState();
    }

    @Override
    public void extractRenderState(EndCrystalRift entity, RiftRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.animationAge = entity.getAnimationAge(partialTick);
        state.yRot = entity.getYRot();
    }

    @Override
    protected AABB getBoundingBoxForCulling(EndCrystalRift entity) {
        return entity.getBoundingBoxForCulling();
    }

    //1.21.9+: render(...) is submit(...); the rift is queued as custom geometry on QueenBlinkRenderUtil's rift type (all
    //summonRift draws), from the entity's pose, before super -- as 1.21.1 drew it before super.render
    @Override
    public void submit(RiftRenderState state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        float age = state.animationAge;
        float yRot = state.yRot;
        collector.submitCustomGeometry(pose, QueenBlinkRenderUtil.riftRenderType(), (last, vertices) -> {
            PoseStack rift = new PoseStack();
            rift.last().set(last);
            QueenBlinkRenderUtil.summonRift(rift, renderType -> vertices, age, yRot);
        });
        super.submit(state, pose, collector, camera);
    }
}
