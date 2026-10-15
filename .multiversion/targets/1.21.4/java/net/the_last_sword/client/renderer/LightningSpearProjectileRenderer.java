package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.the_last_sword.entity.LightningSpearProjectile;
import org.jetbrains.annotations.NotNull;

public class LightningSpearProjectileRenderer extends EntityRenderer<LightningSpearProjectile, LightningSpearProjectileRenderer.RenderState> {
    public LightningSpearProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        shadowRadius = 0.0F;
    }

    public static class RenderState extends EntityRenderState {
        public float yawDegrees;
        public float pitchDegrees;
        public float age;
    }

    @Override
    public RenderState createRenderState() {
        return new RenderState();
    }

    @Override
    public void extractRenderState(LightningSpearProjectile projectile, RenderState state, float partialTick) {
        super.extractRenderState(projectile, state, partialTick);
        state.yawDegrees = Mth.lerp(partialTick, projectile.yRotO, projectile.getYRot()) - 90.0F;
        state.pitchDegrees = Mth.lerp(partialTick, projectile.xRotO, projectile.getXRot()) + 90.0F;
        state.age = projectile.tickCount + partialTick;
    }

    @Override
    public void render(RenderState state, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(state.yawDegrees));
        poseStack.mulPose(Axis.ZP.rotationDegrees(state.pitchDegrees));
        LightningSpearRenderUtil.render(poseStack, bufferSource, state.age);
        poseStack.popPose();
        super.render(state, poseStack, bufferSource, packedLight);
    }


}
