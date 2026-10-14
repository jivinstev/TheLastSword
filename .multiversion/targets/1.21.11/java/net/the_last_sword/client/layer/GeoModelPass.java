package net.the_last_sword.client.layer;

import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.RenderPassInfo;

/**
 * GeckoLib 5.4.5 removed GeoRenderer.buildRenderTask (5.3's "draw the model again with this render type, light, overlay
 * and colour"); GeoRenderer.submitRenderTasks re-draws it with the pass's own light, overlay and colour only. This is
 * that same submission (GeckoLib's own lambda: the captured pose, then BakedGeoModel.render inside renderPosed, where the
 * bones' frame snapshots are live) with the three values the caller chooses.
 */
final class GeoModelPass {
    private GeoModelPass() {
    }

    static <R extends GeoRenderState> void submit(RenderPassInfo<R> info, OrderedSubmitNodeCollector collector,
                                                  RenderType renderType, int packedLight, int packedOverlay, int renderColor) {
        collector.submitCustomGeometry(info.poseStack(), renderType, (pose, vertices) -> {
            info.poseStack().pushPose();
            info.poseStack().last().set(pose);
            info.renderPosed(() -> info.model().render(info, vertices, packedLight, packedOverlay, renderColor));
            info.poseStack().popPose();
        });
    }
}
