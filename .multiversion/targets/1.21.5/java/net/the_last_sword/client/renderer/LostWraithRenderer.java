package net.the_last_sword.client.renderer;

import net.the_last_sword.client.JustifiedDefenceFlash;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.layer.LostWraithSealLayer;
import net.the_last_sword.client.model.LostWraithModel;
import net.the_last_sword.entity.LostWraithEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R must be an EntityRenderState that is also a GeoRenderState. GeckoLib's mixin makes every
//EntityRenderState one at runtime, but 5.1 ships no interface injection for javac, so the bound stays a type
//variable and the constructor reference infers the intersection.
public class LostWraithRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<LostWraithEntity, R> {

    public LostWraithRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LostWraithModel());
        addRenderLayer(new LostWraithSealLayer<>(this));
        this.shadowRadius = 1.0f;
    }

    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int colour) {
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(renderState, poseStack, model, bufferSource, buffer, isReRender, packedLight, packedOverlay, colour);
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    //受伤变红效果
    //等同原版 LivingEntityRenderer.getOverlayCoords(entity, 0)
    @Override
    public int getPackedOverlay(LostWraithEntity entity, Void relatedObject, float u, float partialTick) {
        //1.21.4: a GeckoLib renderer never reaches LivingEntityRenderer.getOverlayCoords, where the Justified
        //Defence white flash is mixed in (LivingEntityRendererMixin); apply it here, as GuardianOfSealedSpireRenderer does
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }

}
