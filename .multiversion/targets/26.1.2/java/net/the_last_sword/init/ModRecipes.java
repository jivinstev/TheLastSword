package net.the_last_sword.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingBookCategory;
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
        SERIALIZERS.register("dragon_crystal_smithing", () -> new RecipeSerializer<DragonCrystalSmithingRecipe>(DragonCrystalSmithingSerializer.MAP_CODEC, DragonCrystalSmithingSerializer.STREAM_CODEC));

    public static final Supplier<RecipeType<DragonCrystalSmithingRecipe>> DRAGON_CRYSTAL_SMITHING_TYPE =
        TYPES.register("dragon_crystal_smithing", () -> new RecipeType<DragonCrystalSmithingRecipe>() {
            @Override
            public String toString() {
                return "dragon_crystal_smithing";
            }
        });

    //终焉幸运方块幸运值合成(动态 CustomRecipe, 复刻 LuckyBlock 原版)
    public static final Supplier<RecipeSerializer<LuckModifierCraftingRecipe>> LUCK_MODIFIER_CRAFTING_SERIALIZER =
        SERIALIZERS.register("luck_modifier_crafting",
            () -> new RecipeSerializer<LuckModifierCraftingRecipe>(
                MapCodec.unit(() -> new LuckModifierCraftingRecipe(CraftingBookCategory.MISC)),
                StreamCodec.of((buf, recipe) -> { }, buf -> new LuckModifierCraftingRecipe(CraftingBookCategory.MISC))));

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
