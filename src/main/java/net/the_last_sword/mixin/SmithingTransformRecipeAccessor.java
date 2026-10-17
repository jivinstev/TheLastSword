package net.the_last_sword.mixin;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SmithingTransformRecipe.class)
public interface SmithingTransformRecipeAccessor {

    @Accessor("template")
    java.util.Optional<Ingredient> theLastSword$getTemplate();

    @Accessor("base")
    java.util.Optional<Ingredient> theLastSword$getBase();

    @Accessor("addition")
    java.util.Optional<Ingredient> theLastSword$getAddition();
}
