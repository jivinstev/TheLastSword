package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAssets;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.DragonCultArmorClientExtensions;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

//圣骑士以更高护甲抵挡正面攻击，耐久仍维持铁甲档次
public abstract class DragonCultPaladinArmorItem extends ArmorItem implements GeoItem {

    private static final String TEXTURE = "the_last_sword:textures/item/dragon_cult_paladin_armor.png";
    private static final String DESCRIPTION_KEY = "item_tooltip.the_last_sword.dragon_cult_paladin_armor";
    private static final String LORE_KEY = "item_tooltip_lore.the_last_sword.dragon_cult_paladin_armor";

    //耐久基数与倍率同铁套
    private static final int[] DURABILITY_PER_TYPE = {13, 15, 16, 11};
    private static final int DURABILITY_MULTIPLIER = 15;
    //护甲值按 [靴子, 护腿, 胸甲, 头盔] 排列
    private static final int[] DEFENSE_PER_TYPE = {3, 6, 8, 3};

    private static final String LAYER = "the_last_sword:dragon_cult_paladin_armor";
    //耐久单位即倍率，各部位耐久 = 13/15/16/11 × 倍率
    private static final ArmorMaterial MATERIAL = new ArmorMaterial(
            DURABILITY_MULTIPLIER,
            Map.of(
                    ArmorType.BOOTS, DEFENSE_PER_TYPE[0],
                    ArmorType.LEGGINGS, DEFENSE_PER_TYPE[1],
                    ArmorType.CHESTPLATE, DEFENSE_PER_TYPE[2],
                    ArmorType.HELMET, DEFENSE_PER_TYPE[3]),
            9,
            SoundEvents.ARMOR_EQUIP_IRON,
            2.0F,
            0.0F,
            ItemTags.REPAIRS_IRON_ARMOR,
            ResourceKey.create(EquipmentAssets.ROOT_ID, ResourceLocation.parse(LAYER)));

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected DragonCultPaladinArmorItem(ArmorType type) {
        super(MATERIAL, type, new Properties());
    }

    public IClientItemExtensions createClientExtensions() {
        return DragonCultArmorClientExtensions.paladin();
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable(DESCRIPTION_KEY));
        tooltip.add(Component.translatable(LORE_KEY).withStyle(ChatFormatting.GRAY));
    }

    //披风常驻摆动
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 5, state -> {
            state.getController().setAnimation(RawAnimation.begin().thenLoop("idle"));
            return PlayState.CONTINUE;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    public static class Helmet extends DragonCultPaladinArmorItem {
        public Helmet() {
            super(ArmorType.HELMET);
        }
    }

    public static class Chestplate extends DragonCultPaladinArmorItem {
        public Chestplate() {
            super(ArmorType.CHESTPLATE);
        }
    }

    public static class Leggings extends DragonCultPaladinArmorItem {
        public Leggings() {
            super(ArmorType.LEGGINGS);
        }
    }

    public static class Boots extends DragonCultPaladinArmorItem {
        public Boots() {
            super(ArmorType.BOOTS);
        }
    }
}
