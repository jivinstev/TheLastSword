package net.the_last_sword.client.renderer;

import net.the_last_sword.client.JustifiedDefenceFlash;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.layer.DragonCultistItemLayer;
import net.the_last_sword.client.model.DragonCultistModel;
import net.the_last_sword.entity.DragonCultistEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer)
public class DragonCultistRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<DragonCultistEntity, R> {
    public DragonCultistRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonCultistModel());
        this.shadowRadius = 0.5f;
        this.withRenderLayer(new DragonCultistItemLayer<>(this));
    }

    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, SubmitNodeCollector nodeCollector, CameraRenderState cameraState, int packedLight, int packedOverlay, int colour) {
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(renderState, poseStack, model, nodeCollector, cameraState,
                packedLight, packedOverlay, colour);
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public int getPackedOverlay(DragonCultistEntity entity, Void relatedObject, float u, float partialTick) {
        //1.21.4: a GeckoLib renderer never reaches LivingEntityRenderer.getOverlayCoords, where the Justified
        //Defence white flash is mixed in (LivingEntityRendererMixin); apply it here, as GuardianOfSealedSpireRenderer does
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
}
