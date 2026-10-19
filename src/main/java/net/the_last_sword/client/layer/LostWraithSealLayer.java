package net.the_last_sword.client.layer;

import com.mojang.renderpearl.api.pipeline.BlendFunction;
import com.mojang.renderpearl.api.pipeline.RenderPipeline;
import com.mojang.renderpearl.api.pipeline.ColorTargetState;
import com.mojang.renderpearl.api.pipeline.DepthStencilState;
import com.mojang.renderpearl.api.pipeline.CompareOp;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.renderpearl.api.vertex.VertexFormat;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.RenderPipelines;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.the_last_sword.entity.LostWraithEntity;
import net.the_last_sword.entity.TheLastEndEntity;
import org.joml.Matrix4f;
import com.geckolib.cache.model.BakedGeoModel;
import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.PerBoneRender;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.GeoRenderLayer;
import com.geckolib.util.RenderUtil;

import java.util.function.BiConsumer;

public final class LostWraithSealLayer<R extends EntityRenderState & GeoRenderState> extends GeoRenderLayer<LostWraithEntity, Void, R> {
    //提取阶段记录封印状态（渲染状态不再持有实体）
    private static final DataTicket<Boolean> UNSPAWNED =
            DataTicket.create("the_last_sword_lost_wraith_unspawned", Boolean.class);
    private static final int LINKS = 12;
    private static final int SEGMENTS = 12;
    private static final int SIDES = 4;
    private static final Vec3[][] LINK_VERTICES = createLinks();
    //26.3: a pipeline must declare every uniform block its shaders read (position_color: Globals, Projection,
    //DynamicTransforms -- vanilla's DEBUG_FILLED_SNIPPET, which also carries the shaders, format and translucent
    //blend), or it fails to compile ("Unable to find shader defined uniform (Projection)"); 26.x is reverse-Z, so
    //1.21's LEQUAL_DEPTH_TEST is GREATER_THAN_OR_EQUAL (CATALOG V92), with depth writes as 1.21 had them
    private static final RenderPipeline SEAL_PIPELINE = RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
            .withLocation("pipeline/lost_wraith_seal")
            .withVertexShader("core/position_color")
            .withFragmentShader("core/position_color")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withDepthStencilState(new DepthStencilState(CompareOp.GREATER_THAN_OR_EQUAL, true))
            .withCull(false)
            .withVertexBinding(0, DefaultVertexFormat.POSITION_COLOR)
            .withPrimitiveTopology(com.mojang.renderpearl.api.pipeline.PrimitiveTopology.QUADS)
            .build();
    private static final RenderType SEAL = RenderType.create("lost_wraith_seal",
            RenderSetup.builder(SEAL_PIPELINE).sortOnUpload().createRenderSetup());

    public LostWraithSealLayer(GeoRenderer<LostWraithEntity, Void, R> renderer) {
        super(renderer);
    }

    @Override
    public void addRenderData(LostWraithEntity entity, Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(UNSPAWNED, entity.getAnimationState() == TheLastEndEntity.STATE_UNSPAWNED);
    }

    //GeckoLib 5: renderForBone became per-bone tasks registered here and run after the model is drawn, with the
    //PoseStack the bone had when it was rendered (the same pose renderForBone received)
    //GeckoLib 5.4: the hook takes the RenderPassInfo; willRender() is 5.3's didRenderModel, and a task runs inside
    //renderPosed, so the bone's frameSnapshot is the visibility GeoBone.isHidden() used to carry
    @Override
    public void addPerBoneRender(RenderPassInfo<R> renderPassInfo, BiConsumer<GeoBone, PerBoneRender<R>> consumer) {
        if (!renderPassInfo.willRender()) {
            return;
        }
        // 激活动画开始即解除封印，不等待战斗就绪状态。
        if (!Boolean.TRUE.equals(renderPassInfo.renderState().getGeckolibData(UNSPAWNED))) {
            return;
        }
        for (String name : new String[]{"left_hand", "right_hand"}) {
            renderPassInfo.model().getBone(name).ifPresent(bone -> consumer.accept(bone,
                    (info, b, renderTasks) -> renderSeal(info.renderState(), info.poseStack(), b,
                            "left_hand".equals(name), renderTasks)));
        }
    }

    private void renderSeal(R renderState, PoseStack poseStack, GeoBone bone, boolean left, SubmitNodeCollector renderTasks) {
        if (bone.frameSnapshot != null && bone.frameSnapshot.isHidden()) {
            return;
        }

        poseStack.pushPose();
        //GeckoLib 5.4.5 and 26.1.2's 5.5.2: the per-bone task runs after RenderUtil.transformToBone, which already ends
        //at the bone's pivot (5.3's ended away from it, so 1.21.10 translated to the pivot here)
        // 模型两侧前臂的枢轴不对称，分别对齐护腕中心，避免链环穿入手臂。
        poseStack.translate(left ? -1.0 / 16.0 : 2.8 / 16.0, -6.0 / 16.0,
                left ? -2.5 / 16.0 : 0.0);
        float pulse = 0.88F + 0.12F * Mth.sin(renderState.ageInTicks * 0.07F);
        renderTasks.submitCustomGeometry(poseStack, SEAL, (last, vertices) -> {
            Matrix4f pose = last.pose();
            for (Vec3[] link : LINK_VERTICES) {
                for (int segment = 0; segment < SEGMENTS; segment++) {
                    for (int side = 0; side < SIDES; side++) {
                        float shade = side == 0 ? 1.0F : side == 2 ? 0.55F : 0.78F;
                        int nextSegment = (segment + 1) % SEGMENTS;
                        int nextSide = (side + 1) % SIDES;
                        vertex(vertices, pose, link[segment * SIDES + side], shade, pulse);
                        vertex(vertices, pose, link[nextSegment * SIDES + side], shade, pulse);
                        vertex(vertices, pose, link[nextSegment * SIDES + nextSide], shade, pulse);
                        vertex(vertices, pose, link[segment * SIDES + nextSide], shade, pulse);
                    }
                }
            }
        });
        poseStack.popPose();
    }

    private static void vertex(VertexConsumer buffer, Matrix4f pose, Vec3 point, float shade, float pulse) {
        buffer.addVertex(pose, (float) point.x, (float) point.y, (float) point.z)
                .setColor(0.72F * shade * pulse, 0.22F * shade * pulse, shade * pulse, 0.9F);
    }

    private static Vec3[][] createLinks() {
        Vec3[][] links = new Vec3[LINKS][SEGMENTS * SIDES];
        for (int link = 0; link < LINKS; link++) {
            double angle = Math.PI * 2 * link / LINKS;
            Vec3 radial = new Vec3(Math.cos(angle), 0, Math.sin(angle));
            Vec3 tangent = new Vec3(-Math.sin(angle), 0, Math.cos(angle));
            double tilt = link % 2 == 0 ? Math.PI / 4 : -Math.PI / 4;
            Vec3 across = new Vec3(0, Math.cos(tilt), 0).add(radial.scale(Math.sin(tilt)));
            Vec3 normal = tangent.cross(across);
            Vec3 center = radial.scale(0.34);
            for (int segment = 0; segment < SEGMENTS; segment++) {
                double phase = Math.PI * 2 * segment / SEGMENTS;
                Vec3 ring = center.add(tangent.scale(0.115 * Math.cos(phase)))
                        .add(across.scale(0.065 * Math.sin(phase)));
                Vec3 outward = tangent.scale(Math.cos(phase) / 0.115)
                        .add(across.scale(Math.sin(phase) / 0.065)).normalize();
                for (int side = 0; side < SIDES; side++) {
                    double tube = Math.PI * 2 * side / SIDES;
                    links[link][segment * SIDES + side] = ring
                            .add(outward.scale(0.014 * Math.cos(tube)))
                            .add(normal.scale(0.014 * Math.sin(tube)));
                }
            }
        }
        return links;
    }
}
