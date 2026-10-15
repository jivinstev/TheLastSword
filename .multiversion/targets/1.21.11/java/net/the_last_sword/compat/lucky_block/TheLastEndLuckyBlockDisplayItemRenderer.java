package net.the_last_sword.compat.lucky_block;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;

import net.minecraft.resources.Identifier;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class TheLastEndLuckyBlockDisplayItemRenderer extends GeoItemRenderer<TheLastEndLuckyBlockDisplayItem> {

    public TheLastEndLuckyBlockDisplayItemRenderer() {
        super(new TheLastEndLuckyBlockDisplayModel());
    }

    //使用Translucent+Cull：保留alpha混合实现Blockbench黑色透视效果，开启背面剔除避免重合面Z-fight
    @Override
    public RenderType getRenderType(GeoRenderState renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(getTextureLocation(renderState));
    }
}
