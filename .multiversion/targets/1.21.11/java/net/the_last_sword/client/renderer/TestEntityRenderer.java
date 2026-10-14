package net.the_last_sword.client.renderer;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.test.TestEntity;

//测试实体渲染器
public class TestEntityRenderer extends HumanoidMobRenderer<TestEntity, HumanoidRenderState, HumanoidModel<HumanoidRenderState>> {

    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "textures/entities/test_entity.png");

    public TestEntityRenderer(EntityRendererProvider.Context context) {
        super(
            context,
            new HumanoidModel<>(context.bakeLayer(ModelLayers.PLAYER)),
            0.5f  // 阴影大小
        );

        //添加护甲层
        net.minecraft.client.renderer.entity.ArmorModelSet<HumanoidModel<HumanoidRenderState>> armorModels =
            net.minecraft.client.renderer.entity.ArmorModelSet.bake(ModelLayers.PLAYER_ARMOR, context.getModelSet(), HumanoidModel::new);
        this.addLayer(new HumanoidArmorLayer<HumanoidRenderState, HumanoidModel<HumanoidRenderState>, HumanoidModel<HumanoidRenderState>>(
            this,
            armorModels,
            context.getEquipmentRenderer()
        ));

    }

    @Override
    public HumanoidRenderState createRenderState() {
        return new HumanoidRenderState();
    }

    @Override
    public Identifier getTextureLocation(HumanoidRenderState state) {
        return TEXTURE;
    }
}
