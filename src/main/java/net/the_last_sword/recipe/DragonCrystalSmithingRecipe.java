package net.the_last_sword.recipe;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.the_last_sword.init.ModRecipes;
import net.the_last_sword.util.nbt.ItemLevelHelper;
import net.the_last_sword.util.TheLastSwordLogger;

public class DragonCrystalSmithingRecipe implements Recipe<RecipeInput> {

    private final ResourceLocation id;
    private final Ingredient template;
    private final Integer templateInputLevel;
    private final Ingredient input;
    private final int inputLevel;
    private final Ingredient addition;
    private final ItemStack output;
    private final int outputLevel;

    public DragonCrystalSmithingRecipe(ResourceLocation id, Ingredient template, Integer templateInputLevel,
                                       Ingredient input, int inputLevel, Ingredient addition,
                                       ItemStack output, int outputLevel) {
        this.id = id;
        this.template = template;
        this.templateInputLevel = templateInputLevel;
        this.input = input;
        this.inputLevel = inputLevel;
        this.addition = addition;
        this.output = output;
        this.outputLevel = outputLevel;
    }

    //1.21 配方改用 RecipeInput；保留 Container 入口供菜单/配置管理器调用
    private static RecipeInput toInput(Container container) {
        return new RecipeInput() {
            @Override
            public ItemStack getItem(int index) {
                return container.getItem(index);
            }

            @Override
            public int size() {
                return container.getContainerSize();
            }
        };
    }

    public boolean matches(Container container, Level level) {
        return matches(toInput(container), level);
    }

    public ItemStack assemble(Container container, HolderLookup.Provider registries) {
        return assemble(toInput(container), registries);
    }

    @Override
    public boolean matches(RecipeInput container, Level level) {
        if (container.size() < 3) {
            return false;
        }

        ItemStack templateStack = container.getItem(0);
        ItemStack baseStack = container.getItem(1);
        ItemStack additionStack = container.getItem(2);

        boolean templateMatch = template.test(templateStack);
        boolean inputMatch = input.test(baseStack);
        boolean additionMatch = addition.test(additionStack);

        TheLastSwordLogger.debug("Recipe {} - Template: {}, Input: {}, Addition: {}",
            id, templateMatch, inputMatch, additionMatch);

        if (!templateMatch || !inputMatch || !additionMatch) {
            return false;
        }

        if (templateInputLevel != null && ItemLevelHelper.getLevel(templateStack) != templateInputLevel) {
            return false;
        }

        //检查输入物品的等级是否匹配 inputLevel
        int baseItemLevel = ItemLevelHelper.getLevel(baseStack);
        boolean levelMatch = baseItemLevel == inputLevel;

        TheLastSwordLogger.debug("Recipe {} - Required level: {}, Item level: {}, Match: {}",
            id, inputLevel, baseItemLevel, levelMatch);

        return levelMatch;
    }

    @Override
    public ItemStack assemble(RecipeInput container, HolderLookup.Provider registries) {
        ItemStack outputStack = output.copy();
        ItemStack inputStack = container.getItem(1);

        //复制附魔
        EnchantmentHelper.setEnchantments(outputStack, EnchantmentHelper.getEnchantmentsForCrafting(inputStack));

        //复制纹饰（Trim）
        if (inputStack.has(DataComponents.TRIM)) {
            outputStack.set(DataComponents.TRIM, inputStack.get(DataComponents.TRIM));
        }

        //复制自定义名称
        if (inputStack.has(DataComponents.CUSTOM_NAME)) {
            outputStack.set(DataComponents.CUSTOM_NAME, inputStack.get(DataComponents.CUSTOM_NAME));
        }

        //设置输出物品的等级（所有属性都会根据等级动态计算）
        ItemLevelHelper.setLevel(outputStack, outputLevel);

        return outputStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output.copy();
    }

    /** The same recipe under {@code id}: the codecs carry no id (a recipe's id is its file name or the sync key). */
    public DragonCrystalSmithingRecipe withId(ResourceLocation id) {
        return new DragonCrystalSmithingRecipe(id, template, templateInputLevel, input, inputLevel, addition, output,
                outputLevel);
    }

    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.DRAGON_CRYSTAL_SMITHING_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.DRAGON_CRYSTAL_SMITHING_TYPE.get();
    }

    //为JEI和序列化器提供getter方法
    public Ingredient getTemplate() {
        return template;
    }

    public boolean hasTemplateInputLevel() {
        return templateInputLevel != null;
    }

    public int getTemplateInputLevel() {
        return templateInputLevel != null ? templateInputLevel : 0;
    }

    public Ingredient getInput() {
        return input;
    }

    public int getInputLevel() {
        return inputLevel;
    }

    public Ingredient getAddition() {
        return addition;
    }

    public int getOutputLevel() {
        return outputLevel;
    }
}
