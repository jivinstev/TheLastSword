package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.the_last_sword.entity.DragonCultistEntity;
import net.the_last_sword.mv.GeoItemLayers;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import com.geckolib.util.RenderUtil;

import java.util.List;

//拜龙教教徒物品渲染层
//GeckoLib 5.5: the layer resolves each bone's item during extraction (getRelevantBones gets the entity again) and
//publishes the list in addRenderData; submitItemStackRender receives the resolved ItemStackRenderState
public class DragonCultistItemLayer<R extends EntityRenderState & GeoRenderState> extends BlockAndItemGeoLayer<DragonCultistEntity, Void, R> {

    //骨骼名称
    private static final String LEFT_HAND = "left_hand";
    private static final String RIGHT_HAND = "right_hand";

    //手持物品相对手部骨骼的偏移（像素）
    private static final float ITEM_OFFSET_X = 1.0F;
    private static final float ITEM_OFFSET_Z = -3.75F;

    public DragonCultistItemLayer(EntityRendererProvider.Context context, GeoRenderer<DragonCultistEntity, Void, R> renderer) {
        super(context, renderer);
    }

    //右手骨骼渲染主手物品，左手骨骼渲染副手物品
    @Override
    protected List<RenderData> getRelevantBones(DragonCultistEntity entity, Void relatedObject, R renderState, float partialTick) {
        return List.of(
                GeoItemLayers.item(RIGHT_HAND, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                        entity.getItemBySlot(EquipmentSlot.MAINHAND), this.itemModelResolver, entity),
                GeoItemLayers.item(LEFT_HAND, ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                        entity.getItemBySlot(EquipmentSlot.OFFHAND), this.itemModelResolver, entity));
    }

    @Override
    public void addRenderData(DragonCultistEntity entity, Void relatedObject, R renderState, float partialTick) {
        GeoItemLayers.publish(renderState, getRelevantBones(entity, relatedObject, renderState, partialTick));
    }

    @Override
    protected void submitItemStackRender(PoseStack poseStack, GeoBone bone, ItemStackRenderState stackRenderState,
                                         ItemDisplayContext displayContext, R renderState,
                                         SubmitNodeCollector renderTasks, int packedLight) {
        poseStack.pushPose();
        //GeckoLib 5.3+ no longer moves the pose to the bone's pivot before this hook (5.1/5.2 called
        //RenderUtil.translateAndRotateMatrixForBone first); do it here so the offsets below keep their meaning
        RenderUtil.translateAndRotateMatrixForBone(poseStack, bone);
        //向外旋转90度
        poseStack.mulPose(Axis.XP.rotationDegrees(-90));
        poseStack.translate(ITEM_OFFSET_X / 16.0, 0, ITEM_OFFSET_Z / 16.0);

        super.submitItemStackRender(poseStack, bone, stackRenderState, displayContext, renderState, renderTasks, packedLight);
        poseStack.popPose();
    }
}
