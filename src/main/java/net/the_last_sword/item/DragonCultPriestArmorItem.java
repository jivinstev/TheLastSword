package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.client.DragonCultArmorClientExtensions;
import net.the_last_sword.init.ModAttributes;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

//拜龙教祭司法袍，护甲、耐久与韧性取原版钻石甲档次
public abstract class DragonCultPriestArmorItem extends ArmorItem implements GeoItem {

    private static final String TEXTURE = "the_last_sword:textures/item/dragon_cult_priest_armor.png";
    private static final String DESCRIPTION_KEY = "item_tooltip.the_last_sword.dragon_cult_priest_armor";
    private static final String LORE_KEY = "item_tooltip_lore.the_last_sword.dragon_cult_priest_armor";

    //耐久基数与倍率同钻石套
    private static final int[] DURABILITY_PER_TYPE = {13, 15, 16, 11};
    private static final int DURABILITY_MULTIPLIER = 33;
    //护甲值按 [靴子, 护腿, 胸甲, 头盔] 排列
    private static final int[] DEFENSE_PER_TYPE = {3, 6, 8, 3};
    //各部位使用独立 UUID，确保整套加成可以叠加并随装备正确移除
    private static final UUID[] MAX_JUSTIFIED_DEFENCE_MODIFIER_UUIDS = {
        UUID.fromString("4c54634d-cb80-45f2-8489-6e565137936c"),
        UUID.fromString("7798766c-5964-49ad-96a1-f98540b55d63"),
        UUID.fromString("ccfb2151-5886-4d25-8f05-13c75a5aca70"),
        UUID.fromString("d5b17e73-339b-4e18-bdf2-e9cfb90b1675")
    };
    private static final UUID[] RECOVERY_SPEED_MODIFIER_UUIDS = {
        UUID.fromString("d3ae595f-92e8-48b3-bbfa-433950401130"),
        UUID.fromString("ef93d754-a91b-48b9-8de8-43630801b7ec"),
        UUID.fromString("b7d47df7-9657-4bf5-9657-818ef72bf9db"),
        UUID.fromString("e3356bb2-90c7-45ad-a081-2cf7fe4c88fc")
    };

    private static final Holder<ArmorMaterial> MATERIAL = createMaterial();

    private static Holder<ArmorMaterial> createMaterial() {
        Map<Type, Integer> defense = new EnumMap<>(Type.class);
        for (Type t : Type.values()) {
            if (t == Type.BODY) {
                defense.put(t, 0);
            } else {
                defense.put(t, DEFENSE_PER_TYPE[t.getSlot().getIndex()]);
            }
        }
        return Holder.direct(new ArmorMaterial(
            defense,
            10,
            SoundEvents.ARMOR_EQUIP_DIAMOND,
            () -> Ingredient.of(Items.DIAMOND),
            List.of(new ArmorMaterial.Layer(ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_cult_priest_armor"))),
            2.0F,
            0.0F
        ));
    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    protected DragonCultPriestArmorItem(Type type) {
        super(MATERIAL, type, new Properties().durability(
            DURABILITY_PER_TYPE[type.getSlot().getIndex()] * DURABILITY_MULTIPLIER));
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        ItemAttributeModifiers.Builder builder = ItemAttributeModifiers.builder();
        for (ItemAttributeModifiers.Entry entry : super.getDefaultAttributeModifiers().modifiers()) {
            builder.add(entry.attribute(), entry.modifier(), entry.slot());
        }
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(this.getEquipmentSlot());
        String suffix = this.getType().getName();
        builder.add(ModAttributes.MAX_JUSTIFIED_DEFENCE, new AttributeModifier(
            ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_cult_priest_armor_max_justified_defence_" + suffix),
            1.0,
            AttributeModifier.Operation.ADD_VALUE
        ), group);
        builder.add(ModAttributes.JUSTIFIED_DEFENCE_RECOVERY_SPEED, new AttributeModifier(
            ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_cult_priest_armor_recovery_speed_" + suffix),
            0.25,
            AttributeModifier.Operation.ADD_MULTIPLIED_BASE
        ), group);
        return builder.build();
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(DragonCultArmorClientExtensions.priest());
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

    //披风、腰带与裙摆常驻摆动
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

    public static class Helmet extends DragonCultPriestArmorItem {
        public Helmet() {
            super(Type.HELMET);
        }
    }

    public static class Chestplate extends DragonCultPriestArmorItem {
        public Chestplate() {
            super(Type.CHESTPLATE);
        }
    }

    public static class Leggings extends DragonCultPriestArmorItem {
        public Leggings() {
            super(Type.LEGGINGS);
        }
    }

    public static class Boots extends DragonCultPriestArmorItem {
        public Boots() {
            super(Type.BOOTS);
        }
    }
}
