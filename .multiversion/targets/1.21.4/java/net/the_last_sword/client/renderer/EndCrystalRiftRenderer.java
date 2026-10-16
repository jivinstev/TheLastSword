package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.entity.EndCrystalRift;

public class EndCrystalRiftRenderer extends EntityRenderer<EndCrystalRift, EndCrystalRiftRenderer.RiftRenderState> {
    public static class RiftRenderState extends EntityRenderState {
        public EndCrystalRift entity;
        public float partialTick;
    }

    @Override
    public RiftRenderState createRenderState() {
        return new RiftRenderState();
    }

    @Override
    public void extractRenderState(EndCrystalRift entity, RiftRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.entity = entity;
        state.partialTick = partialTick;
    }
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
        TheLastSwordMod.MOD_ID, "textures/entities/queen_rift.png");

    public EndCrystalRiftRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(RiftRenderState state, PoseStack pose,
                       MultiBufferSource buffers, int light) {
        EndCrystalRift entity = state.entity;
        QueenBlinkRenderUtil.summonRift(pose, buffers, entity.getAnimationAge(state.partialTick), entity.getYRot());
        super.render(state, pose, buffers, light);
    }

    // 1.21.2+: no longer an EntityRenderer override
    public ResourceLocation getTextureLocation(RiftRenderState state) {
        return TEXTURE;
    }
}
