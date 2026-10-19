package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.the_last_sword.client.model.WingsThatCoverTheWorldModel;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

//覆世之翼渲染器 - 在玩家背部渲染翅膀模型
public class WingsThatCoverTheWorldRenderer implements ICurioRenderer {

    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath("the_last_sword", "textures/entity/curios/wings_that_cover_the_world.png");

    private final WingsThatCoverTheWorldModel<LivingEntity> model;

    public WingsThatCoverTheWorldRenderer() {
        this.model = new WingsThatCoverTheWorldModel<>(
            Minecraft.getInstance().getEntityModels().bakeLayer(WingsThatCoverTheWorldModel.LAYER_LOCATION)
        );
    }

    @Override
    public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            MultiBufferSource renderTypeBuffer,
            int light,
            S renderState,
            RenderLayerParent<S, M> renderLayerParent,
            EntityRendererProvider.Context context,
            float yRotation,
            float xRotation) {

        poseStack.pushPose();

        //跟随玩家身体旋转（蹲下时）
        ICurioRenderer.rotateIfSneaking(poseStack, slotContext.entity());
        ICurioRenderer.translateIfSneaking(poseStack, slotContext.entity());

        //设置翅膀动画
        this.model.setupAnim(slotContext.entity(), renderState.walkAnimationPos, renderState.walkAnimationSpeed,
            renderState.ageInTicks, yRotation, xRotation);

        VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderTypes.entityCutoutNoCull(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY, -1);

        poseStack.popPose();
    }
}
