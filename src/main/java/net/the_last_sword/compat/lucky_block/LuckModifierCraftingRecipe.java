package net.the_last_sword.compat.lucky_block;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import net.the_last_sword.init.ModItems;
import net.the_last_sword.ItemNbt;
import net.the_last_sword.init.ModRecipes;

import java.util.Map;

//复刻 LuckyBlock 原版 LuckModifierCraftingRecipe: 终焉幸运方块 + 材料 → luck 累加 clamp 到 [-100, 100]
public class LuckModifierCraftingRecipe extends CustomRecipe {

    private static final String TAG_LUCK = "Luck";
    private static final int LUCK_MIN = -100;
    private static final int LUCK_MAX = 100;

    //原版材料表: 珠宝类 + 幸运食物 + 坏运物品
    private static final Map<Item, Integer> VANILLA_LUCK_MODIFIERS = ImmutableMap.<Item, Integer>builder()
        .put(Items.DIAMOND, 12)
        .put(Items.DIAMOND_BLOCK, 100)
        .put(Items.EMERALD, 8)
        .put(Items.EMERALD_BLOCK, 80)
        .put(Items.GOLD_INGOT, 6)
        .put(Items.GOLD_BLOCK, 60)
        .put(Items.IRON_INGOT, 3)
        .put(Items.IRON_BLOCK, 30)
        .put(Items.GOLDEN_CARROT, 30)
        .put(Items.GOLDEN_APPLE, 40)
        .put(Items.ENCHANTED_GOLDEN_APPLE, 100)
        .put(Items.NETHER_STAR, 100)
        .put(Items.ROTTEN_FLESH, -5)
        .put(Items.SPIDER_EYE, -10)
        .put(Items.FERMENTED_SPIDER_EYE, -20)
        .put(Items.POISONOUS_POTATO, -10)
        .put(Items.PUFFERFISH, -20)
        .build();

    public LuckModifierCraftingRecipe(CraftingBookCategory category) {
        super(category);
    }

    //模组专属材料需运行时引用(DeferredRegister 延迟加载), 不放 static map
    private static int getLuckModifier(Item item) {
        if (item == ModItems.DRAGON_CRYSTAL.get()) return 12;
        Integer v = VANILLA_LUCK_MODIFIERS.get(item);
        return v != null ? v : 0;
    }

    private static boolean isLuckModifier(Item item) {
        return item == ModItems.DRAGON_CRYSTAL.get() || VANILLA_LUCK_MODIFIERS.containsKey(item);
    }

    private static boolean isLuckyBlock(ItemStack stack) {
        if (ModItems.THE_LAST_END_LUCKY_BLOCK == null) return false;
        return stack.getItem() == ModItems.THE_LAST_END_LUCKY_BLOCK.get();
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int luckyBlockCount = 0;
        boolean hasModifier = false;
        for (int i = 0; i < input.size(); i++) {
            ItemStack s = input.getItem(i);
            if (s.isEmpty()) continue;
            if (isLuckyBlock(s)) {
                if (++luckyBlockCount > 1) return false;
            } else if (isLuckModifier(s.getItem())) {
                hasModifier = true;
            } else {
                return false;
            }
        }
        return luckyBlockCount == 1 && hasModifier;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        ItemStack luckyStack = ItemStack.EMPTY;
        int sum = 0;
        for (int i = 0; i < input.size(); i++) {
            ItemStack s = input.getItem(i);
            if (s.isEmpty()) continue;
            if (isLuckyBlock(s)) {
                luckyStack = s;
            } else {
                sum += getLuckModifier(s.getItem());
            }
        }
        if (luckyStack.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = luckyStack.copyWithCount(1);
        int oldLuck = ItemNbt.hasTag(result) && ItemNbt.getTag(result).contains(TAG_LUCK) ? ItemNbt.getTag(result).getInt(TAG_LUCK) : 0;
        int newLuck = Math.max(LUCK_MIN, Math.min(LUCK_MAX, oldLuck + sum));
        ItemNbt.update(result, t -> t.putInt(TAG_LUCK, newLuck));
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.LUCK_MODIFIER_CRAFTING_SERIALIZER.get();
    }
}
