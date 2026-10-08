package net.the_last_sword.item;

import net.minecraft.world.item.component.ItemAttributeModifiers;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.LightningSpearClientSettings;
import net.the_last_sword.client.LightningSpearClientExtensions;
import net.the_last_sword.configuration.LightningSpearSettings;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.entity.LightningSpearProjectile;
import org.jetbrains.annotations.NotNull;

public class LightningSpearItem extends Item {
    private static final float SHOOT_POWER = 2.5F;
    private static final double ATTACK_DAMAGE_MODIFIER = 19.0D;
    private static final double ATTACK_SPEED_MODIFIER = -3.0D;

    public LightningSpearItem(Properties properties) {
        super(properties.attributes(buildAttributeModifiers()));
    }

    private static ItemAttributeModifiers buildAttributeModifiers() {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("the_last_sword", "weapon_modifier"), ATTACK_DAMAGE_MODIFIER,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath("the_last_sword", "weapon_modifier"), ATTACK_SPEED_MODIFIER,
                        AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new LightningSpearClientExtensions());
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return 72000;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player,
                                                           @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(this)) {
            return InteractionResultHolder.fail(stack);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity livingEntity,
                             int timeLeft) {
        if (level.isClientSide || !(livingEntity instanceof Player player)
                || player.getCooldowns().isOnCooldown(this)) {
            return;
        }

        int useTime = getUseDuration(stack, livingEntity) - timeLeft;
        LightningSpearSettings settings = TheLastSwordConfiguration.getLightningSpearSettings();
        if (useTime < settings.chargeTicks()) {
            return;
        }

        LightningSpearProjectile projectile = new LightningSpearProjectile(level, player);
        projectile.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, SHOOT_POWER, 1.0F);
        if (!level.addFreshEntity(projectile)) {
            return;
        }
        player.getCooldowns().addCooldown(this, settings.cooldownTicks());
        level.playSound(null, projectile, SoundEvents.TRIDENT_THROW.value(), SoundSource.PLAYERS, 1.0F, 1.0F);
        player.awardStat(Stats.ITEM_USED.get(this));
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        LightningSpearSettings settings = LightningSpearClientSettings.get();
        Component slowLevel = settings.slowLevel() <= 10
                ? Component.translatable("enchantment.level." + settings.slowLevel())
                : Component.literal(Integer.toString(settings.slowLevel()));
        tooltip.add(Component.translatable("item_tooltip.the_last_sword.lightning_spear.skill",
                formatValue(settings.chargeTicks() / 20.0), formatValue(settings.range()),
                formatValue(settings.burstSize()), formatValue(settings.damage()), slowLevel,
                formatValue(settings.slowTicks() / 20.0), formatValue(settings.cooldownTicks() / 20.0)));
        tooltip.add(Component.translatable("item_tooltip_lore.the_last_sword.lightning_spear")
                .withStyle(ChatFormatting.GRAY));
    }

    private static String formatValue(double value) {
        return value == Math.rint(value) ? Long.toString(Math.round(value))
                : String.format(Locale.ROOT, "%.2f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

}
