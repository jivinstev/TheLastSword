package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Vector3f;
import java.util.function.Consumer;
import org.joml.Vector3fc;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterSpecialModelRendererEvent;
import net.minecraft.resources.Identifier;
import net.the_last_sword.TheLastSwordMod;
import org.jetbrains.annotations.NotNull;

// 1.21.4: a special model renderer (items/lightning_spear.json, type the_last_sword:lightning_spear) replaces 1.21.1's
// BlockEntityWithoutLevelRenderer handed out by LightningSpearClientExtensions.getCustomRenderer.
public class LightningSpearItemRenderer implements NoDataSpecialModelRenderer {
    private static final long ANIMATION_START_NANOS = System.nanoTime();

    public LightningSpearItemRenderer() {
    }

    @Override
    public void submit(ItemDisplayContext displayContext, PoseStack poseStack, SubmitNodeCollector collector,
                       int packedLight, int packedOverlay, boolean hasFoil, int outlineColor) {
        poseStack.pushPose();
        poseStack.translate(0.5D, 0.5D, 0.5D);
        LightningSpearRenderUtil.render(poseStack, Minecraft.getInstance().renderBuffers().bufferSource(), animationAge());
        poseStack.popPose();
    }

    @Override
    public void getExtents(Consumer<Vector3fc> output) {
        output.accept(new Vector3f(0.0F, 0.0F, 0.0F));
        output.accept(new Vector3f(1.0F, 0.0F, 0.0F));
        output.accept(new Vector3f(0.0F, 1.0F, 0.0F));
        output.accept(new Vector3f(0.0F, 0.0F, 1.0F));
        output.accept(new Vector3f(1.0F, 1.0F, 0.0F));
        output.accept(new Vector3f(1.0F, 0.0F, 1.0F));
        output.accept(new Vector3f(0.0F, 1.0F, 1.0F));
        output.accept(new Vector3f(1.0F, 1.0F, 1.0F));
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
        public SpecialModelRenderer<?> bake(SpecialModelRenderer.BakingContext context) {
            return new LightningSpearItemRenderer();
        }
    }

    @EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
    public static final class Registration {
        private Registration() {
        }

        @SubscribeEvent
        public static void onRegisterSpecialRenderers(RegisterSpecialModelRendererEvent event) {
            event.register(Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "lightning_spear"), Unbaked.MAP_CODEC);
        }
    }
}
