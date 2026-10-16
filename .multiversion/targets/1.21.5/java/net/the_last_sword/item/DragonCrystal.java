package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import java.util.function.Consumer;

public class DragonCrystal extends Item {

    public DragonCrystal() {
        super(net.the_last_sword.mv.RegIds.stamp(new Item.Properties())
            .stacksTo(64)
            .fireResistant()
            .rarity(Rarity.UNCOMMON)
            //1.21.5: TrimMaterial no longer names its ingredient; the item carries provides_trim_material instead
            .trimMaterial(net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.TRIM_MATERIAL,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_crystal")))
        );
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("item_tooltip_lore.the_last_sword.dragon_crystal")
            .withStyle(ChatFormatting.GRAY));
    }
}
