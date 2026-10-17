package net.the_last_sword.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;

//覆世之翼模型 - Blockbench 导出并修改
//1.21.4 移除了 AgeableListModel：Model 直接渲染 root（其唯一子部件即翅膀），等同原 bodyParts
public class WingsThatCoverTheWorldModel<T extends LivingEntity> extends EntityModel<LivingEntityRenderState> {

    public static final ModelLayerLocation LAYER_LOCATION =
        new ModelLayerLocation(Identifier.fromNamespaceAndPath("the_last_sword", "wings_that_cover_the_world"), "main");

    private final ModelPart wingsThatCoverTheWorld;

    public WingsThatCoverTheWorldModel(ModelPart root) {
        super(root);
        this.wingsThatCoverTheWorld = root.getChild("wings_that_cover_the_world");
    }

    //创建模型层定义
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition wingsThatCoverTheWorld = partdefinition.addOrReplaceChild("wings_that_cover_the_world",
            CubeListBuilder.create(),
            PartPose.offset(-3.0F, 3.0F, 4.0F));

        //右翼
        PartDefinition cubeR1 = wingsThatCoverTheWorld.addOrReplaceChild("cube_r1",
            CubeListBuilder.create()
                .texOffs(0, 80)
                .mirror()
                .addBox(-5.0F, -11.0F, -1.0F, 26.0F, 25.0F, 1.0F, new CubeDeformation(0.0F))
                .mirror(false),
            PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, -0.6981F, 0.0F));

        //左翼
        PartDefinition cubeR2 = wingsThatCoverTheWorld.addOrReplaceChild("cube_r2",
            CubeListBuilder.create()
                .texOffs(0, 80)
                .addBox(-21.0F, -11.0F, -1.0F, 26.0F, 25.0F, 1.0F, new CubeDeformation(0.0F)),
            PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.6981F, 0.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    protected Iterable<ModelPart> headParts() {
        return ImmutableList.of();
    }

    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.wingsThatCoverTheWorld);
    }

    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //可以在这里添加翅膀动画效果
        //例如飞行时的挥动效果
    }
}
