package net.the_last_sword.client.renderer;

import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.constant.DataTickets;
import net.minecraft.util.Mth;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.resources.Identifier;
import net.the_last_sword.client.model.DragonCultPaladinModel;
import net.the_last_sword.entity.DragonCultPaladinEntity;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R is a type variable bounded by EntityRenderState & GeoRenderState (see LostWraithRenderer)
public class DragonCultPaladinRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<DragonCultPaladinEntity, R> {
    public DragonCultPaladinRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new DragonCultPaladinModel());
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    @Override
    public int getPackedOverlay(DragonCultPaladinEntity entity, Void relatedObject, float u, float partialTick) {
        return OverlayTexture.pack(0.0F, entity.hurtTime > 0 || entity.deathTime > 0);
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
