package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.the_last_sword.entity.GroundRuptureFragmentEntity;

public class GroundRuptureFragmentRenderer extends EntityRenderer<GroundRuptureFragmentEntity, GroundRuptureFragmentRenderer.RenderState> {
    private final BlockRenderDispatcher blockRenderer;

    public static class RenderState extends EntityRenderState {
        int seed;
        float age;
        float fragmentScale;
        BlockState blockState;
    }

    public GroundRuptureFragmentRenderer(EntityRendererProvider.Context context) {
        super(context);
        blockRenderer = context.getBlockRenderDispatcher();
        shadowRadius = 0.0F;
    }

    @Override
    public void submit(RenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        int seed = state.seed;
        float age = state.age;
        float shrink = age > 13.0F ? Mth.clamp(1.0F - (age - 13.0F) / 8.0F, 0.35F, 1.0F) : 1.0F;
        float scale = state.fragmentScale * shrink;

        float scaleX = scale * (0.72F + component(seed, 0) * 0.38F);
        float scaleY = scale * (0.48F + component(seed, 8) * 0.52F);
        float scaleZ = scale * (0.72F + component(seed, 16) * 0.38F);
        float spinDirection = (seed & 1) == 0 ? 1.0F : -1.0F;

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(component(seed, 4) * 45.0F + age * 7.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(component(seed, 12) * 360.0F + age * 5.0F * spinDirection));
        poseStack.mulPose(Axis.ZP.rotationDegrees(component(seed, 20) * 45.0F - age * 6.0F * spinDirection));
        poseStack.scale(scaleX, scaleY, scaleZ);
        poseStack.translate(-0.5D, -0.5D, -0.5D);
        collector.submitBlock(poseStack, state.blockState, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();

        super.submit(state, poseStack, collector, camera);
    }

    private static float component(int seed, int shift) {
        return ((seed >>> shift) & 0xFF) / 255.0F;
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void extractRenderState(GroundRuptureFragmentEntity fragment, RenderState state, float partialTick) {
        super.extractRenderState(fragment, state, partialTick);
        state.seed = fragment.getVisualSeed();
        state.age = fragment.tickCount + partialTick;
        state.fragmentScale = fragment.getFragmentScale();
        state.blockState = fragment.getBlockState();
    }

}
