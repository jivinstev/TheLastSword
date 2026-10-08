package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.entity.DragonSwordProjectile;
import net.the_last_sword.entity.util.PriestGuardEffect;

import java.util.List;

public class PriestStaffItem extends Item {
    private final ItemAttributeModifiers defaultModifiers;

    public PriestStaffItem() {
        super(new Item.Properties().durability(200).rarity(Rarity.UNCOMMON));

        this.defaultModifiers = ItemAttributeModifiers.builder()
            .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", "priest_staff_attack_damage"),
                2.0,
                AttributeModifier.Operation.ADD_VALUE
            ), EquipmentSlotGroup.MAINHAND)
            .add(Attributes.ATTACK_SPEED, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", "priest_staff_attack_speed"),
                -2.4,
                AttributeModifier.Operation.ADD_VALUE
            ), EquipmentSlotGroup.MAINHAND)
            .build();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        return defaultModifiers;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return defaultModifiers;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }

        if (!level.isClientSide) {
            if (player.isShiftKeyDown()) {
                PriestGuardEffect.apply(
                    player,
                    TheLastSwordConfiguration.getPriestStaffGuardRadiusSafely(),
                    TheLastSwordConfiguration.getPriestStaffGuardShieldGainSafely()
                );
                consumeDurability(stack, player, hand, 2);
                player.getCooldowns().addCooldown(
                    this,
                    TheLastSwordConfiguration.getPriestStaffGuardCooldownSafely()
                );
            } else {
                float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)
                    * TheLastSwordConfiguration.getPriestStaffProjectileDamageMultiplierSafely());
                DragonSwordProjectile.shoot(
                    level, player, player.getRandom(), player.getUUID(), damage, stack
                );
                consumeDurability(stack, player, hand, 1);
            }
        }

        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.translatable(
            "item_tooltip.the_last_sword.priest_staff.projectile",
            formatValue(TheLastSwordConfiguration.getPriestStaffProjectileDamageMultiplierSafely())
        ));
        tooltip.add(Component.translatable(
            "item_tooltip.the_last_sword.priest_staff.guard",
            formatValue(TheLastSwordConfiguration.getPriestStaffGuardRadiusSafely()),
            formatValue(TheLastSwordConfiguration.getPriestStaffGuardShieldGainSafely()),
            formatValue(TheLastSwordConfiguration.getPriestStaffGuardCooldownSafely() / 20.0)
        ));
        tooltip.add(Component.translatable("item_tooltip_lore.the_last_sword.priest_staff")
            .withStyle(ChatFormatting.GRAY));
    }

    private static String formatValue(double value) {
        return value == Math.rint(value)
            ? Long.toString(Math.round(value))
            : String.format(java.util.Locale.ROOT, "%.2f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    private static void consumeDurability(ItemStack stack, Player player, InteractionHand hand, int amount) {
        stack.hurtAndBreak(amount, player,
            hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
    }
}
