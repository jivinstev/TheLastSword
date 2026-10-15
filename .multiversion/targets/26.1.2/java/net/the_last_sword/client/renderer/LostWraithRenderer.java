package net.the_last_sword.client.renderer;

import software.bernie.geckolib.renderer.base.RenderPassInfo;
import software.bernie.geckolib.renderer.base.BoneSnapshots;
import software.bernie.geckolib.constant.DataTickets;
import net.minecraft.util.Mth;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.the_last_sword.client.layer.LostWraithSealLayer;
import net.the_last_sword.client.model.LostWraithModel;
import net.the_last_sword.entity.LostWraithEntity;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

//GeckoLib 5.1: R must be an EntityRenderState that is also a GeoRenderState. GeckoLib's mixin makes every
//EntityRenderState one at runtime, but 5.1 ships no interface injection for javac, so the bound stays a type
//variable and the constructor reference infers the intersection.
public class LostWraithRenderer<R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<LostWraithEntity, R> {

    public LostWraithRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LostWraithModel());
        withRenderLayer(new LostWraithSealLayer<>(this));
        this.shadowRadius = 1.0f;
        //GeckoLib 5.4 replaced preRender with a RenderPassInfo hook; the scale is constant, so set it once here
        float scale = 1.0f;
        this.scaleHeight = scale;
        this.scaleWidth = scale;
    }

    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }

    @Override
    protected float getDeathMaxRotation(GeoRenderState renderState) {
        return 0.0F;
    }

    //受伤变红效果
    //等同原版 LivingEntityRenderer.getOverlayCoords(entity, 0)
    @Override
    public int getPackedOverlay(LostWraithEntity entity, Void relatedObject, float u, float partialTick) {
        return OverlayTexture.pack(OverlayTexture.u(0), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
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
