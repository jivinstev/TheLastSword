package net.the_last_sword.entity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ServerLevelAccessor;
import net.neoforged.neoforge.common.ItemAbilities;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.entity.ai.GuardianSaberBlockGoal;
import org.jetbrains.annotations.Nullable;
import net.the_last_sword.ModHolders;

// 封印尖塔守卫 - 剑士变种
public class GuardianSaberEntity extends GuardianOfSealedSpireEntity {

    public static final int STATE_BLOCK = 4;

    private long shieldDisabledUntil;
    private boolean blockRequested;

    public GuardianSaberEntity(EntityType<? extends GuardianSaberEntity> type, Level world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new GuardianSaberBlockGoal(this));
    }

    @Override
    public String getSkillAnimationName(int attackState) {
        if (attackState == STATE_BLOCK) {
            return "block";
        }
        return super.getSkillAnimationName(attackState);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.30)
                .add(Attributes.MAX_HEALTH, 200)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.FOLLOW_RANGE, 32);
    }

    @Override
    public GuardianType getGuardianType() {
        return GuardianType.SABER;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor world, DifficultyInstance difficulty, EntitySpawnReason reason, @Nullable SpawnGroupData livingdata) {
        SpawnGroupData result = super.finalizeSpawn(world, difficulty, reason, livingdata);

        ItemStack sword = new ItemStack(Items.NETHERITE_SWORD);
        sword.enchant(ModHolders.enchantment(Enchantments.SHARPNESS), 5);
        sword.enchant(ModHolders.enchantment(Enchantments.SWEEPING_EDGE), 3);
        sword.enchant(ModHolders.enchantment(Enchantments.LOOTING), 3);
        sword.enchant(ModHolders.enchantment(Enchantments.KNOCKBACK), 2);
        sword.enchant(ModHolders.enchantment(Enchantments.UNBREAKING), 3);
        sword.enchant(ModHolders.enchantment(Enchantments.MENDING), 1);

        this.setItemSlot(EquipmentSlot.MAINHAND, sword);
        this.setDropChance(EquipmentSlot.MAINHAND, 2.0F);

        this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
        this.setDropChance(EquipmentSlot.OFFHAND, 0.0F);

        return result;
    }

    public boolean isShieldDisabled() {
        return level().getGameTime() < shieldDisabledUntil;
    }

    public boolean hasBlockRequest() {
        return blockRequested;
    }

    public void requestBlock() {
        blockRequested = true;
    }

    public void consumeBlockRequest() {
        blockRequested = false;
    }

    //成功受伤后请求格挡；由高优先级Goal在下一次AI调度时进入block
    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        boolean hurt = super.hurtServer(level, source, amount);
        if (hurt) {
            requestBlock();
        }
        return hurt;
    }

    //沿用原版玩家逻辑：支持斧及其他声明可破盾的物品
    //1.21.5: blockUsingShield/canDisableShield are gone; the weapon component's disable_blocking_for_seconds says whether the attacker breaks guard
    @Override
    protected void blockUsingItem(ServerLevel level, LivingEntity attacker) {
        super.blockUsingItem(level, attacker);
        if (attacker.getSecondsToDisableBlocking() > 0.0F) {
            shieldDisabledUntil = level().getGameTime()
                    + TheLastSwordConfiguration.getGuardianSaberShieldDisableTimeSafely();
            stopUsingItem();
            if (getAnimationState() == STATE_BLOCK) {
                setAnimationState(STATE_IDLE);
            }
            level().broadcastEntityEvent(this, (byte) 30);
        }
    }

    //LivingEntity默认不会损耗非玩家实体的盾牌，这里补齐原版玩家的耐久逻辑
    //1.21.5: hurtCurrentlyUsedShield is gone; the blocked damage comes out of applyItemBlocking
    @Override
    public float applyItemBlocking(ServerLevel level, DamageSource source, float amount) {
        float blocked = super.applyItemBlocking(level, source, amount);
        if (blocked > 0.0F) {
            hurtCurrentlyUsedShield(blocked);
        }
        return blocked;
    }

    private void hurtCurrentlyUsedShield(float damage) {
        ItemStack shield = getUseItem();
        if (!shield.has(net.minecraft.core.component.DataComponents.BLOCKS_ATTACKS) || damage < 3.0F) {
            return;
        }

        int durabilityDamage = 1 + Mth.floor(damage);
        InteractionHand usedHand = getUsedItemHand();
        EquipmentSlot usedSlot = usedHand == InteractionHand.MAIN_HAND
                ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
        shield.hurtAndBreak(durabilityDamage, this, usedSlot);

        if (shield.isEmpty()) {
            setItemSlot(usedSlot, ItemStack.EMPTY);
            stopUsingItem();
            playSound(SoundEvents.SHIELD_BREAK.value(), 0.8F, 0.8F + level().random.nextFloat() * 0.4F);
            if (getAnimationState() == STATE_BLOCK) {
                setAnimationState(STATE_IDLE);
            }
        }
    }
}
