// OVERLAY-ONLY: shared fork-layer helper from mc-mod-version-upgrade templates/fork-layer/shared (not the authors' code).
// REQUIRES: curios
package net.the_last_sword.mv;

import java.util.Optional;
import java.util.function.Supplier;
import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.client.ICurioRenderer;

/**
 * Curios' {@code top.theillusivec4.curios.api.client.CuriosRendererRegistry} on 26.3: Curios 17 (26.3) deleted the
 * class (CATALOG §V105). In the 26.2 build both of its methods were one-line delegates to {@link ICurioRenderer}'s
 * statics, which 26.3 keeps unchanged ({@code register} adds the supplier to the map Curios loads its renderers from, so
 * the same registration at client setup still draws the curio); this is that delegate. The shared row points the
 * authors' files here; an overlay calls {@code ICurioRenderer.register} directly.
 */
public final class CuriosRendererRegistry {
    private CuriosRendererRegistry() {
    }

    public static void register(Item item, Supplier<ICurioRenderer> renderer) {
        ICurioRenderer.register(item, renderer);
    }

    public static Optional<ICurioRenderer> getRenderer(Item item) {
        return Optional.ofNullable(ICurioRenderer.getOrNull(item));
    }
}
