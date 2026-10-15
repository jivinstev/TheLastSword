package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.entity.ThePastShadowOfTheQueenEntity;
import net.the_last_sword.init.ModItems;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.PerBoneRender;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.util.RenderUtil;

import java.util.function.BiConsumer;

//GeckoLib 5: the right-hand item is decided in addRenderData (the render state does not carry the entity)
//GeckoLib 5.5 reworked BlockAndItemGeoLayer (context constructor, ItemStackRenderState hooks), so this layer registers
//its per-bone item task itself and resolves the stack through the item model resolver
public class ThePastShadowOfTheQueenItemLayer<R extends EntityRenderState & GeoRenderState>
        extends GeoRenderLayer<ThePastShadowOfTheQueenEntity, Void, R> {
    private static final DataTicket<ItemStack> RIGHT_HAND_STACK =
            DataTicket.create("the_last_sword_queen_right_hand", ItemStack.class);
    private final ItemStack lightningSpear = new ItemStack(ModItems.LIGHTNING_SPEAR.get());

    public ThePastShadowOfTheQueenItemLayer(GeoRenderer<ThePastShadowOfTheQueenEntity, Void, R> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(ThePastShadowOfTheQueenEntity entity, Void relatedObject, R renderState, float partialTick) {
        ItemStack stack;
        if (entity.isDying()) {
            stack = entity.getMainHandItem();
        } else {
            stack = entity.isLightningSpearVisible() ? lightningSpear : ItemStack.EMPTY;
        }
        renderState.addGeckolibData(RIGHT_HAND_STACK, stack);
    }

    @Override
    public void addPerBoneRender(RenderPassInfo<R> renderPassInfo, BiConsumer<GeoBone, PerBoneRender<R>> consumer) {
        if (!renderPassInfo.willRender()) {
            return;
        }
        renderPassInfo.model().getBone("right_hand").ifPresent(bone -> {
            ItemStack stack = renderPassInfo.renderState().getOrDefaultGeckolibData(RIGHT_HAND_STACK, ItemStack.EMPTY);
            if (stack.isEmpty()) {
                return;
            }
            consumer.accept(bone, (info, b, renderTasks) -> submitItemStackRender(info.poseStack(), b, stack,
                    ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, renderTasks, info.packedLight()));
        });
    }

    private void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                       ItemDisplayContext displayContext, SubmitNodeCollector renderTasks,
                                       int packedLight) {
        poseStack.pushPose();
        //GeckoLib 5.3+ no longer moves the pose to the bone's pivot before the item is drawn; do it here so the
        //offsets below keep their meaning
        RenderUtil.translateAndRotateMatrixForBone(poseStack, bone);
        // 手部骨骼的枢轴在肘部，沿前臂下移到掌心后再应用物品变换。
        poseStack.translate(0.0D, -7.0D / 16.0D, 0.0D);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        ItemStackRenderState stackRenderState = new ItemStackRenderState();
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getItemModelResolver().updateForTopItem(stackRenderState, stack, displayContext, minecraft.level, null,
                displayContext.ordinal());
        stackRenderState.submit(poseStack, renderTasks, packedLight, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }

}
