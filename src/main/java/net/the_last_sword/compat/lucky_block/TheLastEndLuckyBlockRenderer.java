package net.the_last_sword.compat.lucky_block;

import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import com.geckolib.renderer.GeoBlockRenderer;
import com.geckolib.renderer.base.GeoRenderState;

public class TheLastEndLuckyBlockRenderer<R extends BlockEntityRenderState & GeoRenderState> extends GeoBlockRenderer<TheLastEndLuckyBlockEntity, R> {

    public TheLastEndLuckyBlockRenderer(BlockEntityRendererProvider.Context context) {
        super(context, new TheLastEndLuckyBlockModel());
    }

    //使用Translucent+Cull：保留alpha混合实现Blockbench黑色透视效果，开启背面剔除避免重合面Z-fight
    @Override
    public RenderType getRenderType(R renderState, Identifier texture) {
        return RenderTypes.entityTranslucent(texture);
    }
}
