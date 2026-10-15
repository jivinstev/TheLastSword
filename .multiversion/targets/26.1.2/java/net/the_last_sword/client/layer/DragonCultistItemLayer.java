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
import net.the_last_sword.entity.DragonCultistEntity;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.PerBoneRender;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.util.RenderUtil;

import java.util.function.BiConsumer;

//拜龙教教徒物品渲染层
//GeckoLib 5: the render state no longer carries the entity, so the hand items are captured in addRenderData.
//GeckoLib 5.5 reworked BlockAndItemGeoLayer (context constructor, ItemStackRenderState hooks), so this layer registers
//its per-bone item tasks itself and resolves the stack through the item model resolver, exactly as BlockAndItemGeoLayer did
public class DragonCultistItemLayer<R extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<DragonCultistEntity, Void, R> {

    private static final DataTicket<ItemStack> MAIN_HAND_STACK =
            DataTicket.create("the_last_sword_dragon_cultist_main_hand", ItemStack.class);
    private static final DataTicket<ItemStack> OFF_HAND_STACK =
            DataTicket.create("the_last_sword_dragon_cultist_off_hand", ItemStack.class);

    //骨骼名称
    private static final String LEFT_HAND = "left_hand";
    private static final String RIGHT_HAND = "right_hand";

    //手持物品相对手部骨骼的偏移（像素）
    private static final float ITEM_OFFSET_X = 1.0F;
    private static final float ITEM_OFFSET_Z = -3.75F;

    public DragonCultistItemLayer(GeoRenderer<DragonCultistEntity, Void, R> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(DragonCultistEntity entity, Void relatedObject, R renderState, float partialTick) {
        //右手骨骼渲染主手物品，左手骨骼渲染副手物品
        renderState.addGeckolibData(MAIN_HAND_STACK, entity.getItemBySlot(EquipmentSlot.MAINHAND).copy());
        renderState.addGeckolibData(OFF_HAND_STACK, entity.getItemBySlot(EquipmentSlot.OFFHAND).copy());
    }

    @Override
    public void addPerBoneRender(RenderPassInfo<R> renderPassInfo, BiConsumer<GeoBone, PerBoneRender<R>> consumer) {
        if (!renderPassInfo.willRender()) {
            return;
        }
        R renderState = renderPassInfo.renderState();
        addItemBone(renderPassInfo, consumer, RIGHT_HAND, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                renderState.getOrDefaultGeckolibData(MAIN_HAND_STACK, ItemStack.EMPTY));
        addItemBone(renderPassInfo, consumer, LEFT_HAND, ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                renderState.getOrDefaultGeckolibData(OFF_HAND_STACK, ItemStack.EMPTY));
    }

    private void addItemBone(RenderPassInfo<R> renderPassInfo, BiConsumer<GeoBone, PerBoneRender<R>> consumer,
                             String boneName, ItemDisplayContext displayContext, ItemStack stack) {
        if (stack.isEmpty()) {
            return;
        }
        renderPassInfo.model().getBone(boneName).ifPresent(bone -> consumer.accept(bone,
                (info, b, renderTasks) -> submitItemStackRender(info.poseStack(), b, stack, displayContext,
                        renderTasks, info.packedLight())));
    }

    private void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                       ItemDisplayContext displayContext, SubmitNodeCollector renderTasks,
                                       int packedLight) {
        poseStack.pushPose();
        //GeckoLib 5.3+ no longer moves the pose to the bone's pivot before the item is drawn; do it here so the
        //offsets below keep their meaning
        RenderUtil.translateAndRotateMatrixForBone(poseStack, bone);
        //向外旋转90度
        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        poseStack.translate(ITEM_OFFSET_X / 16.0, 0, ITEM_OFFSET_Z / 16.0);

        ItemStackRenderState stackRenderState = new ItemStackRenderState();
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getItemModelResolver().updateForTopItem(stackRenderState, stack, displayContext, minecraft.level, null,
                displayContext.ordinal());
        stackRenderState.submit(poseStack, renderTasks, packedLight, OverlayTexture.NO_OVERLAY, 0);
        poseStack.popPose();
    }
}
