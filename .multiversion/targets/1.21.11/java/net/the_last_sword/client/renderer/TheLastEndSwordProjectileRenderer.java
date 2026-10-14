package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.the_last_sword.entity.TheLastEndSwordProjectile;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import java.util.Deque;

//最终之剑弹射物渲染器 - 末影水晶模型 + 红蓝螺旋拖尾
public class TheLastEndSwordProjectileRenderer extends EntityRenderer<TheLastEndSwordProjectile, TheLastEndSwordProjectileRenderer.TrailRenderState> {

    //渲染状态：在提取阶段拷贝实体数据，供渲染阶段使用
    public static class TrailRenderState extends EntityRenderState {
        public int tickCount;
        public float partialTicks;
        public Vec3 currentPos;
        public Vec3 entityRenderPos;
        public Vec3[] trailPositions = new Vec3[0];
    }

    private static final Identifier END_CRYSTAL_LOCATION = Identifier.parse("textures/entity/end_crystal/end_crystal.png");
    private static final RenderType RENDER_TYPE = RenderTypes.entityCutout(END_CRYSTAL_LOCATION);
    private static final float SIN_45 = (float) Math.sin(0.7853981633974483);

    //拖尾参数
    private static final float TRAIL_WIDTH = 0.15f;
    private static final int FULL_BRIGHT = 0xF000F0;

    private final ModelPart cube;
    private final ModelPart glass;

    public TheLastEndSwordProjectileRenderer(EntityRendererProvider.Context context) {
        super(context);
        ModelPart root = context.bakeLayer(ModelLayers.END_CRYSTAL);
        this.glass = root.getChild("glass");
        this.cube = root.getChild("cube");
    }

    @Override
    public TrailRenderState createRenderState() {
        return new TrailRenderState();
    }

    @Override
    public void extractRenderState(TheLastEndSwordProjectile entity, TrailRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        state.tickCount = entity.tickCount;
        state.partialTicks = partialTicks;
        state.currentPos = entity.position();
        state.entityRenderPos = new Vec3(
                Mth.lerp(partialTicks, entity.xOld, entity.getX()),
                Mth.lerp(partialTicks, entity.yOld, entity.getY()),
                Mth.lerp(partialTicks, entity.zOld, entity.getZ())
        );
        Deque<Vec3> trail = entity.getTrailPositions();
        state.trailPositions = trail.toArray(new Vec3[0]);
    }

    @Override
    public void submit(TrailRenderState state, PoseStack poseStack, SubmitNodeCollector collector, CameraRenderState camera) {
        //渲染末影水晶模型
        collector.submitCustomGeometry(poseStack, RENDER_TYPE, (pose, consumer) ->
                renderCrystal(state, state.partialTicks, pose, consumer, state.lightCoords));

        //渲染拖尾
        collector.submitCustomGeometry(poseStack, RenderTypes.lightning(), (pose, consumer) ->
                renderTrail(state, state.partialTicks, pose, consumer));

        super.submit(state, poseStack, collector, camera);
    }

    //渲染末影水晶模型
    private void renderCrystal(TrailRenderState entity, float partialTicks, PoseStack.Pose basePose, VertexConsumer vertexConsumer, int packedLight) {
        PoseStack poseStack = new PoseStack();
        poseStack.last().set(basePose);
        poseStack.pushPose();

        float time = (entity.tickCount + partialTicks) * 3.0f;
        float bobbing = Mth.sin((entity.tickCount + partialTicks) * 0.2f) / 2.0f + 0.5f;
        bobbing = (bobbing * bobbing + bobbing) * 0.4f;
        float yOffset = bobbing - 1.4f;

        int overlay = OverlayTexture.NO_OVERLAY;

        poseStack.scale(2.0f, 2.0f, 2.0f);
        poseStack.translate(0.0f, -1.0f, 0.0f);

        poseStack.mulPose(Axis.YP.rotationDegrees(time));
        poseStack.translate(0.0f, 1.5f + yOffset / 2.0f, 0.0f);
        poseStack.mulPose(new Quaternionf().setAngleAxis(1.0471976f, SIN_45, 0.0f, SIN_45));
        this.glass.render(poseStack, vertexConsumer, packedLight, overlay);

        poseStack.scale(0.875f, 0.875f, 0.875f);
        poseStack.mulPose(new Quaternionf().setAngleAxis(1.0471976f, SIN_45, 0.0f, SIN_45));
        poseStack.mulPose(Axis.YP.rotationDegrees(time));
        this.glass.render(poseStack, vertexConsumer, packedLight, overlay);

        poseStack.scale(0.875f, 0.875f, 0.875f);
        poseStack.mulPose(new Quaternionf().setAngleAxis(1.0471976f, SIN_45, 0.0f, SIN_45));
        poseStack.mulPose(Axis.YP.rotationDegrees(time));
        this.cube.render(poseStack, vertexConsumer, packedLight, overlay);

        poseStack.popPose();
    }

    //渲染红蓝双螺旋拖尾
    private void renderTrail(TrailRenderState entity, float partialTicks, PoseStack.Pose basePose, VertexConsumer trailConsumer) {
        Vec3[] positions = entity.trailPositions;
        if (positions.length < 2) return;

        //当前插值位置
        Vec3 currentPos = entity.currentPos;
        Vec3 entityRenderPos = entity.entityRenderPos;

        int count = positions.length;

        Matrix4f matrix = basePose.pose();

        float time = (entity.tickCount + partialTicks) * 0.3f;

        for (int i = 0; i < count - 1; i++) {
            //进度 0.0（头部）到 1.0（尾部）
            float progress = (float) i / (count - 1);
            float nextProgress = (float) (i + 1) / (count - 1);

            //透明度从头到尾衰减
            float alpha = (1.0f - progress) * 0.6f;
            float nextAlpha = (1.0f - nextProgress) * 0.6f;

            //颜色：红线从红渐变到紫，蓝线从蓝渐变到紫
            float redR = Mth.lerp(progress, 1.0f, 0.6f);
            float redG = Mth.lerp(progress, 0.1f, 0.0f);
            float redB = Mth.lerp(progress, 0.2f, 0.8f);

            float blueR = Mth.lerp(progress, 0.2f, 0.6f);
            float blueG = Mth.lerp(progress, 0.1f, 0.0f);
            float blueB = Mth.lerp(progress, 1.0f, 0.8f);

            float nextRedR = Mth.lerp(nextProgress, 1.0f, 0.6f);
            float nextRedG = Mth.lerp(nextProgress, 0.1f, 0.0f);
            float nextRedB = Mth.lerp(nextProgress, 0.2f, 0.8f);

            float nextBlueR = Mth.lerp(nextProgress, 0.2f, 0.6f);
            float nextBlueG = Mth.lerp(nextProgress, 0.1f, 0.0f);
            float nextBlueB = Mth.lerp(nextProgress, 1.0f, 0.8f);

            //相对于实体渲染位置的偏移
            Vec3 pos = positions[i].subtract(entityRenderPos);
            Vec3 nextPos = positions[i + 1].subtract(entityRenderPos);

            //螺旋偏移：两条线相位差π
            float angle1 = time + progress * 6.0f;
            float angle2 = angle1 + (float) Math.PI;

            float width = TRAIL_WIDTH * (1.0f - progress * 0.5f);

            //红色螺旋线的两个顶点偏移
            float r1OffX = Mth.cos(angle1) * width;
            float r1OffY = Mth.sin(angle1) * width;
            float r1NextOffX = Mth.cos(time + nextProgress * 6.0f) * TRAIL_WIDTH * (1.0f - nextProgress * 0.5f);
            float r1NextOffY = Mth.sin(time + nextProgress * 6.0f) * TRAIL_WIDTH * (1.0f - nextProgress * 0.5f);

            //蓝色螺旋线
            float r2OffX = Mth.cos(angle2) * width;
            float r2OffY = Mth.sin(angle2) * width;
            float r2NextOffX = Mth.cos(time + nextProgress * 6.0f + (float) Math.PI) * TRAIL_WIDTH * (1.0f - nextProgress * 0.5f);
            float r2NextOffY = Mth.sin(time + nextProgress * 6.0f + (float) Math.PI) * TRAIL_WIDTH * (1.0f - nextProgress * 0.5f);

            //红色螺旋四边形
            trailConsumer.addVertex(matrix, (float) pos.x + r1OffX, (float) pos.y + r1OffY, (float) pos.z)
                    .setColor(redR, redG, redB, alpha).setLight(FULL_BRIGHT);
            trailConsumer.addVertex(matrix, (float) nextPos.x + r1NextOffX, (float) nextPos.y + r1NextOffY, (float) nextPos.z)
                    .setColor(nextRedR, nextRedG, nextRedB, nextAlpha).setLight(FULL_BRIGHT);
            trailConsumer.addVertex(matrix, (float) nextPos.x - r1NextOffX, (float) nextPos.y - r1NextOffY, (float) nextPos.z)
                    .setColor(nextRedR, nextRedG, nextRedB, nextAlpha).setLight(FULL_BRIGHT);
            trailConsumer.addVertex(matrix, (float) pos.x - r1OffX, (float) pos.y - r1OffY, (float) pos.z)
                    .setColor(redR, redG, redB, alpha).setLight(FULL_BRIGHT);

            //蓝色螺旋四边形
            trailConsumer.addVertex(matrix, (float) pos.x + r2OffX, (float) pos.y + r2OffY, (float) pos.z)
                    .setColor(blueR, blueG, blueB, alpha).setLight(FULL_BRIGHT);
            trailConsumer.addVertex(matrix, (float) nextPos.x + r2NextOffX, (float) nextPos.y + r2NextOffY, (float) nextPos.z)
                    .setColor(nextBlueR, nextBlueG, nextBlueB, nextAlpha).setLight(FULL_BRIGHT);
            trailConsumer.addVertex(matrix, (float) nextPos.x - r2NextOffX, (float) nextPos.y - r2NextOffY, (float) nextPos.z)
                    .setColor(nextBlueR, nextBlueG, nextBlueB, nextAlpha).setLight(FULL_BRIGHT);
            trailConsumer.addVertex(matrix, (float) pos.x - r2OffX, (float) pos.y - r2OffY, (float) pos.z)
                    .setColor(blueR, blueG, blueB, alpha).setLight(FULL_BRIGHT);
        }
    }

    public Identifier getTextureLocation(TrailRenderState entity) {
        return END_CRYSTAL_LOCATION;
    }
}
