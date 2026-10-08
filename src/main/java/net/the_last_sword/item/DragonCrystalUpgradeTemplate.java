package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SmithingTemplateItem;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

@EventBusSubscriber(modid = "the_last_sword", bus = EventBusSubscriber.Bus.MOD)
public class DragonCrystalUpgradeTemplate extends SmithingTemplateItem implements IDragonSmithingTemplate {

    //稀有度与防火通过默认数据组件提供
    @SubscribeEvent
    public static void onModifyDefaultComponents(ModifyDefaultComponentsEvent event) {
        event.modifyMatching(item -> item instanceof DragonCrystalUpgradeTemplate, builder -> {
            builder.set(DataComponents.RARITY, Rarity.UNCOMMON);
            builder.set(DataComponents.FIRE_RESISTANT, Unit.INSTANCE);
        });
    }

    private static final Component DRAGON_CRYSTAL_UPGRADE =
        Component.translatable("item.the_last_sword.dragon_crystal_upgrade_template").withStyle(ChatFormatting.GRAY);
    private static final Component DRAGON_CRYSTAL_UPGRADE_APPLIES_TO =
        Component.translatable("item.the_last_sword.dragon_crystal_upgrade_template.applies_to").withStyle(ChatFormatting.BLUE);
    private static final Component DRAGON_CRYSTAL_UPGRADE_INGREDIENTS =
        Component.translatable("item.the_last_sword.dragon_crystal_upgrade_template.ingredients").withStyle(ChatFormatting.BLUE);
    private static final Component DRAGON_CRYSTAL_UPGRADE_BASE_SLOT_DESCRIPTION =
        Component.translatable("item.the_last_sword.dragon_crystal_upgrade_template.base_slot_description");
    private static final Component DRAGON_CRYSTAL_UPGRADE_ADDITIONS_SLOT_DESCRIPTION =
        Component.translatable("item.the_last_sword.dragon_crystal_upgrade_template.additions_slot_description");

    private static final ResourceLocation EMPTY_SLOT_SWORD = ResourceLocation.parse("item/empty_slot_sword");
    private static final ResourceLocation EMPTY_SLOT_HELMET = ResourceLocation.parse("item/empty_armor_slot_helmet");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.parse("item/empty_armor_slot_chestplate");
    private static final ResourceLocation EMPTY_SLOT_LEGGINGS = ResourceLocation.parse("item/empty_armor_slot_leggings");
    private static final ResourceLocation EMPTY_SLOT_BOOTS = ResourceLocation.parse("item/empty_armor_slot_boots");
    private static final ResourceLocation EMPTY_SLOT_INGOT = ResourceLocation.parse("item/empty_slot_ingot");

    public DragonCrystalUpgradeTemplate() {
        super(
            DRAGON_CRYSTAL_UPGRADE_APPLIES_TO,
            DRAGON_CRYSTAL_UPGRADE_INGREDIENTS,
            DRAGON_CRYSTAL_UPGRADE,
            DRAGON_CRYSTAL_UPGRADE_BASE_SLOT_DESCRIPTION,
            DRAGON_CRYSTAL_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
            createDragonCrystalUpgradeIconList(),
            createDragonCrystalUpgradeMaterialList()
        );
    }

    //创建基础槽空图标列表
    private static List<ResourceLocation> createDragonCrystalUpgradeIconList() {
        return List.of(
            EMPTY_SLOT_SWORD,
            EMPTY_SLOT_HELMET,
            EMPTY_SLOT_CHESTPLATE,
            EMPTY_SLOT_LEGGINGS,
            EMPTY_SLOT_BOOTS
        );
    }

    //创建附加槽空图标列表
    private static List<ResourceLocation> createDragonCrystalUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_INGOT);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("item_tooltip_lore.the_last_sword.dragon_crystal_upgrade_template").withStyle(ChatFormatting.GRAY));
    }

}
