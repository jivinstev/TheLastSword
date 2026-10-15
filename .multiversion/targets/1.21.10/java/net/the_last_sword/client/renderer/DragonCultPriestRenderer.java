package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.layer.DeathRayLayer;
import net.the_last_sword.client.model.DragonCultPriestModel;
import net.the_last_sword.entity.DragonCultPriestEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer)
public class DragonCultPriestRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<DragonCultPriestEntity, R> {

    //拜龙教紫，替代末影龙原本的品红
    private static final int RAY_RED = 155;
    private static final int RAY_GREEN = 89;
    private static final int RAY_BLUE = 208;

    //死亡进度与半身高在提取阶段记录（渲染状态不再持有实体）
    private static final DataTicket<Float> DEATH_PROGRESS =
            DataTicket.create("the_last_sword_priest_death_progress", Float.class);
    private static final DataTicket<Float> HALF_HEIGHT =
            DataTicket.create("the_last_sword_priest_half_height", Float.class);

    public DragonCultPriestRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonCultPriestModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        return RenderType.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    public void preRender(R renderState, PoseStack poseStack, BakedGeoModel model, SubmitNodeCollector collector, CameraRenderState cameraState, int packedLight, int packedOverlay, int colour) {
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        super.preRender(renderState, poseStack, model, collector, cameraState, packedLight, packedOverlay, colour);
    }

    @Override
    public void extractRenderState(DragonCultPriestEntity entity, R renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);
        int deathTick = entity.getDeathTick();
        renderState.addGeckolibData(DEATH_PROGRESS, deathTick <= 0 ? 0.0f
                : Math.min((deathTick + partialTick) / DragonCultPriestEntity.DEATH_ANIMATION_TICKS, 1.0f));
        renderState.addGeckolibData(HALF_HEIGHT, entity.getBbHeight() * 0.5f);
    }

    //模型渲染完毕后叠加死亡放射光柱
    //1.21.9+: render(state, pose, MultiBufferSource, light) is submit(state, pose, SubmitNodeCollector, camera); the rays
    //are queued as custom geometry on the lightning render type DeathRayLayer draws with, from the same pose
    @Override
    public void submit(R renderState, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState cameraState) {
        super.submit(renderState, poseStack, collector, cameraState);

        float progress = renderState.getOrDefaultGeckolibData(DEATH_PROGRESS, 0.0f);
        if (progress <= 0.0f) {
            return;
        }
        float halfHeight = renderState.getOrDefaultGeckolibData(HALF_HEIGHT, 0.0f);
        collector.submitCustomGeometry(poseStack, RenderType.lightning(), (pose, vertices) -> {
            PoseStack rays = new PoseStack();
            rays.last().set(pose);
            DeathRayLayer.render(rays, renderType -> vertices, progress, halfHeight, RAY_RED, RAY_GREEN, RAY_BLUE);
        });
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public int getPackedOverlay(DragonCultPriestEntity entity, Void relatedObject, float u, float partialTick) {
        //等同原版 LivingEntityRenderer.getOverlayCoords(entity, 0)
        return OverlayTexture.pack(0.0F, entity.hurtTime > 0 || entity.deathTime > 0);
    }
}
