package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.the_last_sword.client.model.WingsThatCoverTheWorldModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

//覆世之翼渲染器 - 在玩家背部渲染翅膀模型
public class WingsThatCoverTheWorldRenderer implements ICurioRenderer {

    private static final ResourceLocation TEXTURE =
        ResourceLocation.fromNamespaceAndPath("the_last_sword", "textures/entity/curios/wings_that_cover_the_world.png");

    private final WingsThatCoverTheWorldModel<LivingEntity> model;

    public WingsThatCoverTheWorldRenderer() {
        this.model = new WingsThatCoverTheWorldModel<>(
            Minecraft.getInstance().getEntityModels().bakeLayer(WingsThatCoverTheWorldModel.LAYER_LOCATION)
        );
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource renderTypeBuffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        poseStack.pushPose();

        //跟随玩家身体旋转（蹲下时）
        ICurioRenderer.rotateIfSneaking(poseStack, slotContext.entity());
        ICurioRenderer.translateIfSneaking(poseStack, slotContext.entity());

        //设置翅膀动画
        this.model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, -1);

        poseStack.popPose();
    }
}
