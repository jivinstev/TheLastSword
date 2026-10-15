package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import net.the_last_sword.client.JustifiedDefenceFlash;
import net.the_last_sword.client.layer.GuardianItemLayer;
import net.the_last_sword.client.model.GuardianOfSealedSpireModel;
import net.the_last_sword.entity.GuardianOfSealedSpireEntity;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer)
public class GuardianOfSealedSpireRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<GuardianOfSealedSpireEntity, R> {

    public GuardianOfSealedSpireRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GuardianOfSealedSpireModel());
        this.shadowRadius = 0.5f;
        this.withScale(1.0f);
        this.addRenderLayer(new GuardianItemLayer<>(this));
    }

    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int colour) {
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(renderState, poseStack, model, bufferSource, buffer, isReRender,
                packedLight, packedOverlay, colour);
    }

    //死亡不倾倒（GeoEntityRenderer 不继承 LivingEntityRenderer，仍使用 getDeathMaxRotation）
    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    //受伤变红效果，同时保留统一覆盖色入口的闪光叠加（原 getOverlayCoords 的 Mixin 逻辑）
    @Override
    public int getPackedOverlay(GuardianOfSealedSpireEntity entity, Void relatedObject, float u, float partialTick) {
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }

    //NeoForge 21.8's InventoryScreen.renderEntityInInventory (an entity preview in a GUI) asks createRenderState() for a
    //fresh state, which GeckoLib 5.1/5.2 answer with null (they build theirs in createBaseRenderState(entity)): the
    //preview then dies in extractRenderState. Answer the class createBaseRenderState gives this living entity.
    @Override
    @SuppressWarnings("unchecked")
    public R createRenderState() {
        return (R) new net.minecraft.client.renderer.entity.state.LivingEntityRenderState();
    }
}
