package net.the_last_sword.client.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Either;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.client.renderer.ThePastShadowOfTheQueenRenderer;
import net.the_last_sword.entity.ThePastShadowOfTheQueenEntity;
import net.the_last_sword.init.ModItems;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

import java.util.List;

//GeckoLib 5: the right-hand item is decided in addRenderData (the render state does not carry the entity) and
//the layer names its bone up front
public class ThePastShadowOfTheQueenItemLayer<R extends ThePastShadowOfTheQueenRenderer.QueenRenderState & GeoRenderState>
        extends BlockAndItemGeoLayer<ThePastShadowOfTheQueenEntity, Void, R> {
    private static final DataTicket<ItemStack> RIGHT_HAND_STACK =
            DataTicket.create("the_last_sword_queen_right_hand", ItemStack.class);
    private final ItemStack lightningSpear = new ItemStack(ModItems.LIGHTNING_SPEAR.get());

    public ThePastShadowOfTheQueenItemLayer(GeoRenderer<ThePastShadowOfTheQueenEntity, Void, R> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(ThePastShadowOfTheQueenEntity entity, Void relatedObject, R renderState) {
        ItemStack stack;
        if (entity.isDying()) {
            stack = entity.getMainHandItem();
        } else {
            stack = entity.isLightningSpearVisible() ? lightningSpear : ItemStack.EMPTY;
        }
        renderState.addGeckolibData(RIGHT_HAND_STACK, stack);
    }

    @Override
    protected List<RenderData<R>> getRelevantBones(R renderState, BakedGeoModel model) {
        return List.of(new RenderData<>("right_hand", ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                (bone, state) -> Either.left(state.getOrDefaultGeckolibData(RIGHT_HAND_STACK, ItemStack.EMPTY))));
    }

    @Override
    protected void renderStackForBone(PoseStack poseStack, GeoBone bone, ItemStack stack,
                                      ItemDisplayContext displayContext, R renderState,
                                      MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        // 手部骨骼的枢轴在肘部，沿前臂下移到掌心后再应用物品变换。
        poseStack.translate(0.0D, -7.0D / 16.0D, 0.0D);
        poseStack.mulPose(Axis.XP.rotationDegrees(-90.0F));
        super.renderStackForBone(poseStack, bone, stack, displayContext, renderState, bufferSource,
                packedLight, packedOverlay);
    }

}
