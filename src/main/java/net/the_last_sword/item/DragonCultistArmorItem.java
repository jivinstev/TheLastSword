package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.DragonCultArmorClientExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;

import software.bernie.geckolib.util.GeckoLibUtil;

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

    private static final Map<ArmorItem.Type, Integer> DEFENSE = Map.of(
            ArmorItem.Type.BOOTS, DEFENSE_PER_TYPE[0],
            ArmorItem.Type.LEGGINGS, DEFENSE_PER_TYPE[1],
            ArmorItem.Type.CHESTPLATE, DEFENSE_PER_TYPE[2],
            ArmorItem.Type.HELMET, DEFENSE_PER_TYPE[3]);

    private static final ArmorMaterial MATERIAL = new ArmorMaterial(
            DEFENSE,
            15,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            () -> Ingredient.of(Items.LEATHER),
            List.of(new ArmorMaterial.Layer(ResourceLocation.parse("the_last_sword:dragon_cultist_armor"))),
            1.0F,
            0.0F);

    private static final Holder<ArmorMaterial> MATERIAL_HOLDER = Holder.direct(MATERIAL);

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected DragonCultistArmorItem(Type type) {
        super(MATERIAL_HOLDER, type, new Properties().durability(DURABILITY_PER_TYPE[type.getSlot().getIndex()] * DURABILITY_MULTIPLIER));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(DragonCultArmorClientExtensions.cultist());
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.parse(TEXTURE);
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable(DESCRIPTION_KEY));
        tooltip.add(Component.translatable(LORE_KEY).withStyle(ChatFormatting.GRAY));
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
            super(Type.HELMET);
        }
    }

    public static class Chestplate extends DragonCultistArmorItem {
        public Chestplate() {
            super(Type.CHESTPLATE);
        }
    }

    public static class Leggings extends DragonCultistArmorItem {
        public Leggings() {
            super(Type.LEGGINGS);
        }
    }

    public static class Boots extends DragonCultistArmorItem {
        public Boots() {
            super(Type.BOOTS);
        }
    }
}
