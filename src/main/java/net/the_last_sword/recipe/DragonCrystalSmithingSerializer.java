package net.the_last_sword.recipe;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import java.util.Optional;

//26.1: RecipeSerializer is a final record (codec, streamCodec); ModRecipes builds it from MAP_CODEC and STREAM_CODEC,
//while this class keeps codec()/streamCodec() for the callers that hold an instance
public class DragonCrystalSmithingSerializer {

    // The "template" and "input" objects carry an optional "inputLevel" next to the
    // ingredient fields; Codec.pair decodes both halves from the same JSON object.
    // 1.21.2 dropped the {"item": X} / {"tag": X} ingredient object (an ingredient is now "X", "#X" or a list), but
    // these objects carry "inputLevel" beside the ingredient, so they cannot become bare strings -- and the built-in
    // files and players' config copies are all written in the 1.21.1 object form. Read that form (and NeoForge's
    // typed ingredient objects), write the object form back.
    private static final MapCodec<Ingredient> OBJECT_INGREDIENT = Codec.mapEither(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item"),
            TagKey.codec(Registries.ITEM).fieldOf("tag")
    ).xmap(
            either -> either.map(item -> Ingredient.of(item.value()),
                    tag -> Ingredient.of(BuiltInRegistries.ITEM.getOrThrow(tag))),
            ingredient -> Either.left(ingredient.items().findFirst().orElseThrow()));

    private static final Codec<Ingredient> INGREDIENT = Codec.withAlternative(OBJECT_INGREDIENT.codec(), Ingredient.CODEC);

    private static final Codec<Pair<Ingredient, Optional<Integer>>> TEMPLATE_CODEC =
            Codec.pair(INGREDIENT, Codec.INT.optionalFieldOf("inputLevel").codec());

    private static final Codec<Pair<Ingredient, Integer>> INPUT_CODEC =
            Codec.pair(INGREDIENT, Codec.INT.optionalFieldOf("inputLevel", 0).codec());

    // Recipes written for 1.20.1 (players' own files in config/the_last_sword) name the item "item";
    // the ItemStack codec calls it "id". Read both, write "id".
    private static final Codec<ItemStack> LEGACY_STACK_CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(ItemStack::typeHolder),
            Codec.INT.optionalFieldOf("count", 1).forGetter(ItemStack::getCount)
    ).apply(instance, ItemStack::new));

    private static final Codec<Pair<ItemStack, Integer>> OUTPUT_CODEC =
            Codec.pair(Codec.withAlternative(ItemStack.CODEC, LEGACY_STACK_CODEC),
                    Codec.INT.optionalFieldOf("outputLevel", 0).codec());

    public static final MapCodec<DragonCrystalSmithingRecipe> MAP_CODEC =RecordCodecBuilder.mapCodec(instance -> instance.group(
            TEMPLATE_CODEC.fieldOf("template").forGetter(r -> Pair.of(r.getTemplate(),
                    r.hasTemplateInputLevel() ? Optional.of(r.getTemplateInputLevel()) : Optional.<Integer>empty())),
            INPUT_CODEC.fieldOf("input").forGetter(r -> Pair.of(r.getInput(), r.getInputLevel())),
            INGREDIENT.fieldOf("addition").forGetter(DragonCrystalSmithingRecipe::getAddition),
            OUTPUT_CODEC.fieldOf("output").forGetter(r -> Pair.of(r.getResultItem(null), r.getOutputLevel()))
    ).apply(instance, (template, input, addition, output) -> new DragonCrystalSmithingRecipe(null,
            template.getFirst(), template.getSecond().orElse(null),
            input.getFirst(), input.getSecond(),
            addition,
            output.getFirst(), output.getSecond())));

    public static final StreamCodec<RegistryFriendlyByteBuf, DragonCrystalSmithingRecipe> STREAM_CODEC = StreamCodec.of(
            (buffer, recipe) -> {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getTemplate());
                buffer.writeBoolean(recipe.hasTemplateInputLevel());
                if (recipe.hasTemplateInputLevel()) {
                    buffer.writeInt(recipe.getTemplateInputLevel());
                }
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getInput());
                buffer.writeInt(recipe.getInputLevel());
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.getAddition());
                ItemStack.STREAM_CODEC.encode(buffer, recipe.getResultItem(null));
                buffer.writeInt(recipe.getOutputLevel());
            },
            buffer -> {
                Ingredient template = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                Integer templateInputLevel = buffer.readBoolean() ? buffer.readInt() : null;
                Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                int inputLevel = buffer.readInt();
                Ingredient addition = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);
                ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
                int outputLevel = buffer.readInt();

                return new DragonCrystalSmithingRecipe(null, template, templateInputLevel,
                        input, inputLevel, addition, output, outputLevel);
            });

    public MapCodec<DragonCrystalSmithingRecipe> codec() {
        return MAP_CODEC;
    }

    public StreamCodec<RegistryFriendlyByteBuf, DragonCrystalSmithingRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
