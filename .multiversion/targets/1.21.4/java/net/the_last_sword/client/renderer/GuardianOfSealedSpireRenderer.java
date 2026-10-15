package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.JustifiedDefenceFlash;
import net.the_last_sword.client.layer.GuardianItemLayer;
import net.the_last_sword.client.model.GuardianOfSealedSpireModel;
import net.the_last_sword.entity.GuardianOfSealedSpireEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GuardianOfSealedSpireRenderer extends GeoEntityRenderer<GuardianOfSealedSpireEntity> {

    public GuardianOfSealedSpireRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GuardianOfSealedSpireModel());
        this.shadowRadius = 0.5f;
        this.addRenderLayer(new GuardianItemLayer(this));
    }

    @Override
    public RenderType getRenderType(GuardianOfSealedSpireEntity animatable, ResourceLocation texture,
            MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, GuardianOfSealedSpireEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, colour);
    }

    //死亡不倾倒（GeoEntityRenderer 不继承 LivingEntityRenderer，仍使用 getDeathMaxRotation）
    @Override
    protected float getDeathMaxRotation(GuardianOfSealedSpireEntity animatable, float partialTick) {
        return 0.0F;
    }

    //受伤变红效果，同时保留统一覆盖色入口的闪光叠加（原 getOverlayCoords 的 Mixin 逻辑）
    @Override
    public int getPackedOverlay(GuardianOfSealedSpireEntity entity, float u, float partialTick) {
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
}
