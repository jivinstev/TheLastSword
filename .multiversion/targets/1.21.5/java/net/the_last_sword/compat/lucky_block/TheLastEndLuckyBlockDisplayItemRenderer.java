package net.the_last_sword.compat.lucky_block;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class TheLastEndLuckyBlockDisplayItemRenderer extends GeoItemRenderer<TheLastEndLuckyBlockDisplayItem> {

    public TheLastEndLuckyBlockDisplayItemRenderer() {
        super(new TheLastEndLuckyBlockDisplayModel());
    }

    //使用Translucent+Cull：保留alpha混合实现Blockbench黑色透视效果，开启背面剔除避免重合面Z-fight
    @Override
    public RenderType getRenderType(GeoRenderState renderState, ResourceLocation texture) {
        //1.21.5: entityTranslucent has no culling here (its pipeline is withCull(false)); NeoForge's layered
        //translucent item type keeps 1.21.1's entityTranslucentCull state (pipeline ENTITY_TRANSLUCENT_CULL)
        return net.neoforged.neoforge.client.NeoForgeRenderTypes.getItemLayeredTranslucent(getTextureLocation(renderState));
    }
}
