package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.the_last_sword.compat.gear.ArmorItem;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.DragonCultArmorClientExtensions;
import com.geckolib.animatable.GeoItem;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

//拜龙教盔甲，护甲取原版锁链甲档次，另加每件1点韧性
public abstract class DragonCultistArmorItem extends ArmorItem implements GeoItem {

    private static final String TEXTURE = "the_last_sword:textures/item/dragon_cultist_armor.png";
    private static final String DESCRIPTION_KEY = "item_tooltip.the_last_sword.dragon_cultist_armor";
    private static final String LORE_KEY = "item_tooltip_lore.the_last_sword.dragon_cultist_armor";

    //耐久基数与倍率同皮革套
    private static final int[] DURABILITY_PER_TYPE = {13, 15, 16, 11};
    private static final int DURABILITY_MULTIPLIER = 5;
    //护甲值按 [靴子, 护腿, 胸甲, 头盔] 排列
    private static final int[] DEFENSE_PER_TYPE = {1, 4, 5, 2};

    private static final Map<ArmorType, Integer> DEFENSE = Map.of(
            ArmorType.BOOTS, DEFENSE_PER_TYPE[0],
            ArmorType.LEGGINGS, DEFENSE_PER_TYPE[1],
            ArmorType.CHESTPLATE, DEFENSE_PER_TYPE[2],
            ArmorType.HELMET, DEFENSE_PER_TYPE[3]);

    //耐久单位即倍率，各部位耐久 = 13/15/16/11 × 倍率
    private static final ArmorMaterial MATERIAL = new ArmorMaterial(
            DURABILITY_MULTIPLIER,
            DEFENSE,
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            1.0F,
            0.0F,
            ItemTags.REPAIRS_LEATHER_ARMOR,
            ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.parse("the_last_sword:dragon_cultist_armor")));

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected DragonCultistArmorItem(ArmorType type) {
        super(MATERIAL, type, net.the_last_sword.mv.RegIds.stamp(new Properties()));
    }

    //1.21.4: initializeClient is gone; GeckoLib serves the armor renderer through GeoItem.createGeoRenderer
    @Override
    public void createGeoRenderer(java.util.function.Consumer<com.geckolib.animatable.client.GeoRenderProvider> consumer) {
        consumer.accept(DragonCultArmorClientExtensions.cultist());
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext context, net.minecraft.world.item.component.TooltipDisplay display, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        super.appendHoverText(stack, context, display, tooltipAdder, flag);
        tooltipAdder.accept(Component.translatable(DESCRIPTION_KEY));
        tooltipAdder.accept(Component.translatable(LORE_KEY).withStyle(ChatFormatting.GRAY));
    }

    //盔甲本体无动画，仅需模型与贴图
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public static class Helmet extends DragonCultistArmorItem {
        public Helmet() {
            super(ArmorType.HELMET);
        }
    }

    public static class Chestplate extends DragonCultistArmorItem {
        public Chestplate() {
            super(ArmorType.CHESTPLATE);
        }
    }

    public static class Leggings extends DragonCultistArmorItem {
        public Leggings() {
            super(ArmorType.LEGGINGS);
        }
    }

    public static class Boots extends DragonCultistArmorItem {
        public Boots() {
            super(ArmorType.BOOTS);
        }
    }
}
