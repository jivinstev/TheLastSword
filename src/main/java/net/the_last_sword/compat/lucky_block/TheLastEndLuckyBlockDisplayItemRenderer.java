package net.the_last_sword.compat.lucky_block;


import net.minecraft.client.renderer.rendertype.RenderType;

import net.minecraft.resources.Identifier;
import com.geckolib.renderer.GeoItemRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class TheLastEndLuckyBlockDisplayItemRenderer extends GeoItemRenderer<TheLastEndLuckyBlockDisplayItem> {

    public TheLastEndLuckyBlockDisplayItemRenderer() {
        super(new TheLastEndLuckyBlockDisplayModel());
    }

    //使用Translucent+Cull：保留alpha混合实现Blockbench黑色透视效果，开启背面剔除避免重合面Z-fight
    @Override
    public RenderType getRenderType(GeoRenderState renderState, Identifier texture) {
        //1.21.5: entityTranslucent has no culling here (its pipeline is withCull(false)); 26.3 removed NeoForge's
        //layered translucent item type, and vanilla's entityTranslucentCull is 1.21.1's type again (pipeline
        //ENTITY_TRANSLUCENT_CULL: translucent blend, back faces culled)
        return net.minecraft.client.renderer.rendertype.RenderTypes.entityTranslucentCull(getTextureLocation(renderState));
    }
}
