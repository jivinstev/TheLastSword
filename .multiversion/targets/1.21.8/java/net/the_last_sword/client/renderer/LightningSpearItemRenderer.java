package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Vector3f;
import java.util.Set;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.TheLastSwordMod;
import org.jetbrains.annotations.NotNull;

// 1.21.4: a special model renderer (items/lightning_spear.json, type the_last_sword:lightning_spear) replaces 1.21.1's
// BlockEntityWithoutLevelRenderer handed out by LightningSpearClientExtensions.getCustomRenderer.
public class LightningSpearItemRenderer implements NoDataSpecialModelRenderer {
    private static final long ANIMATION_START_NANOS = System.nanoTime();

    public LightningSpearItemRenderer() {
    }

    @Override
    public void render(@NotNull ItemDisplayContext displayContext, @NotNull PoseStack poseStack,
                       @NotNull MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoil) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        LightningSpearRenderUtil.render(poseStack, bufferSource, animationAge());
        poseStack.popPose();
    }

    @Override
    public void getExtents(Set<Vector3f> output) {
        output.add(new Vector3f(0.0F, 0.0F, 0.0F));
        output.add(new Vector3f(1.0F, 0.0F, 0.0F));
        output.add(new Vector3f(0.0F, 1.0F, 0.0F));
        output.add(new Vector3f(0.0F, 0.0F, 1.0F));
        output.add(new Vector3f(1.0F, 1.0F, 0.0F));
        output.add(new Vector3f(1.0F, 0.0F, 1.0F));
        output.add(new Vector3f(0.0F, 1.0F, 1.0F));
        output.add(new Vector3f(1.0F, 1.0F, 1.0F));
    }

    private static float animationAge() {
        return (System.nanoTime() - ANIMATION_START_NANOS) / 50_000_000.0F;
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = MapCodec.unit(new Unbaked());

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new LightningSpearItemRenderer();
        }
    }

    @EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
    public static final class Registration {
        private Registration() {
        }

        @SubscribeEvent
        public static void onRegisterSpecialRenderers(RegisterSpecialModelRendererEvent event) {
            event.register(ResourceLocation.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "lightning_spear"), Unbaked.MAP_CODEC);
        }
    }
}
