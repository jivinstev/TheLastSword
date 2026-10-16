package net.the_last_sword.client.renderer;

import net.the_last_sword.client.JustifiedDefenceFlash;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.model.DragonCultPaladinModel;
import net.the_last_sword.entity.DragonCultPaladinEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonCultPaladinRenderer extends GeoEntityRenderer<DragonCultPaladinEntity> {

    public DragonCultPaladinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonCultPaladinModel());
        this.shadowRadius = 0.6f;
    }

    @Override
    public RenderType getRenderType(DragonCultPaladinEntity animatable, ResourceLocation texture,
            MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, DragonCultPaladinEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, colour);
    }

    @Override
    protected float getDeathMaxRotation(DragonCultPaladinEntity animatable, float partialTick) {
        return 0.0F;
    }

    @Override
    public int getPackedOverlay(DragonCultPaladinEntity entity, float u, float partialTick) {
        //1.21.4: a GeckoLib renderer never reaches LivingEntityRenderer.getOverlayCoords, where the Justified
        //Defence white flash is mixed in (LivingEntityRendererMixin); apply it here, as GuardianOfSealedSpireRenderer does
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
}
