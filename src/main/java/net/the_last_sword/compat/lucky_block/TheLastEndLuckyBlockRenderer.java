package net.the_last_sword.compat.lucky_block;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import software.bernie.geckolib.renderer.GeoBlockRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class TheLastEndLuckyBlockRenderer<R extends BlockEntityRenderState & GeoRenderState> extends GeoBlockRenderer<TheLastEndLuckyBlockEntity, R> {

    public TheLastEndLuckyBlockRenderer() {
        super(new TheLastEndLuckyBlockModel());
    }

    //使用Translucent+Cull：保留alpha混合实现Blockbench黑色透视效果，开启背面剔除避免重合面Z-fight
    @Override
    public RenderType getRenderType(R renderState, ResourceLocation texture) {
        //1.21.5: entityTranslucent has no culling here (its pipeline is withCull(false)); NeoForge's layered
        //translucent item type keeps 1.21.1's entityTranslucentCull state (pipeline ENTITY_TRANSLUCENT_CULL)
        return net.neoforged.neoforge.client.NeoForgeRenderTypes.getItemLayeredTranslucent(texture);
    }
}
