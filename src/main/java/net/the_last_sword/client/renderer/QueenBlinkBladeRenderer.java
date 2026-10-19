package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.the_last_sword.mv.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.AABB;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.entity.QueenBlinkBlade;
import net.the_last_sword.entity.QueenEnhancedBlade;

public class QueenBlinkBladeRenderer extends EntityRenderer<QueenBlinkBlade, QueenBlinkBladeRenderer.State> {
    public static class State extends EntityRenderState {
        public QueenBlinkBlade entity;
    }

    private final boolean enhanced;
    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID,
            "textures/entities/the_past_shadow_of_the_queen.png");

    public QueenBlinkBladeRenderer(EntityRendererProvider.Context context) {
        this(context, false);
    }

    public QueenBlinkBladeRenderer(EntityRendererProvider.Context context, boolean enhanced) {
        super(context);
        this.enhanced = enhanced;
    }

    // 1.21.2 asks the renderer for the culling box; the blade's own box (QueenBlinkBlade) still answers it
    @Override
    protected AABB getBoundingBoxForCulling(QueenBlinkBlade entity) {
        return entity.getBoundingBoxForCulling();
    }

    @Override
    public State createRenderState() {
        return new State();
    }

    @Override
    public void extractRenderState(QueenBlinkBlade entity, State state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.entity = entity;
    }

    @Override
    public void submit(State state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState camera) {
        MultiBufferSource buffers = MultiBufferSource.main();
        QueenBlinkBlade entity = state.entity;
        pose.pushPose();
        pose.mulPose(Axis.YP.rotationDegrees(-entity.getYRot()));
        if (entity instanceof QueenEnhancedBlade blade) {
            pose.translate(0, entity.bladeHeight() * 0.5, 0);
            pose.mulPose(Axis.XP.rotationDegrees(entity.getXRot()));
            pose.mulPose(Axis.ZP.rotationDegrees(blade.bladeRoll()));
            pose.translate(0, -entity.bladeHeight() * 0.5, 0);
        }
        QueenBlinkRenderUtil.blade(pose, buffers, entity.bladeWidth(), entity.bladeHeight(), entity.bladeLength(), enhanced);
        pose.popPose();
        super.submit(state, pose, collector, camera);
    }

    public Identifier getTextureLocation(State state) {
        return TEXTURE;
    }
}
