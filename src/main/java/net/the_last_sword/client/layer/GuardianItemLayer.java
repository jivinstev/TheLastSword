package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.the_last_sword.entity.GuardianOfSealedSpireEntity;
import software.bernie.geckolib.cache.model.BakedGeoModel;
import software.bernie.geckolib.cache.model.GeoBone;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import software.bernie.geckolib.util.RenderUtil;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;

import javax.annotation.Nullable;
import java.util.List;

//封印尖塔守卫物品渲染层
public class GuardianItemLayer<R extends EntityRenderState & GeoRenderState> extends BlockAndItemGeoLayer<GuardianOfSealedSpireEntity, Void, R> {

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
    protected List<RenderData<R>> getRelevantBones(R renderState, BakedGeoModel model) {
        return List.of(
                new RenderData<>(RIGHT_HAND, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                        (bone, state) -> Either.left(stackOrEmpty(getStackForBone(bone, state)))),
                new RenderData<>(LEFT_HAND, ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                        (bone, state) -> Either.left(stackOrEmpty(getStackForBone(bone, state)))));
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

    //GeckoLib 5.3: renderStackForBone is submitItemStackRender, with the render colour as a third int
    @Override
    protected void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                         ItemDisplayContext boneContext, R renderState,
                                         SubmitNodeCollector renderTasks, CameraRenderState cameraState,
                                         int packedLight, int packedOverlay, int renderColor) {
        //GeckoLib 5.4.5: the per-bone task runs after RenderUtil.transformToBone, which already ends at the bone's pivot
        //with its rotation applied once. GeckoLib 4 (the authors') and 5.3 (1.21.10) applied the bone's rotation a second
        //time here (prepMatrixForBone, then translateAndRotateMatrixForBone); the offsets below were tuned for that,
        //so step back off the pivot and apply it again, ending at the pivot as before
        bone.translateAwayFromPivotPoint(poseStack);
        RenderUtil.translateAndRotateMatrixForBone(poseStack, bone);
        //显示方式取决于物品本身（盾牌），而不仅是骨骼
        ItemDisplayContext displayContext = getTransformTypeForStack(bone, stack, renderState);
        //新版left_hand枢轴就是盾牌挂点：盾牌模型本身已是竖直的，只需转出盾面朝外、握把贴身的持盾姿态
        if (stack.is(Items.SHIELD)) {
            poseStack.mulPose(Axis.YP.rotationDegrees(SHIELD_FACE_ROTATION));
            //抵消ItemRenderer对方块模型的半格居中平移，让盾牌握把落在枢轴上
            poseStack.translate(0.5, 0.5, 0.5);
            super.submitItemStackRender(poseStack, bone, stack, displayContext, renderState, renderTasks,
                    cameraState, packedLight, packedOverlay, renderColor);
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

        super.submitItemStackRender(poseStack, bone, stack, displayContext, renderState, renderTasks, cameraState,
                packedLight, packedOverlay, renderColor);
    }
}
