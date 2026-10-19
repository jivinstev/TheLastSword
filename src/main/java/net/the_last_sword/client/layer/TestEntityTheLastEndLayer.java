package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.Identifier;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.shader.TheLastEndEffect;
import net.the_last_sword.client.shader.TheLastEndRenderTypes;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;

//测试实体的 The Last End 星空剑刃特效渲染层
public class TestEntityTheLastEndLayer extends RenderLayer<HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    //测试实体纹理（着色器会在黑色区域自动应用星空效果）
    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "textures/entities/test_entity.png");

    public TestEntityTheLastEndLayer(RenderLayerParent<HumanoidRenderState, HumanoidModel<HumanoidRenderState>> renderer) {
        super(renderer);
    }

    @Override
    public void submit(
        PoseStack poseStack,
        SubmitNodeCollector collector,
        int packedLight,
        HumanoidRenderState entity,
        float yRot,
        float xRot
    ) {
        //检查着色器是否可用
        if (!TheLastEndEffect.isAvailable()) {
            //降级：使用半透明渲染
            collector.submitCustomGeometry(poseStack, net.neoforged.neoforge.client.NeoForgeRenderTypes.getItemLayeredTranslucent(TEXTURE), (pose, vertexConsumer) -> {
                this.getParentModel().renderToBuffer(
                    poseStack,
                    vertexConsumer,
                    15728880,  // 最大亮度
                    LivingEntityRenderer.getOverlayCoords(entity, 0.0f),
                    0xCCFFFFFF  // ARGB (80%透明度)
                );
            });
            return;
        }

        //应用 The Last End uniforms
        TheLastEndEffect.applyUniforms();

        //使用 The Last End 实体效果渲染类型
        RenderType renderType = TheLastEndRenderTypes.createEntityEffect(TEXTURE);

        //渲染实体模型
        collector.submitCustomGeometry(poseStack, renderType, (pose, vertexConsumer) -> {
            this.getParentModel().renderToBuffer(
                poseStack,
                vertexConsumer,
                packedLight,  // 使用原始光照
                LivingEntityRenderer.getOverlayCoords(entity, 0.0f),
                0xFFFFFFFF  // ARGB (完全不透明)
            );
        });
    }
}
