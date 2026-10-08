package net.the_last_sword.compat.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.UUID;

//龙水晶王冠 - head槽位
public class DragonCrystalCrown extends Item implements ICurioItem {

    private static final UUID CROWN_UUID = UUID.fromString("b2c3d4e5-f6a7-5b6c-9d8e-0f1a2b3c4d5e");

    public DragonCrystalCrown() {
        super(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();

        //最大生命值 +200
        modifiers.put(Attributes.MAX_HEALTH,
            new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_crystal_crown_health"), 200.0,
                AttributeModifier.Operation.ADD_VALUE));

        //攻击力 +20
        modifiers.put(Attributes.ATTACK_DAMAGE,
            new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_crystal_crown_attack"), 20.0,
                AttributeModifier.Operation.ADD_VALUE));

        //盔甲值 +20
        modifiers.put(Attributes.ARMOR,
            new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_crystal_crown_armor"), 20.0,
                AttributeModifier.Operation.ADD_VALUE));

        //盔甲韧性 +20
        modifiers.put(Attributes.ARMOR_TOUGHNESS,
            new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_crystal_crown_toughness"), 20.0,
                AttributeModifier.Operation.ADD_VALUE));

        return modifiers;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        //根据虚空转换开关显示不同提示
        String key = TheLastSwordConfiguration.getCuriosDragonCrystalCrownVoidConversionEnabledSafely()
            ? "item_tooltip.the_last_sword.dragon_crystal_crown.enabled"
            : "item_tooltip.the_last_sword.dragon_crystal_crown.disabled";
        tooltip.add(Component.translatable(key));
        tooltip.add(Component.translatable("item_tooltip_lore.the_last_sword.dragon_crystal_crown")
            .withStyle(ChatFormatting.GRAY));
    }
}
