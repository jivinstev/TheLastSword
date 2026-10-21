package net.the_last_sword.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.compat.CompatCheck;
import net.the_last_sword.compat.cataclysm.CataclysmItemsRegistry;
import net.the_last_sword.compat.curios.CuriosItemsRegistry;
import net.the_last_sword.compat.lucky_block.TheLastEndLuckyBlockDisplayItem;
import net.the_last_sword.item.*;
import net.the_last_sword.test.UltraTestSwordItem;
import net.minecraft.world.item.Rarity;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
        DeferredRegister.create(Registries.ITEM, TheLastSwordMod.MOD_ID);

    //终焉卷轴 - 知识书
    public static final Supplier<Item> THE_LAST_END_SCROLL = ITEMS.register("the_last_end_scroll",
        () -> net.the_last_sword.mv.RegIds.enter("the_last_end_scroll") ? null : new TheLastEndScroll()
    );

    //法师的笔记1 - 剧情纸条
    public static final Supplier<Item> MAGE_NOTE_1 = ITEMS.register("mage_note_1",
        () -> net.the_last_sword.mv.RegIds.enter("mage_note_1") ? null : new PaperNote(
            "item.the_last_sword.mage_note_1",
            "item_tooltip.the_last_sword.mage_note_1",
            "note.the_last_sword.mage_note_1.content",
            "note.the_last_sword.mage_note_1.gui"
        )
    );

    //法师的笔记2 - 剧情纸条
    public static final Supplier<Item> MAGE_NOTE_2 = ITEMS.register("mage_note_2",
        () -> net.the_last_sword.mv.RegIds.enter("mage_note_2") ? null : new PaperNote(
            "item.the_last_sword.mage_note_2",
            "item_tooltip.the_last_sword.mage_note_2",
            "note.the_last_sword.mage_note_2.content",
            "note.the_last_sword.mage_note_2.gui"
        )
    );

    //法师的笔记3 - 剧情纸条
    public static final Supplier<Item> MAGE_NOTE_3 = ITEMS.register("mage_note_3",
        () -> net.the_last_sword.mv.RegIds.enter("mage_note_3") ? null : new PaperNote(
            "item.the_last_sword.mage_note_3",
            "item_tooltip.the_last_sword.mage_note_3",
            "note.the_last_sword.mage_note_3.content",
            "note.the_last_sword.mage_note_3.gui"
        )
    );

    //法师的笔记4 - 剧情纸条
    public static final Supplier<Item> MAGE_NOTE_4 = ITEMS.register("mage_note_4",
        () -> net.the_last_sword.mv.RegIds.enter("mage_note_4") ? null : new PaperNote(
            "item.the_last_sword.mage_note_4",
            "item_tooltip.the_last_sword.mage_note_4",
            "note.the_last_sword.mage_note_4.content",
            "note.the_last_sword.mage_note_4.gui"
        )
    );

    //拜龙教的秘信 - 剧情纸条
    public static final Supplier<Item> DRAGON_CULT_SECRET_LETTER = ITEMS.register("dragon_cult_secret_letter",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_secret_letter") ? null : new PaperNote(
            "item.the_last_sword.dragon_cult_secret_letter",
            "item_tooltip.the_last_sword.dragon_cult_secret_letter",
            "note.the_last_sword.dragon_cult_secret_letter.content",
            "note.the_last_sword.dragon_cult_secret_letter.gui"
        )
    );

    //旅行者的讯息 - 剧情纸条
    public static final Supplier<Item> TRAVELER_MESSAGE = ITEMS.register("traveler_message",
        () -> net.the_last_sword.mv.RegIds.enter("traveler_message") ? null : new PaperNote(
            "item.the_last_sword.traveler_message",
            "item_tooltip.the_last_sword.traveler_message",
            "note.the_last_sword.traveler_message.content",
            "note.the_last_sword.traveler_message.content"
        )
    );

    //尖塔灵魂印记 - 剧情纸条
    public static final Supplier<Item> WHERE_IS_IT_NOTE = ITEMS.register("where_is_it_note",
        () -> net.the_last_sword.mv.RegIds.enter("where_is_it_note") ? null : new PaperNote(
            "item.the_last_sword.where_is_it_note",
            "item_tooltip.the_last_sword.where_is_it_note",
            "note.the_last_sword.where_is_it_note.content",
            "note.the_last_sword.where_is_it_note.gui"
        )
    );

    //铁匠的痛苦 - 剧情纸条
    public static final Supplier<Item> BLACKSMITHS_PAIN_NOTE = ITEMS.register("blacksmiths_pain_note",
        () -> net.the_last_sword.mv.RegIds.enter("blacksmiths_pain_note") ? null : new PaperNote(
            "item.the_last_sword.blacksmiths_pain_note",
            "item_tooltip.the_last_sword.blacksmiths_pain_note",
            "note.the_last_sword.blacksmiths_pain_note.content",
            "note.the_last_sword.blacksmiths_pain_note.gui"
        )
    );

    public static final Supplier<Item> DRAGON_CRYSTAL = ITEMS.register("dragon_crystal",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal") ? null : new DragonCrystal()
    );

    //拜龙教号角 - 在村庄内吹响可召来拜龙教袭击
    public static final Supplier<Item> DRAGON_CULT_HORN = ITEMS.register("dragon_cult_horn",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_horn") ? null : new DragonCultHornItem()
    );

    public static final Supplier<Item> DISPOSABLE_ENERGY_BATTERY = ITEMS.register("disposable_energy_battery",
        () -> net.the_last_sword.mv.RegIds.enter("disposable_energy_battery") ? null : new DisposableEnergyBattery()
    );

    public static final Supplier<Item> ANCIENT_ENERGY_CORE = ITEMS.register("ancient_energy_core",
        () -> net.the_last_sword.mv.RegIds.enter("ancient_energy_core") ? null : new AncientEnergyCore()
    );

    public static final Supplier<Item> ZERO_POINT_ENGINE = ITEMS.register("zero_point_engine",
        () -> net.the_last_sword.mv.RegIds.enter("zero_point_engine") ? null : new ZeroPointEngine()
    );

    public static final Supplier<Item> DRAGON_CRYSTAL_UPGRADE_TEMPLATE = ITEMS.register("dragon_crystal_upgrade_template",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_upgrade_template") ? null : new DragonCrystalUpgradeTemplate()
    );

    public static final Supplier<Item> DRAGON_CRYSTAL_SMITHING_TABLE = ITEMS.register("dragon_crystal_smithing_table",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_smithing_table") ? null : new DragonCrystalSmithingTableBlockItem()
    );

    public static final Supplier<Item> DRAGON_CRYSTAL_ENCHANTING_TABLE = ITEMS.register("dragon_crystal_enchanting_table",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_enchanting_table") ? null : new net.the_last_sword.item.display.DragonCrystalEnchantingTableDisplayItem(
            ModBlocks.DRAGON_CRYSTAL_ENCHANTING_TABLE.get(),
            net.the_last_sword.mv.RegIds.stamp(new Item.Properties())
                .rarity(Rarity.UNCOMMON)
                .fireResistant()
        )
    );

    //终焉幸运方块（幸运方块本体 mod 加载时才注册, 见 registerConditionalItems）
    public static Supplier<Item> THE_LAST_END_LUCKY_BLOCK;

    public static final Supplier<Item> DRAGON_CRYSTAL_SWORD = ITEMS.register("dragon_crystal_sword",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_sword") ? null : new DragonCrystalSword()
    );

    public static final Supplier<Item> KNIGHT_GREATSWORD = ITEMS.register("knight_greatsword",
        () -> net.the_last_sword.mv.RegIds.enter("knight_greatsword") ? null : new KnightGreatswordItem()
    );

    public static final Supplier<Item> PRIEST_STAFF = ITEMS.register("priest_staff",
        () -> net.the_last_sword.mv.RegIds.enter("priest_staff") ? null : PriestStaffFactory.create()
    );

    public static final Supplier<Item> DRAGON_SWORD = ITEMS.register("dragon_sword",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_sword") ? null : new DragonSword()
    );

    public static final Supplier<Item> THE_LAST_SWORD = ITEMS.register("the_last_sword",
        () -> net.the_last_sword.mv.RegIds.enter("the_last_sword") ? null : new TheLastSword()
    );

    public static final Supplier<Item> LIGHTNING_SPEAR = ITEMS.register("lightning_spear",
        () -> net.the_last_sword.mv.RegIds.enter("lightning_spear") ? null : new LightningSpearItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties()).stacksTo(1).rarity(Rarity.UNCOMMON).fireResistant())
    );

    public static final Supplier<Item> THE_LAST_SWORD_YOU_NEVER_FORGOT = ITEMS.register("the_last_sword_you_never_forgot",
        () -> net.the_last_sword.mv.RegIds.enter("the_last_sword_you_never_forgot") ? null : new TheLastSwordYouNeverForgot()
    );

    public static final Supplier<Item> ULTRA_TEST_SWORD = ITEMS.register("ultra_test_sword",
        () -> net.the_last_sword.mv.RegIds.enter("ultra_test_sword") ? null : new UltraTestSwordItem()
    );

    //魂石
    public static final Supplier<Item> DRAGON_CRYSTAL_SOUL_STONE = ITEMS.register("dragon_crystal_soul_stone",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_soul_stone") ? null : new DragonCrystalSoulStone()
    );

    public static final Supplier<Item> SWORD_SOUL_STONE = ITEMS.register("sword_soul_stone",
        () -> net.the_last_sword.mv.RegIds.enter("sword_soul_stone") ? null : new SwordSoulStone()
    );

    //龙魂灯笼
    public static final Supplier<Item> DRAGON_SOUL_LANTERN = ITEMS.register("dragon_soul_lantern",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_soul_lantern") ? null : new DragonSoulLanternItem(ModBlocks.DRAGON_SOUL_LANTERN.get(), net.the_last_sword.mv.RegIds.stamp(new Item.Properties()).stacksTo(64).rarity(Rarity.RARE).fireResistant())
    );

    //龙水晶盔甲
    public static final Supplier<Item> DRAGON_CRYSTAL_ARMOR_HELMET = ITEMS.register("dragon_crystal_armor_helmet",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_armor_helmet") ? null : new DragonCrystalArmorItem.Helmet()
    );

    public static final Supplier<Item> DRAGON_CRYSTAL_ARMOR_CHESTPLATE = ITEMS.register("dragon_crystal_armor_chestplate",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_armor_chestplate") ? null : new DragonCrystalArmorItem.Chestplate()
    );

    public static final Supplier<Item> DRAGON_CRYSTAL_ARMOR_LEGGINGS = ITEMS.register("dragon_crystal_armor_leggings",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_armor_leggings") ? null : new DragonCrystalArmorItem.Leggings()
    );

    public static final Supplier<Item> DRAGON_CRYSTAL_ARMOR_BOOTS = ITEMS.register("dragon_crystal_armor_boots",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_crystal_armor_boots") ? null : new DragonCrystalArmorItem.Boots()
    );

    //龙之盔甲
    public static final Supplier<Item> DRAGON_ARMOR_HELMET = ITEMS.register("dragon_armor_helmet",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_armor_helmet") ? null : new DragonArmorItem.Helmet()
    );

    public static final Supplier<Item> DRAGON_ARMOR_CHESTPLATE = ITEMS.register("dragon_armor_chestplate",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_armor_chestplate") ? null : new DragonArmorItem.Chestplate()
    );

    public static final Supplier<Item> DRAGON_ARMOR_LEGGINGS = ITEMS.register("dragon_armor_leggings",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_armor_leggings") ? null : new DragonArmorItem.Leggings()
    );

    public static final Supplier<Item> DRAGON_ARMOR_BOOTS = ITEMS.register("dragon_armor_boots",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_armor_boots") ? null : new DragonArmorItem.Boots()
    );

    //拜龙教盔甲
    public static final Supplier<Item> DRAGON_CULTIST_ARMOR_HELMET = ITEMS.register("dragon_cultist_armor_helmet",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cultist_armor_helmet") ? null : new DragonCultistArmorItem.Helmet()
    );

    public static final Supplier<Item> DRAGON_CULTIST_ARMOR_CHESTPLATE = ITEMS.register("dragon_cultist_armor_chestplate",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cultist_armor_chestplate") ? null : new DragonCultistArmorItem.Chestplate()
    );

    public static final Supplier<Item> DRAGON_CULTIST_ARMOR_LEGGINGS = ITEMS.register("dragon_cultist_armor_leggings",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cultist_armor_leggings") ? null : new DragonCultistArmorItem.Leggings()
    );

    public static final Supplier<Item> DRAGON_CULTIST_ARMOR_BOOTS = ITEMS.register("dragon_cultist_armor_boots",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cultist_armor_boots") ? null : new DragonCultistArmorItem.Boots()
    );

    //拜龙教圣骑士战甲
    public static final Supplier<Item> DRAGON_CULT_PALADIN_ARMOR_HELMET = ITEMS.register("dragon_cult_paladin_armor_helmet",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_paladin_armor_helmet") ? null : new DragonCultPaladinArmorItem.Helmet()
    );

    public static final Supplier<Item> DRAGON_CULT_PALADIN_ARMOR_CHESTPLATE = ITEMS.register("dragon_cult_paladin_armor_chestplate",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_paladin_armor_chestplate") ? null : new DragonCultPaladinArmorItem.Chestplate()
    );

    public static final Supplier<Item> DRAGON_CULT_PALADIN_ARMOR_LEGGINGS = ITEMS.register("dragon_cult_paladin_armor_leggings",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_paladin_armor_leggings") ? null : new DragonCultPaladinArmorItem.Leggings()
    );

    public static final Supplier<Item> DRAGON_CULT_PALADIN_ARMOR_BOOTS = ITEMS.register("dragon_cult_paladin_armor_boots",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_paladin_armor_boots") ? null : new DragonCultPaladinArmorItem.Boots()
    );

    //拜龙教祭司法袍
    public static final Supplier<Item> DRAGON_CULT_PRIEST_ARMOR_HELMET = ITEMS.register("dragon_cult_priest_armor_helmet",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_priest_armor_helmet") ? null : new DragonCultPriestArmorItem.Helmet()
    );

    public static final Supplier<Item> DRAGON_CULT_PRIEST_ARMOR_CHESTPLATE = ITEMS.register("dragon_cult_priest_armor_chestplate",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_priest_armor_chestplate") ? null : new DragonCultPriestArmorItem.Chestplate()
    );

    public static final Supplier<Item> DRAGON_CULT_PRIEST_ARMOR_LEGGINGS = ITEMS.register("dragon_cult_priest_armor_leggings",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_priest_armor_leggings") ? null : new DragonCultPriestArmorItem.Leggings()
    );

    public static final Supplier<Item> DRAGON_CULT_PRIEST_ARMOR_BOOTS = ITEMS.register("dragon_cult_priest_armor_boots",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_priest_armor_boots") ? null : new DragonCultPriestArmorItem.Boots()
    );

    // ProjectE's items: ProjectE has no build for this Minecraft (DEPS.md), so they are never registered here
    // and stay null, exactly as on 1.21.1 without ProjectE installed.
    public static Supplier<Item> CROWN_OF_COUNTLESS_STARS;
    public static Supplier<Item> STAFF_OF_UNIVERSAL_TRANSMUTATION;
    public static Supplier<Item> ROBE_OF_THE_PRIMAL_SOURCE;
    public static Supplier<Item> LEGGINGS_OF_EARTHS_BLESSING;
    public static Supplier<Item> BOOTS_OF_THE_DEEP_ABYSS;

    //测试实体刷怪蛋
    public static final Supplier<Item> TEST_ENTITY_SPAWN_EGG = ITEMS.register("test_entity_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("test_entity_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.TEST_ENTITY.get())))
    );

    //拜龙教教徒刷怪蛋（深紫色 + 紫色）
    public static final Supplier<Item> DRAGON_CULTIST_SPAWN_EGG = ITEMS.register("dragon_cultist_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cultist_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.DRAGON_CULTIST.get())))
    );

    //拜龙教圣骑士刷怪蛋（深紫色 + 黑色）
    public static final Supplier<Item> DRAGON_CULT_PALADIN_SPAWN_EGG = ITEMS.register("dragon_cult_paladin_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_paladin_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.DRAGON_CULT_PALADIN.get())))
    );

    //拜龙教祭司刷怪蛋（深紫色 + 金色）
    public static final Supplier<Item> DRAGON_CULT_PRIEST_SPAWN_EGG = ITEMS.register("dragon_cult_priest_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("dragon_cult_priest_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.DRAGON_CULT_PRIEST.get())))
    );

    //封印尖塔守卫刷怪蛋（深灰色 + 紫色）
    public static final Supplier<Item> GUARDIAN_OF_SEALED_SPIRE_SPAWN_EGG = ITEMS.register("guardian_of_sealed_spire_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("guardian_of_sealed_spire_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.GUARDIAN_OF_SEALED_SPIRE.get())))
    );

    //守卫剑士刷怪蛋（深灰色 + 蓝色）
    public static final Supplier<Item> GUARDIAN_SABER_SPAWN_EGG = ITEMS.register("guardian_saber_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("guardian_saber_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.GUARDIAN_SABER.get())))
    );

    //守卫狂战士刷怪蛋（深灰色 + 红色）
    public static final Supplier<Item> GUARDIAN_BERSERKER_SPAWN_EGG = ITEMS.register("guardian_berserker_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("guardian_berserker_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.GUARDIAN_BERSERKER.get())))
    );

    //守卫弓箭手刷怪蛋（深灰色 + 绿色）
    public static final Supplier<Item> GUARDIAN_ARCHER_SPAWN_EGG = ITEMS.register("guardian_archer_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("guardian_archer_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.GUARDIAN_ARCHER.get())))
    );

    //迷失战魂刷怪蛋（黑色 + 深灰色）
    public static final Supplier<Item> LOST_WRAITH_SPAWN_EGG = ITEMS.register("lost_wraith_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("lost_wraith_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.LOST_WRAITH.get())))
    );

    //终焉剑灵刷怪蛋（黑色 + 白色）
    public static final Supplier<Item> THE_LAST_END_SWORD_WRAITH_SPAWN_EGG = ITEMS.register("the_last_end_sword_wraith_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("the_last_end_sword_wraith_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.THE_LAST_END_SWORD_WRAITH.get())))
    );

    //13级终焉剑灵生成蛋（自定义物品）
    public static final Supplier<Item> THE_LAST_END_SWORD_WRAITH_LEVEL_13_SPAWN_EGG = ITEMS.register("the_last_end_sword_wraith_level_13_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("the_last_end_sword_wraith_level_13_spawn_egg") ? null : new TheLastEndSwordWraithLevel13SpawnEgg()
    );

    //女皇的逝去之影刷怪蛋（紫色 + 浅紫色）
    public static final Supplier<Item> THE_PAST_SHADOW_OF_THE_QUEEN_SPAWN_EGG = ITEMS.register("the_past_shadow_of_the_queen_spawn_egg",
        () -> net.the_last_sword.mv.RegIds.enter("the_past_shadow_of_the_queen_spawn_egg") ? null : new SpawnEggItem(net.the_last_sword.mv.RegIds.stamp(new Item.Properties().spawnEgg(ModEntities.THE_PAST_SHADOW_OF_THE_QUEEN.get())))
    );

    //Curios饰品（前置mod，直接注册）
    public static final Supplier<Item> DRAGON_CRYSTAL_RING = CuriosItemsRegistry.registerDragonCrystalRing(ITEMS);
    public static final Supplier<Item> DRAGON_CRYSTAL_NECKLACE = CuriosItemsRegistry.registerDragonCrystalNecklace(ITEMS);
    public static final Supplier<Item> DRAGON_CRYSTAL_CROWN = CuriosItemsRegistry.registerDragonCrystalCrown(ITEMS);
    public static final Supplier<Item> WINGS_THAT_COVER_THE_WORLD = CuriosItemsRegistry.registerWingsThatCoverTheWorld(ITEMS);
    public static final Supplier<Item> EXTREME_LIFE_SUPPORT_DEVICE = CuriosItemsRegistry.registerExtremeLifeSupportDevice(ITEMS);
    public static final Supplier<Item> DIMENSION_EXPLORER = CuriosItemsRegistry.registerDimensionExplorer(ITEMS);
    public static final Supplier<Item> THE_GIVERS_PAIN = CuriosItemsRegistry.registerTheGiversPain(ITEMS);

    //Cataclysm联动物品（条件注册）
    public static Supplier<Item> ANCIENT_REMNANT_MEDAL;
    public static Supplier<Item> ENDER_GUARDIAN_MEDAL;
    public static Supplier<Item> IGNIS_MEDAL;
    public static Supplier<Item> MALEDICTUS_MEDAL;
    public static Supplier<Item> NETHERITE_MONSTROSITY_MEDAL;
    public static Supplier<Item> THE_HARBINGER_MEDAL;
    public static Supplier<Item> THE_LEVIATHAN_MEDAL;
    public static Supplier<Item> SCYLLA_MEDAL;

    public static void register(IEventBus eventBus) {
        //注册条件物品
        registerConditionalItems();
        //注册到事件总线
        ITEMS.register(eventBus);
    }

    //注册联动物品（直接调用，Java懒加载保证不会在未加载时执行）
    private static void registerConditionalItems() {
        //灾变奖章
        if (CompatCheck.isCataclysmLoaded()) {
            ANCIENT_REMNANT_MEDAL = CataclysmItemsRegistry.registerAncientRemnantMedal(ITEMS);
            ENDER_GUARDIAN_MEDAL = CataclysmItemsRegistry.registerEnderGuardianMedal(ITEMS);
            IGNIS_MEDAL = CataclysmItemsRegistry.registerIgnisMedal(ITEMS);
            MALEDICTUS_MEDAL = CataclysmItemsRegistry.registerMaledictusMedal(ITEMS);
            NETHERITE_MONSTROSITY_MEDAL = CataclysmItemsRegistry.registerNetheriteMonstrosityMedal(ITEMS);
            THE_HARBINGER_MEDAL = CataclysmItemsRegistry.registerTheHarbingerMedal(ITEMS);
            THE_LEVIATHAN_MEDAL = CataclysmItemsRegistry.registerTheLeviathanMedal(ITEMS);
            SCYLLA_MEDAL = CataclysmItemsRegistry.registerScyllaMedal(ITEMS);
        }
        //幸运方块联动
        if (CompatCheck.isLuckyBlockLoaded()) {
            THE_LAST_END_LUCKY_BLOCK = ITEMS.register("the_last_end_lucky_block",
                () -> net.the_last_sword.mv.RegIds.enter("the_last_end_lucky_block") ? null : new TheLastEndLuckyBlockDisplayItem(
                    ModBlocks.THE_LAST_END_LUCKY_BLOCK.get(),
                    net.the_last_sword.mv.RegIds.stamp(new Item.Properties())
                        .rarity(Rarity.RARE)
                )
            );
        }
    }
}
