package net.the_last_sword.compat.lucky_block.lucky_event.events;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.the_last_sword.compat.lucky_block.lucky_event.LuckyEvent;
import net.the_last_sword.compat.lucky_block.lucky_event.LuckyEventCategory;
import net.the_last_sword.compat.lucky_block.lucky_event.LuckyEventContext;
import net.the_last_sword.init.ModItems;
import net.the_last_sword.util.nbt.ItemLevelHelper;
import net.the_last_sword.ModHolders;

//Dragon Bob 事件 - 幸运方块原位置生成满配 12 级龙盔甲+龙之剑僵尸, 附魔参考封印尖塔守卫(剑参考剑士)
public class DragonBobEvent extends LuckyEvent {

    private static final String BOB_NAME = "Dragon Bob!!!";
    private static final int ITEM_LEVEL = 12;

    @Override
    public LuckyEventCategory getCategory() {
        return LuckyEventCategory.BAD;
    }

    @Override
    public String getId() {
        return "Dragon Set Bob";
    }

    @Override
    public int getMinLuck() {
        return -100;
    }

    @Override
    public int getMaxLuck() {
        return 0;
    }

    @Override
    public void execute(LuckyEventContext ctx) {
        ServerLevel world = ctx.world();
        BlockPos spawnPos = ctx.pos();
        float yaw = ctx.random().nextFloat() * 360.0F;

        Zombie bob = new Zombie(world);
        bob.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, yaw, 0.0F);
        bob.setCustomName(Component.literal(BOB_NAME));
        bob.setCustomNameVisible(true);

        equipBob(bob);

        world.addFreshEntity(bob);
    }

    private static void equipBob(Zombie bob) {
        ItemStack helmet = new ItemStack(ModItems.DRAGON_ARMOR_HELMET.get());
        helmet.enchant(ModHolders.enchantment(Enchantments.PROTECTION), 4);
        helmet.enchant(ModHolders.enchantment(Enchantments.THORNS), 3);
        helmet.enchant(ModHolders.enchantment(Enchantments.UNBREAKING), 3);
        helmet.enchant(ModHolders.enchantment(Enchantments.MENDING), 1);
        helmet.enchant(ModHolders.enchantment(Enchantments.RESPIRATION), 3);
        helmet.enchant(ModHolders.enchantment(Enchantments.AQUA_AFFINITY), 1);
        helmet.enchant(ModHolders.enchantment(Enchantments.PROJECTILE_PROTECTION), 4);
        ItemLevelHelper.setLevel(helmet, ITEM_LEVEL);
        bob.setItemSlot(EquipmentSlot.HEAD, helmet);
        bob.setDropChance(EquipmentSlot.HEAD, 0.0F);

        ItemStack chestplate = new ItemStack(ModItems.DRAGON_ARMOR_CHESTPLATE.get());
        chestplate.enchant(ModHolders.enchantment(Enchantments.PROTECTION), 4);
        chestplate.enchant(ModHolders.enchantment(Enchantments.THORNS), 3);
        chestplate.enchant(ModHolders.enchantment(Enchantments.UNBREAKING), 3);
        chestplate.enchant(ModHolders.enchantment(Enchantments.MENDING), 1);
        chestplate.enchant(ModHolders.enchantment(Enchantments.BLAST_PROTECTION), 4);
        ItemLevelHelper.setLevel(chestplate, ITEM_LEVEL);
        bob.setItemSlot(EquipmentSlot.CHEST, chestplate);
        bob.setDropChance(EquipmentSlot.CHEST, 0.0F);

        ItemStack leggings = new ItemStack(ModItems.DRAGON_ARMOR_LEGGINGS.get());
        leggings.enchant(ModHolders.enchantment(Enchantments.PROTECTION), 4);
        leggings.enchant(ModHolders.enchantment(Enchantments.THORNS), 3);
        leggings.enchant(ModHolders.enchantment(Enchantments.UNBREAKING), 3);
        leggings.enchant(ModHolders.enchantment(Enchantments.MENDING), 1);
        leggings.enchant(ModHolders.enchantment(Enchantments.FIRE_PROTECTION), 4);
        leggings.enchant(ModHolders.enchantment(Enchantments.SWIFT_SNEAK), 3);
        ItemLevelHelper.setLevel(leggings, ITEM_LEVEL);
        bob.setItemSlot(EquipmentSlot.LEGS, leggings);
        bob.setDropChance(EquipmentSlot.LEGS, 0.0F);

        ItemStack boots = new ItemStack(ModItems.DRAGON_ARMOR_BOOTS.get());
        boots.enchant(ModHolders.enchantment(Enchantments.PROTECTION), 4);
        boots.enchant(ModHolders.enchantment(Enchantments.THORNS), 3);
        boots.enchant(ModHolders.enchantment(Enchantments.UNBREAKING), 3);
        boots.enchant(ModHolders.enchantment(Enchantments.MENDING), 1);
        boots.enchant(ModHolders.enchantment(Enchantments.DEPTH_STRIDER), 3);
        boots.enchant(ModHolders.enchantment(Enchantments.FEATHER_FALLING), 4);
        boots.enchant(ModHolders.enchantment(Enchantments.SOUL_SPEED), 3);
        ItemLevelHelper.setLevel(boots, ITEM_LEVEL);
        bob.setItemSlot(EquipmentSlot.FEET, boots);
        bob.setDropChance(EquipmentSlot.FEET, 0.0F);

        ItemStack sword = new ItemStack(ModItems.DRAGON_SWORD.get());
        sword.enchant(ModHolders.enchantment(Enchantments.SHARPNESS), 5);
        sword.enchant(ModHolders.enchantment(Enchantments.SWEEPING_EDGE), 3);
        sword.enchant(ModHolders.enchantment(Enchantments.LOOTING), 3);
        sword.enchant(ModHolders.enchantment(Enchantments.KNOCKBACK), 2);
        sword.enchant(ModHolders.enchantment(Enchantments.UNBREAKING), 3);
        sword.enchant(ModHolders.enchantment(Enchantments.MENDING), 1);
        ItemLevelHelper.setLevel(sword, ITEM_LEVEL);
        bob.setItemSlot(EquipmentSlot.MAINHAND, sword);
        bob.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }
}
