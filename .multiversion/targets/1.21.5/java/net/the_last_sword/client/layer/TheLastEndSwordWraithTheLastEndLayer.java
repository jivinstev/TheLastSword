package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.client.shader.TheLastEndEffect;
import net.the_last_sword.client.shader.TheLastEndRenderTypes;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

//终焉剑灵的万物终焉状态着色器渲染层
//GeckoLib 5: render() receives the render state, not the entity, so the state it needs is captured in addRenderData
public class TheLastEndSwordWraithTheLastEndLayer<R extends EntityRenderState & GeoRenderState>
        extends GeoRenderLayer<TheLastEndSwordWraithEntity, Void, R> {
    private static final DataTicket<Boolean> ALL_THINGS_END =
            DataTicket.create("the_last_sword_wraith_all_things_end", Boolean.class);

    public TheLastEndSwordWraithTheLastEndLayer(GeoRenderer<TheLastEndSwordWraithEntity, Void, R> entityRenderer) {
        super(entityRenderer);
    }

    @Override
    public void addRenderData(TheLastEndSwordWraithEntity animatable, Void relatedObject, R renderState) {
        renderState.addGeckolibData(ALL_THINGS_END, animatable.isAllThingsEnd());
    }

    @Override
    public void render(R renderState, PoseStack poseStack, BakedGeoModel bakedModel,
                       RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer,
                       int packedLight, int packedOverlay, int renderColor) {

        //只在万物终焉状态下渲染
        if (!Boolean.TRUE.equals(renderState.getGeckolibData(ALL_THINGS_END))) {
            return;
        }

        //检查着色器是否可用
        if (!TheLastEndEffect.isAvailable()) {
            return;
        }

        //应用终焉着色器uniforms
        TheLastEndEffect.applyUniforms();

        //获取剑灵的原始纹理
        ResourceLocation texture = getRenderer().getTextureLocation(renderState);

        //使用终焉着色器渲染类型
        RenderType theLastEndRenderType = TheLastEndRenderTypes.createEntityEffect(texture);
        VertexConsumer vertexConsumer = bufferSource.getBuffer(theLastEndRenderType);

        //渲染模型
        getRenderer().reRender(
            renderState,
            poseStack,
            bakedModel,
            bufferSource,
            theLastEndRenderType,
            vertexConsumer,
            packedLight,
            OverlayTexture.NO_OVERLAY, -1);
    }
}
