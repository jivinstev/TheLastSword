package net.the_last_sword.compat.cataclysm;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.item.DragonCrystalSoulStone;
import top.theillusivec4.curios.api.SlotAttribute;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;


//Cataclysm联动奖章基类 - 可作为饰品穿戴，穿戴后增加1个curio槽位
public abstract class CataclysmMedal extends DragonCrystalSoulStone implements ICurioItem {

    private final String translationKey;

    public CataclysmMedal(String medalName) {
        this.translationKey = "compat.cataclysm." + medalName;
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.translationKey);
    }

    @Override
    public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation uuid, ItemStack stack) {
        Multimap<Holder<Attribute>, AttributeModifier> modifiers = LinkedHashMultimap.create();

        //穿戴奖章后增加1个curio槽位（使用传入的uuid确保每个槽位位置的修饰符独立）
        modifiers.put(
            SlotAttribute.getOrCreate("curio"),
            new AttributeModifier(ResourceLocation.fromNamespaceAndPath("the_last_sword", "cataclysm_medal_curio_slot"), 1.0, AttributeModifier.Operation.ADD_VALUE)
        );

        //子类可以覆写此方法添加更多属性加成
        return modifiers;
    }
}
