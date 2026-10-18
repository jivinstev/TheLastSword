package net.the_last_sword.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.SpecialModelWrapper;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.client.renderer.LightningSpearItemRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// OVERLAY-ONLY (1.21.8, mixin_add_only): 1.21.6 draws GUI items into an item atlas once and reuses that picture until
// the stack changes, unless its render state is marked animated. The lightning spear's special renderer animates by
// time (LightningSpearItemRenderer.animationAge), as 1.21.1's BlockEntityWithoutLevelRenderer did every frame, so
// mark its state animated and the GUI redraws it each frame instead of showing the first frame frozen.
// 1.21.9+: update's holder is an ItemOwner (was LivingEntity); the method is otherwise the same.
@Mixin(SpecialModelWrapper.class)
public class SpecialModelWrapperMixin<T> {
    @Shadow
    @Final
    private SpecialModelRenderer<T> specialRenderer;

    @Inject(method = "update", at = @At("TAIL"))
    private void the_last_sword$animateLightningSpear(ItemStackRenderState renderState, ItemStack stack,
            ItemModelResolver resolver, ItemDisplayContext displayContext, ClientLevel level, ItemOwner owner,
            int seed, CallbackInfo ci) {
        if (this.specialRenderer instanceof LightningSpearItemRenderer) {
            renderState.setAnimated();
        }
    }
}
