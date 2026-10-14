package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.entity.DragonCultistEntity;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.util.List;

//拜龙教教徒物品渲染层
//GeckoLib 5: the render state no longer carries the entity, so the hand items are captured in addRenderData,
//and the layer names its bones (with their display context) up front instead of being asked per bone
public class DragonCultistItemLayer<R extends EntityRenderState & GeoRenderState> extends BlockAndItemGeoLayer<DragonCultistEntity, Void, R> {

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
    protected List<RenderData<R>> getRelevantBones(R renderState, BakedGeoModel model) {
        return List.of(
                new RenderData<>(RIGHT_HAND, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                        (bone, state) -> Either.left(state.getOrDefaultGeckolibData(MAIN_HAND_STACK, ItemStack.EMPTY))),
                new RenderData<>(LEFT_HAND, ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                        (bone, state) -> Either.left(state.getOrDefaultGeckolibData(OFF_HAND_STACK, ItemStack.EMPTY))));
    }

    //手持物品姿态调整：1.21.10 的 GeckoLib 已不再提供 renderStackForBone 钩子，这里保留原有的变换逻辑
    private void applyHandItemPose(PoseStack poseStack) {
        //向外旋转90度
        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        poseStack.translate(ITEM_OFFSET_X / 16.0, 0, ITEM_OFFSET_Z / 16.0);
    }
}
