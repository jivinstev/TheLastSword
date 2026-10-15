package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.the_last_sword.entity.GuardianOfSealedSpireEntity;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.PerBoneRender;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.util.RenderUtil;

import javax.annotation.Nullable;
import java.util.function.BiConsumer;

//封印尖塔守卫物品渲染层
//GeckoLib 5.5 reworked BlockAndItemGeoLayer (context constructor, ItemStackRenderState hooks), so this layer registers
//its per-bone item tasks itself and resolves the stack through the item model resolver, exactly as BlockAndItemGeoLayer did
public class GuardianItemLayer<R extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<GuardianOfSealedSpireEntity, Void, R> {

    //提取阶段记录手持物与类型（渲染状态不再持有实体）
    private static final DataTicket<ItemStack> MAIN_HAND_STACK =
            DataTicket.create("the_last_sword_guardian_main_hand", ItemStack.class);
    private static final DataTicket<ItemStack> OFF_HAND_STACK =
            DataTicket.create("the_last_sword_guardian_off_hand", ItemStack.class);
    private static final DataTicket<Boolean> IS_ARCHER =
            DataTicket.create("the_last_sword_guardian_is_archer", Boolean.class);

    //骨骼名称
    private static final String LEFT_HAND = "left_hand";
    private static final String RIGHT_HAND = "right_hand";

    //手持物相对新版手腕骨骼的偏移（像素）
    private static final float ITEM_OFFSET_Z = -3.75F;
    private static final float ARCHER_EXTRA_OFFSET = 1.0F;
    private static final float ITEM_FORWARD_OFFSET = 1.0F;
    private static final float ITEM_UPWARD_ROTATION = 10.0F;

    //盾面朝向：-90度为盾面朝身体外侧、握把贴身，180则正对前方
    private static final float SHIELD_FACE_ROTATION = -90.0F;

    public GuardianItemLayer(GeoRenderer<GuardianOfSealedSpireEntity, Void, R> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(GuardianOfSealedSpireEntity entity, Void relatedObject, R renderState, float partialTick) {
        GeoRenderState geoState = renderState;
        geoState.addGeckolibData(MAIN_HAND_STACK, entity.getItemBySlot(EquipmentSlot.MAINHAND).copy());
        geoState.addGeckolibData(OFF_HAND_STACK, entity.getItemBySlot(EquipmentSlot.OFFHAND).copy());
        geoState.addGeckolibData(IS_ARCHER, entity.getGuardianType() == GuardianOfSealedSpireEntity.GuardianType.ARCHER);
    }

    //GeckoLib 5: the layer names its bones up front; each bone's stack is read from the render state
    @Override
    public void addPerBoneRender(RenderPassInfo<R> renderPassInfo, BiConsumer<GeoBone, PerBoneRender<R>> consumer) {
        if (!renderPassInfo.willRender()) {
            return;
        }
        for (String boneName : new String[]{RIGHT_HAND, LEFT_HAND}) {
            renderPassInfo.model().getBone(boneName).ifPresent(bone -> {
                ItemStack stack = stackOrEmpty(getStackForBone(bone, renderPassInfo.renderState()));
                if (stack.isEmpty()) {
                    return;
                }
                consumer.accept(bone, (info, b, renderTasks) -> submitItemStackRender(info.poseStack(), b, stack,
                        info.renderState(), renderTasks, info.packedLight()));
            });
        }
    }

    private static ItemStack stackOrEmpty(@Nullable ItemStack stack) {
        return stack == null ? ItemStack.EMPTY : stack;
    }

    @Nullable
    protected ItemStack getStackForBone(GeoBone bone, R renderState) {
        //右手骨骼渲染主手物品
        if (bone.name().equals(RIGHT_HAND)) {
            return renderState.getGeckolibData(MAIN_HAND_STACK);
        }
        //左手骨骼渲染副手物品
        if (bone.name().equals(LEFT_HAND)) {
            return renderState.getGeckolibData(OFF_HAND_STACK);
        }
        return null;
    }

    protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, R renderState) {
        //盾牌直接以left_hand枢轴为挂点，绕过原版第三人称左手自带的平移
        if (stack.is(Items.SHIELD)) {
            return ItemDisplayContext.NONE;
        }
        if (bone.name().equals(RIGHT_HAND)) {
            return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
        }
        return ItemDisplayContext.THIRD_PERSON_LEFT_HAND;
    }

    private void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStack stack, R renderState,
                                       SubmitNodeCollector renderTasks, int packedLight) {
        poseStack.pushPose();
        //GeckoLib 5.3+ no longer moves the pose to the bone's pivot before the item is drawn; do it here so the
        //offsets below keep their meaning
        RenderUtil.translateAndRotateMatrixForBone(poseStack, bone);
        //显示方式取决于物品本身（盾牌），而不仅是骨骼
        ItemDisplayContext displayContext = getTransformTypeForStack(bone, stack, renderState);
        //新版left_hand枢轴就是盾牌挂点：盾牌模型本身已是竖直的，只需转出盾面朝外、握把贴身的持盾姿态
        if (stack.is(Items.SHIELD)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(SHIELD_FACE_ROTATION));
            //抵消ItemRenderer对方块模型的半格居中平移，让盾牌握把落在枢轴上
            poseStack.translate(0.5, 0.5, 0.5);
            submitStack(poseStack, stack, displayContext, renderTasks, packedLight);
            poseStack.popPose();
            return;
        }

        //先沿守卫正前方移动，再将物品朝上额外旋转10度
        poseStack.translate(0, 0, -ITEM_FORWARD_OFFSET / 16.0);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F + ITEM_UPWARD_ROTATION));

        //弓的模型比近战武器更宽，保留少量额外偏移
        if (Boolean.TRUE.equals(renderState.getGeckolibData(IS_ARCHER))) {
            poseStack.translate(ARCHER_EXTRA_OFFSET / 16.0,
                    ARCHER_EXTRA_OFFSET / 16.0, ITEM_OFFSET_Z / 16.0);
        } else {
            poseStack.translate(0, 0, ITEM_OFFSET_Z / 16.0);
        }

        submitStack(poseStack, stack, displayContext, renderTasks, packedLight);
        poseStack.popPose();
    }

    private static void submitStack(PoseStack poseStack, ItemStack stack, ItemDisplayContext displayContext,
                                    SubmitNodeCollector renderTasks, int packedLight) {
        ItemStackRenderState stackRenderState = new ItemStackRenderState();
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getItemModelResolver().updateForTopItem(stackRenderState, stack, displayContext, minecraft.level, null,
                displayContext.ordinal());
        stackRenderState.submit(poseStack, renderTasks, packedLight, OverlayTexture.NO_OVERLAY, 0);
    }
}
