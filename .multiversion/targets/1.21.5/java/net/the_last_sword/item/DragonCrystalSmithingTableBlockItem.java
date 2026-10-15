package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.the_last_sword.init.ModBlocks;

import java.util.function.Consumer;

public class DragonCrystalSmithingTableBlockItem extends BlockItem {

    public DragonCrystalSmithingTableBlockItem() {
        super(ModBlocks.DRAGON_CRYSTAL_SMITHING_TABLE.get(),
            net.the_last_sword.mv.RegIds.stamp(new Properties())
                .rarity(Rarity.UNCOMMON)
                .fireResistant()
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, display, tooltip, flag);
        tooltip.accept(Component.translatable("item_tooltip_lore.the_last_sword.dragon_crystal_smithing_table")
            .withStyle(ChatFormatting.GRAY));
    }
}
