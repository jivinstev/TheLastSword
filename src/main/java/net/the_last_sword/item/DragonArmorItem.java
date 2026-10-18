package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.the_last_sword.client.renderer.DragonArmorRenderer;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.init.ModItems;
import net.the_last_sword.util.nbt.ItemEnergyStorage;
import net.the_last_sword.util.nbt.ItemLevelHelper;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.constant.dataticket.DataTicket;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

/**
 * 龙之盔甲
 */
public abstract class DragonArmorItem extends TheLastEndArmorItem implements GeoItem {

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public String animationprocedure = "empty";

    //能量基础值（6级龙套的基础能量）
    private static final int BASE_ENERGY = 1048576; // 1M FE

    //耐久单位 134217727 × 各部位基数(最大16) 仍在 int 范围内，近似原来的 Integer.MAX_VALUE
    private static final ArmorMaterial MATERIAL = new ArmorMaterial(
            134217727,
            // 防御值：靴子8, 护腿12, 胸甲12, 头盔8
            Map.of(ArmorType.BOOTS, 8, ArmorType.LEGGINGS, 12, ArmorType.CHESTPLATE, 12, ArmorType.HELMET, 8),
            32,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            10f,
            5f,
            TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("the_last_sword", "repairs_dragon_armor")),
            ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_armor")));

    public abstract EquipmentSlot getEquipmentSlot(ItemStack stack);

    private static List<ItemStack> armorStacks(LivingEntity living) {
        List<ItemStack> result = new java.util.ArrayList<>();
        for (EquipmentSlot s : EquipmentSlot.values()) {
            if (s.getType() == EquipmentSlot.Type.HUMANOID_ARMOR) {
                result.add(living.getItemBySlot(s));
            }
        }
        return result;
    }

    public DragonArmorItem(ArmorType type, Item.Properties props) {
        super(MATERIAL, type, props.fireResistant().rarity(Rarity.EPIC));
    }
    @Override
    public <T extends LivingEntity> int damageItem(ItemStack stack, int amount, T entity, Consumer<Item> onBroken) {
        //龙之套装不掉耐久
        return 0;
    }
    //1.21.4: initializeClient is gone; GeckoLib serves the armor renderer through GeoItem.createGeoRenderer
    @Override
    public void createGeoRenderer(Consumer<software.bernie.geckolib.animatable.client.GeoRenderProvider> consumer) {
        consumer.accept(net.the_last_sword.client.DragonCultArmorClientExtensions.create(DragonArmorRenderer::new));
    }

    // ==================== 实现TheLastEndArmorItem抽象方法 ====================

    //龙套装初始等级6级
    @Override
    protected int getDefaultLevel() {
        return 6;
    }

    @Override
    protected int[] getBaseArmorValues() {
        return new int[]{8, 12, 12, 8}; // [靴子, 护腿, 胸甲, 头盔]
    }

    @Override
    protected double getBaseToughness() {
        return 10.0;
    }

    @Override
    protected String getArmorName() {
        return "DragonArmor";
    }

    // ==================== Forge Energy系统 ====================

    // 能量存储由 RegisterCapabilitiesEvent 注册（Capabilities.EnergyStorage.ITEM），此处提供工厂方法
    public static ItemEnergyStorage createEnergyStorage(ItemStack stack) {
        return new ItemEnergyStorage(stack, () -> DragonArmorItem.getMaxEnergy(stack));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true; // 始终显示能量条
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        IEnergyStorage energy = net.the_last_sword.mv.LegacyEnergy.item(stack);
        if (energy == null) return 0;
        int maxEnergy = energy.getMaxEnergyStored();
        int currentEnergy = energy.getEnergyStored();
        if (maxEnergy == 0) return 0;
        return Math.round(13.0F * currentEnergy / maxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        IEnergyStorage energy = net.the_last_sword.mv.LegacyEnergy.item(stack);
        if (energy == null) return 0x8B00FF;
        int maxEnergy = energy.getMaxEnergyStored();
        int currentEnergy = energy.getEnergyStored();
        if (maxEnergy == 0) return 0x8B00FF;

        float ratio = (float) currentEnergy / maxEnergy;

        if (ratio < 0.25F) {
            return 0xFF0000; // 红色
        } else if (ratio < 0.5F) {
            return 0xFF8C00; // 橙色
        } else if (ratio < 0.75F) {
            return 0x9B30FF; // 紫色
        } else {
            return 0xBF00FF; // 亮紫色
        }
    }

    @Override
    protected void appendSpecificTooltip(ItemStack itemstack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        // 显示能量信息
        IEnergyStorage energy = net.the_last_sword.mv.LegacyEnergy.item(itemstack);
        if (energy != null) {
            list.add(Component.translatable("item_tooltip.the_last_sword.energy")
                    .append(": §a" + energy.getEnergyStored() + " §r/ " + energy.getMaxEnergyStored() + " FE"));
        }

        // 部件特定描述
        EquipmentSlot slot = this.getEquipmentSlot(itemstack);
        switch (slot) {
            case HEAD -> list.add(Component.translatable("item_tooltip.the_last_sword.dragon_armor_helmet"));
            case CHEST -> list.add(Component.translatable("item_tooltip.the_last_sword.dragon_armor_chestplate"));
            case LEGS -> list.add(Component.translatable("item_tooltip.the_last_sword.dragon_armor_leggings"));
            case FEET -> list.add(Component.translatable("item_tooltip.the_last_sword.dragon_armor_boots"));
        }

        // 龙魂觉醒被动描述（读取配置值）
        int crystalRange = TheLastSwordConfiguration.getDragonArmorEnderCrystalRangeSafely();
        int chargeRate = TheLastSwordConfiguration.getDragonArmorEnderCrystalChargeRateSafely();
        list.add(Component.translatable("item_tooltip.the_last_sword.dragon_armor_skill", crystalRange, chargeRate));
        list.add(Component.translatable("item_tooltip_lore.the_last_sword.dragon_armor")
            .withStyle(ChatFormatting.GRAY));
    }

    // ==================== GeckoLib动画 ====================

    //GeckoLib 5: the render state no longer carries the entity (4.x's DataTickets.ENTITY); DragonArmorRenderer records
    //the wearer under this ticket in addRenderData. No client class is named here: this item loads on a server too.
    public static final DataTicket<LivingEntity> WEARER = DataTicket.create("the_last_sword_dragon_armor_wearer", LivingEntity.class);

    private static Entity entityOf(AnimationTest<DragonArmorItem> event) {
        return event.getData(WEARER);
    }

    private PlayState predicate(AnimationTest<DragonArmorItem> event) {
        if (this.animationprocedure.equals("empty")) {
            Entity entity = entityOf(event);
            if (!(entity instanceof LivingEntity)) {
                return PlayState.STOP;
            }
            if (entity instanceof ArmorStand) {
                event.setAnimation(RawAnimation.begin().thenLoop("idle"));
                return PlayState.CONTINUE;
            }

            Set<Item> wornArmor = new ObjectOpenHashSet<>();
            for (ItemStack stack : armorStacks((LivingEntity) entity)) {
                if (stack.isEmpty())
                    return PlayState.STOP;
                wornArmor.add(stack.getItem());
            }

            boolean isWearingAll = wornArmor.containsAll(ObjectArrayList.of(
                    ModItems.DRAGON_ARMOR_BOOTS.get(),
                    ModItems.DRAGON_ARMOR_LEGGINGS.get(),
                    ModItems.DRAGON_ARMOR_CHESTPLATE.get(),
                    ModItems.DRAGON_ARMOR_HELMET.get()));

            if (!isWearingAll) {
                return PlayState.STOP;
            }

            if (entity instanceof LivingEntity livingEntity) {
                // 检查是否处于龙套提供的飞行状态
                boolean isDragonArmorFlying = false;
                if (livingEntity instanceof Player player) {
                    ItemStack chestplate = player.getItemBySlot(EquipmentSlot.CHEST);
                    boolean wearingDragonChestplate = !chestplate.isEmpty() &&
                            chestplate.getItem() instanceof DragonArmorItem.Chestplate;
                    isDragonArmorFlying = wearingDragonChestplate && player.getAbilities().flying;
                }

                //根据飞行状态设置动画
                if (isDragonArmorFlying) {
                    //检查当前动画是否是fly，如果不是则切换
                    if (event.controller().getCurrentAnimation() == null ||
                        !event.controller().getCurrentAnimation().animation().name().equals("fly")) {
                        //切换到飞行动画
                        event.setAnimation(RawAnimation.begin().thenLoop("fly"));
                    }
                } else {
                    //检查当前动画是否是idle，如果不是则切换
                    if (event.controller().getCurrentAnimation() != null &&
                        event.controller().getCurrentAnimation().animation().name().equals("fly")) {
                        //切换到待机动画
                        event.setAnimation(RawAnimation.begin().thenLoop("idle"));
                    } else if (event.controller().getCurrentAnimation() == null) {
                        //初始状态，直接设置idle动画
                        event.setAnimation(RawAnimation.begin().thenLoop("idle"));
                    }
                }
            } else {
                event.setAnimation(RawAnimation.begin().thenLoop("idle"));
            }
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    String prevAnim = "empty";

    private PlayState procedurePredicate(AnimationTest<DragonArmorItem> event) {
        if (!this.animationprocedure.equals("empty") &&
                event.controller().getAnimationState() == AnimationController.State.STOPPED ||
                (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {

            if (!this.animationprocedure.equals(prevAnim))
                event.controller().forceAnimationReset();

            event.setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));

            if (event.controller().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.controller().forceAnimationReset();
            }

            Entity entity = entityOf(event);
            if (entity instanceof ArmorStand) {
                return PlayState.CONTINUE;
            }
            if (!(entity instanceof LivingEntity)) {
                return PlayState.STOP;
            }

            Set<Item> wornArmor = new ObjectOpenHashSet<>();
            for (ItemStack stack : armorStacks((LivingEntity) entity)) {
                if (stack.isEmpty())
                    return PlayState.STOP;
                wornArmor.add(stack.getItem());
            }

            boolean isWearingAll = wornArmor.containsAll(ObjectArrayList.of(
                    ModItems.DRAGON_ARMOR_BOOTS.get(),
                    ModItems.DRAGON_ARMOR_LEGGINGS.get(),
                    ModItems.DRAGON_ARMOR_CHESTPLATE.get(),
                    ModItems.DRAGON_ARMOR_HELMET.get()));

            return isWearingAll ? PlayState.CONTINUE : PlayState.STOP;
        } else if (animationprocedure.equals("empty")) {
            prevAnim = "empty";
            return PlayState.STOP;
        }
        prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>("controller", 5, this::predicate));
        data.add(new AnimationController<>("procedureController", 5, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    // ==================== 能量系统 ====================

    // 计算龙套的最大能量
    //公式：最大能量 = 基础值 + 等级 × 每级提升
    public static int getMaxEnergy(ItemStack stack) {
        if (stack.isEmpty() || !(stack.getItem() instanceof DragonArmorItem)) {
            return 0;
        }
        int level = ItemLevelHelper.getLevel(stack);
        int perLevel = TheLastSwordConfiguration.getDragonArmorEnergyPerLevelSafely();
        return BASE_ENERGY + level * perLevel;
    }

    //检查全套龙套是否都有能量>0
    public static boolean hasEnergyFullSet(Player player) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.HUMANOID_ARMOR) continue;
            ItemStack stack = player.getItemBySlot(slot);
            if (!(stack.getItem() instanceof DragonArmorItem)) return false;
            IEnergyStorage energy = net.the_last_sword.mv.LegacyEnergy.item(stack);
            boolean hasEnergy = energy != null && energy.getEnergyStored() > 0;
            if (!hasEnergy) return false;
        }
        return true;
    }

    //检查实体是否穿戴全套龙之盔甲
    public static boolean isFullSet(LivingEntity entity) {
        return !entity.getItemBySlot(EquipmentSlot.HEAD).isEmpty() &&
                entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof DragonArmorItem.Helmet &&
                !entity.getItemBySlot(EquipmentSlot.CHEST).isEmpty() &&
                entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof DragonArmorItem.Chestplate &&
                !entity.getItemBySlot(EquipmentSlot.LEGS).isEmpty() &&
                entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof DragonArmorItem.Leggings &&
                !entity.getItemBySlot(EquipmentSlot.FEET).isEmpty() &&
                entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof DragonArmorItem.Boots;
    }

    // ==================== 4个子类：头盔、胸甲、护腿、靴子 ====================

    public static class Helmet extends DragonArmorItem {
        public Helmet() {
            super(ArmorType.HELMET, net.the_last_sword.mv.RegIds.stamp(new Item.Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.HEAD;
        }
    }

    public static class Chestplate extends DragonArmorItem {
        public Chestplate() {
            super(ArmorType.CHESTPLATE, net.the_last_sword.mv.RegIds.stamp(new Item.Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.CHEST;
        }
    }

    public static class Leggings extends DragonArmorItem {
        public Leggings() {
            super(ArmorType.LEGGINGS, net.the_last_sword.mv.RegIds.stamp(new Item.Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.LEGS;
        }
    }

    public static class Boots extends DragonArmorItem {
        public Boots() {
            super(ArmorType.BOOTS, net.the_last_sword.mv.RegIds.stamp(new Item.Properties()));
        }

        @Override
        public EquipmentSlot getEquipmentSlot(ItemStack stack) {
            return EquipmentSlot.FEET;
        }
    }
}
