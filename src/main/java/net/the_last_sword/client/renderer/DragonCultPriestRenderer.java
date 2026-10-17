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
import net.the_last_sword.client.layer.DeathRayLayer;
import net.the_last_sword.client.model.DragonCultPriestModel;
import net.the_last_sword.entity.DragonCultPriestEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class DragonCultPriestRenderer extends GeoEntityRenderer<DragonCultPriestEntity> {

    //拜龙教紫，替代末影龙原本的品红
    private static final int RAY_RED = 155;
    private static final int RAY_GREEN = 89;
    private static final int RAY_BLUE = 208;

    //当前帧渲染的实体与插值进度, 由 preRender 记录供 render 叠加光柱
    private DragonCultPriestEntity renderingEntity;
    private float renderingPartialTick;

    public DragonCultPriestRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonCultPriestModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(DragonCultPriestEntity animatable, ResourceLocation texture,
            MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucent(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack poseStack, DragonCultPriestEntity entity, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        if (!isReRender) {
            this.renderingEntity = entity;
            this.renderingPartialTick = partialTick;
        }
        super.preRender(poseStack, entity, model, bufferSource, buffer, isReRender, partialTick,
                packedLight, packedOverlay, colour);
    }

    //模型渲染完毕后叠加死亡放射光柱
    @Override
    public void render(EntityRenderState renderState, PoseStack poseStack,
            MultiBufferSource bufferSource, int packedLight) {
        this.renderingEntity = null;
        super.render(renderState, poseStack, bufferSource, packedLight);

        DragonCultPriestEntity entity = this.renderingEntity;
        this.renderingEntity = null;
        if (entity == null) {
            return;
        }
        int deathTick = entity.getDeathTick();
        if (deathTick <= 0) {
            return;
        }

        float progress = Math.min((deathTick + this.renderingPartialTick) / DragonCultPriestEntity.DEATH_ANIMATION_TICKS, 1.0f);
        DeathRayLayer.render(poseStack, bufferSource, progress, entity.getBbHeight() * 0.5f,
                RAY_RED, RAY_GREEN, RAY_BLUE);
    }

    @Override
    protected float getDeathMaxRotation(DragonCultPriestEntity entity, float partialTick) {
        return 0.0F;
    }

    @Override
    public int getPackedOverlay(DragonCultPriestEntity entity, float u, float partialTick) {
        //1.21.4: a GeckoLib renderer never reaches LivingEntityRenderer.getOverlayCoords, where the Justified
        //Defence white flash is mixed in (LivingEntityRendererMixin); apply it here, as GuardianOfSealedSpireRenderer does
        float flash = JustifiedDefenceFlash.getWhiteOverlayProgress(entity);
        return OverlayTexture.pack(OverlayTexture.u(Math.max(flash, 0.0F)),
                OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
}
