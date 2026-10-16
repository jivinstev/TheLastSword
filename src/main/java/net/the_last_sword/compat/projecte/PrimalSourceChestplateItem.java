package net.the_last_sword.compat.projecte;

import moze_intel.projecte.utils.text.PELang;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import moze_intel.projecte.gameObjs.items.armor.GemChest;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.the_last_sword.compat.projecte.client.PrimalSourceArmorClientExtension;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;
import java.util.List;

public final class PrimalSourceChestplateItem extends GemChest implements PrimalSourceArmorItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PrimalSourceChestplateItem() {
        super(PrimalSourceArmorSupport.properties());
    }

    @Override
    public EquipmentSlot getPrimalArmorSlot() {
        return EquipmentSlot.CHEST;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return PrimalSourceArmorSupport.addAttributes(super.getDefaultAttributeModifiers(stack), this);
    }

    @Override
    public int getEnchantmentValue() {
        return 64;
    }

    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, List<Component> tooltips, TooltipFlag flags) {
        super.appendHoverText(stack, level, tooltips, flags);
        tooltips.removeIf(line -> line.getContents() instanceof TranslatableContents text
            && text.getKey().equals(PELang.GEM_LORE_CHEST.getTranslationKey()));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        // 1.21：附魔适用范围改为数据驱动，按同部位的原版盔甲判断（原 enchantment.category.canEnchant）。
        return enchantment.value().isSupportedItem(new ItemStack(Items.DIAMOND_CHESTPLATE));
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return enchantment.value().isPrimaryItem(new ItemStack(Items.DIAMOND_CHESTPLATE));
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, level, entity, slot, selected);
        // 1.21.1 无 onArmorTick：仅在穿戴于本部位时执行。
        if (entity instanceof Player player && player.getItemBySlot(getPrimalArmorSlot()) == stack) {
            PrimalSourceArmorSupport.tickFluidWalking(player, level, FluidTags.LAVA);
        }
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new PrimalSourceArmorClientExtension<>());
    }

    @Override
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.parse("the_last_sword:textures/item/primal_source_armor_1.png");
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
