package net.the_last_sword.compat.projecte;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.the_last_sword.init.ModAttributes;


public final class PrimalSourceArmorSupport {
    private PrimalSourceArmorSupport() {
    }

    public static Item create(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> new PrimalSourceHelmetItem();
            case CHESTPLATE -> new PrimalSourceChestplateItem();
            case LEGGINGS -> new PrimalSourceLeggingsItem();
            case BOOTS -> new PrimalSourceBootsItem();
            // 1.21 新增的 BODY（动物铠甲）没有对应部位。
            case BODY -> throw new IllegalArgumentException("no primal source armor for " + type);
        };
    }

    public static Item.Properties properties() {
        return new Item.Properties().fireResistant().rarity(Rarity.EPIC);
    }

    public static ItemAttributeModifiers addAttributes(ItemAttributeModifiers base, PrimalSourceArmorItem armor) {
        EquipmentSlot slot = armor.getPrimalArmorSlot();
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(slot);
        // 沿用各部位的固定标识，确保替换装备后属性正确移除。
        return base
            .withModifierAdded(ModAttributes.MAX_JUSTIFIED_DEFENCE, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", "primal_source_max_defence_" + slot.getName()),
                2.0, AttributeModifier.Operation.ADD_VALUE), group)
            .withModifierAdded(ModAttributes.JUSTIFIED_DEFENCE_RECOVERY_SPEED, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", "primal_source_defence_recovery_" + slot.getName()),
                0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE), group);
    }

    public static void tickFluidWalking(Player player, Level level, TagKey<Fluid> fluid) {
        BlockPos pos = new BlockPos(
            (int) Math.floor(player.getX()),
            (int) (player.getY() + 0.35), // 1.20.1 Player#getMyRidingOffset() == -0.35（1.21 已移除）
            (int) Math.floor(player.getZ()));
        // 液面行走的原入口限定了物品身份，需为新装备补齐相同行为。
        if (level.getFluidState(pos.below()).is(fluid) && level.isEmptyBlock(pos)) {
            if (!player.isShiftKeyDown()) {
                player.setDeltaMovement(player.getDeltaMovement().multiply(1, 0, 1));
                player.fallDistance = 0;
                player.setOnGround(true);
            }
        } else if (!level.isClientSide && fluid.equals(FluidTags.WATER) && player.isInWater()) {
            player.setAirSupply(player.getMaxAirSupply());
        }
    }
}
