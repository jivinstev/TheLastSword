package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.entity.ThePastShadowOfTheQueenEntity;
import net.the_last_sword.init.ModItems;
import net.the_last_sword.mv.GeoItemLayers;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer;
import com.geckolib.util.RenderUtil;

import java.util.List;

//GeckoLib 5.5: the right-hand item is decided and resolved during extraction (getRelevantBones gets the entity), the
//list is published in addRenderData, and submitItemStackRender receives the resolved ItemStackRenderState
public class ThePastShadowOfTheQueenItemLayer<R extends EntityRenderState & GeoRenderState>
        extends BlockAndItemGeoLayer<ThePastShadowOfTheQueenEntity, Void, R> {
    //26.1 builds entity renderers in the first resource reload, before item components are bound, so an ItemStack made
    //in the constructor ("Components not bound yet") kills the client: make it on first use instead
    private ItemStack lightningSpear;

    public ThePastShadowOfTheQueenItemLayer(EntityRendererProvider.Context context,
                                            GeoRenderer<ThePastShadowOfTheQueenEntity, Void, R> renderer) {
        super(context, renderer);
    }

    @Override
    protected List<RenderData> getRelevantBones(ThePastShadowOfTheQueenEntity entity, Void relatedObject, R renderState,
                                                float partialTick) {
        ItemStack stack;
        if (entity.isDying()) {
            stack = entity.getMainHandItem();
        } else {
            if (entity.isLightningSpearVisible()) {
                if (lightningSpear == null) {
                    lightningSpear = new ItemStack(ModItems.LIGHTNING_SPEAR.get());
                }
                stack = lightningSpear;
            } else {
                stack = ItemStack.EMPTY;
            }
        }
        return List.of(GeoItemLayers.item("right_hand", ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, stack,
                this.itemModelResolver, entity));
    }

    @Override
    public void addRenderData(ThePastShadowOfTheQueenEntity entity, Void relatedObject, R renderState, float partialTick) {
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
        // 手部骨骼的枢轴在肘部，沿前臂下移到掌心后再应用物品变换。
        poseStack.translate(0.0D, -7.0D / 16.0D, 0.0D);
        poseStack.rotate(Axis.XP.rotationDegrees(-90.0F));
        super.submitItemStackRender(poseStack, bone, stackRenderState, displayContext, renderState, renderTasks, packedLight);
        poseStack.popPose();
    }

}
