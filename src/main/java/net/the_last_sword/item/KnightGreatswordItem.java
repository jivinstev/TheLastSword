package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.entity.util.GroundRuptureEffect;
import net.the_last_sword.util.EntityUtil;

import java.util.List;
import java.util.UUID;

public class KnightGreatswordItem extends SwordItem {
    private static final UUID ENTITY_REACH_MODIFIER_UUID =
        UUID.fromString("837d8ef2-24d0-4cc0-91d8-20a5c158d26c");
    private static final UUID BLOCK_REACH_MODIFIER_UUID =
        UUID.fromString("20ebf6dd-f43e-4f8a-b686-0a58e185d5b6");

    private static final Tier KNIGHT_GREATSWORD_TIER = new Tier() {
        @Override
        public int getUses() {
            return 512;
        }

        @Override
        public float getSpeed() {
            return 4.0f;
        }

        @Override
        public float getAttackDamageBonus() {
            return 0.0f;
        }

        @Override
        public TagKey<Block> getIncorrectBlocksForDrops() {
            return BlockTags.INCORRECT_FOR_IRON_TOOL;
        }

        @Override
        public int getEnchantmentValue() {
            return 14;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }
    };

    private static ItemAttributeModifiers knightGreatswordAttributes() {
        return SwordItem.createAttributes(KNIGHT_GREATSWORD_TIER, 11, -3.0f)
            .withModifierAdded(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", "knight_greatsword_entity_reach"),
                1.0,
                AttributeModifier.Operation.ADD_VALUE
            ), EquipmentSlotGroup.MAINHAND)
            .withModifierAdded(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(
                ResourceLocation.fromNamespaceAndPath("the_last_sword", "knight_greatsword_block_reach"),
                1.0,
                AttributeModifier.Operation.ADD_VALUE
            ), EquipmentSlotGroup.MAINHAND);
    }

    public KnightGreatswordItem() {
        // 玩家自带 1 点攻击伤害和 4 点攻击速度，因此这里的修正值会得到最终 12 / 1.0 的面板属性。
        super(KNIGHT_GREATSWORD_TIER, new Item.Properties().rarity(Rarity.COMMON).attributes(knightGreatswordAttributes()));
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();
        if (player == null || !player.isShiftKeyDown() || context.getClickedFace() != Direction.UP) {
            return InteractionResult.PASS;
        }

        Level level = context.getLevel();
        if (level instanceof ServerLevel serverLevel) {
            Vec3 impactCenter = context.getClickLocation();
            serverLevel.playSound(null, context.getClickedPos(), SoundEvents.GENERIC_EXPLODE.value(),
                SoundSource.PLAYERS, 1.0F, 1.0F);
            GroundRuptureEffect.spawn(serverLevel, impactCenter, player.getRandom());
            damageNearbyTargets(serverLevel, player, impactCenter);
            EquipmentSlot slot = context.getHand() == InteractionHand.OFF_HAND ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
            context.getItemInHand().hurtAndBreak(1, player, slot);
        }

        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private void damageNearbyTargets(ServerLevel level, Player player, Vec3 center) {
        double halfSize = 1.5D;
        AABB damageArea = new AABB(
            center.x - halfSize, center.y - halfSize, center.z - halfSize,
            center.x + halfSize, center.y + halfSize, center.z + halfSize
        );
        float damage = (float) (player.getAttributeValue(Attributes.ATTACK_DAMAGE)
            * TheLastSwordConfiguration.getKnightGreatswordGroundSlamDamageMultiplierSafely());
        if (damage <= 0.0F) {
            return;
        }

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, damageArea,
            target -> target != player && target.isAlive() && EntityUtil.canAttack(player, target));
        for (LivingEntity target : targets) {
            target.invulnerableTime = 0;
            target.hurt(player.damageSources().playerAttack(player), damage);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        tooltip.add(Component.translatable(
            "item_tooltip.the_last_sword.knight_greatsword.ground_slam",
            String.format("%.1f", TheLastSwordConfiguration
                .getKnightGreatswordGroundSlamDamageMultiplierSafely())
        ));

        tooltip.add(Component.translatable("item_tooltip_lore.the_last_sword.knight_greatsword")
            .withStyle(ChatFormatting.GRAY));
    }
}
