package net.the_last_sword.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.compat.lucky_block.LuckModifierCraftingRecipe;
import net.the_last_sword.recipe.DragonCrystalSmithingRecipe;
import net.the_last_sword.recipe.DragonCrystalSmithingSerializer;

public class ModRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
        DeferredRegister.create(Registries.RECIPE_SERIALIZER, TheLastSwordMod.MOD_ID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
        DeferredRegister.create(Registries.RECIPE_TYPE, TheLastSwordMod.MOD_ID);

    public static final Supplier<RecipeSerializer<DragonCrystalSmithingRecipe>> DRAGON_CRYSTAL_SMITHING_SERIALIZER =
        SERIALIZERS.register("dragon_crystal_smithing", DragonCrystalSmithingSerializer::new);

    public static final Supplier<RecipeType<DragonCrystalSmithingRecipe>> DRAGON_CRYSTAL_SMITHING_TYPE =
        TYPES.register("dragon_crystal_smithing", () -> new RecipeType<DragonCrystalSmithingRecipe>() {
            @Override
            public String toString() {
                return "dragon_crystal_smithing";
            }
        });

    //终焉幸运方块幸运值合成(动态 CustomRecipe, 复刻 LuckyBlock 原版)
    public static final Supplier<SimpleCraftingRecipeSerializer<LuckModifierCraftingRecipe>> LUCK_MODIFIER_CRAFTING_SERIALIZER =
        SERIALIZERS.register("luck_modifier_crafting",
            () -> new SimpleCraftingRecipeSerializer<>(category -> new LuckModifierCraftingRecipe(category)));

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
