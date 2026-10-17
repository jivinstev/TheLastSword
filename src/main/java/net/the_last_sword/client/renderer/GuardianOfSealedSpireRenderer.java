package net.the_last_sword.client.renderer;

import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.constant.DataTickets;
import net.minecraft.util.Mth;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.cache.model.BakedGeoModel;
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
        //GeckoLib 5.4 replaced preRender with a RenderPassInfo hook; the scale is constant, so set it once here
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
        this.withScale(1.0f);
        this.withRenderLayer(renderer -> new GuardianItemLayer<>(renderer));
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(texture);
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

    //GeckoLib 5.4: the head look the model's setCustomAnimations applied (pitch/yaw from the render state, in degrees)
    //is a bone-snapshot adjustment on the renderer, made for every frame after the animation
    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
        Float pitch = renderPassInfo.getGeckolibData(DataTickets.ENTITY_PITCH);
        Float yaw = renderPassInfo.getGeckolibData(DataTickets.ENTITY_YAW);
        if (pitch == null || yaw == null) {
            return;
        }
        snapshots.ifPresent("head", head -> head.setRotX(pitch * Mth.DEG_TO_RAD).setRotY(yaw * Mth.DEG_TO_RAD));
    }
}
