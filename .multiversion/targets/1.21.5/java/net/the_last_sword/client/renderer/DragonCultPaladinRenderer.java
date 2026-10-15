package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.model.DragonCultPaladinModel;
import net.the_last_sword.entity.DragonCultPaladinEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer)
public class DragonCultPaladinRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<DragonCultPaladinEntity, R> {
    public DragonCultPaladinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonCultPaladinModel());
    }

    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, int packedLight, int packedOverlay, int colour) {

        super.preRender(renderState, poseStack, model, bufferSource, buffer, isReRender,
                packedLight, packedOverlay, colour);
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public int getPackedOverlay(DragonCultPaladinEntity entity, Void relatedObject, float u, float partialTick) {
        return OverlayTexture.pack(0.0F, entity.hurtTime > 0 || entity.deathTime > 0);
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
