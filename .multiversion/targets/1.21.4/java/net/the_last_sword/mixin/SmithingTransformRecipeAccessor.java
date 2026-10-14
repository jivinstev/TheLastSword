package net.the_last_sword.mixin;

import java.util.Optional;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmithingTransformRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

// 1.21.2+: the three smithing slots are Optional<Ingredient> (an empty slot is Optional.empty()).
@Mixin(SmithingTransformRecipe.class)
public interface SmithingTransformRecipeAccessor {

    @Accessor("template")
    Optional<Ingredient> theLastSword$getTemplate();

    @Accessor("base")
    Optional<Ingredient> theLastSword$getBase();

    @Accessor("addition")
    Optional<Ingredient> theLastSword$getAddition();
}
