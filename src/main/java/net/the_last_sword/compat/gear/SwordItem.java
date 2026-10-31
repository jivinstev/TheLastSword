package net.the_last_sword.compat.gear;

import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Repairable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

/** The 1.21.1 tiered SwordItem contract, built on the 1.21.4 components exactly as ToolMaterial.applySwordProperties does. */
public class SwordItem extends Item {
    private static final Identifier BASE_ATTACK_DAMAGE_ID = Identifier.withDefaultNamespace("base_attack_damage");
    private static final Identifier BASE_ATTACK_SPEED_ID = Identifier.withDefaultNamespace("base_attack_speed");
    private final Tier tier;

    public SwordItem(Tier tier, Item.Properties properties, ItemAttributeModifiers attributes) {
        super(applyTier(tier, properties).attributes(attributes));
        this.tier = tier;
    }

    public Tier getTier() {
        return this.tier;
    }

    public static ItemAttributeModifiers createAttributes(Tier tier, int attackDamage, float attackSpeed) {
        return ItemAttributeModifiers.builder()
            .add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(BASE_ATTACK_DAMAGE_ID, (double) ((float) attackDamage + tier.getAttackDamageBonus()), AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
            )
            .add(
                Attributes.ATTACK_SPEED,
                new AttributeModifier(BASE_ATTACK_SPEED_ID, (double) attackSpeed, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.MAINHAND
            )
            .build();
    }

    private static Item.Properties applyTier(Tier tier, Item.Properties properties) {
        HolderGetter<Block> blocks = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        // A tier with 0 uses is the authors' "infinite durability" (1.20.1: a max damage of 0 is not damageable).
        // From 1.20.5 durability(0) still makes the stack damageable with a max damage of 0, so the first hit, mined
        // block or tool use destroyed it: give such a tier no durability components at all.
        properties = (tier.getUses() > 0 ? properties.durability(tier.getUses()) : properties.stacksTo(1))
            .enchantable(tier.getEnchantmentValue())
            .component(
                DataComponents.TOOL,
                new Tool(
                    List.of(
                        Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F),
                        Tool.Rule.overrideSpeed(blocks.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)
                    ),
                    1.0F,
                    2,
                    true
                )
            );
        // 1.21.1 read the repair ingredient lazily; 1.21.4 stores it as a component, so an item that is
        // not bound yet at construction time simply gets no repair component.
        try {
            properties = properties.component(DataComponents.REPAIRABLE, new Repairable(tier.getRepairIngredient().getValues()));
        } catch (RuntimeException ignored) {
        }
        return properties;
    }
}
