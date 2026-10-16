# Review: Minecraft 1.21.5

An independent review by a FRESH agent that was given only this target's files (STUBS.json, its overlays,
the authors' files they replace, its resource overlays and renames.tsv). One row per STUBS.json entry,
per whole-file overlay, per resource group (`resources`: a directory of the target's resources/, with the
digest of its files -- an edit makes it a new row) and for renames.tsv (`renames`, with its digest);
verdict `ok` (the change keeps the authors' behaviour, or the difference is right and the note says why)
or `not-ok` (it does not: the target is not done). A hook item of the behaviour inventory (a mixin target,
an injection, an event listener, a shader uniform/sampler) is accounted for only by a row whose note names
its id, or by an `item` row for the id. `reviewer` names the reviewing agent, distinct from the fixer
(e.g. `fresh agent <id>, not the fixer`). `commit` is the commit reviewed, a hex id of 7-40 digits
(`git rev-parse --short HEAD`). tools/fork-port.py adds a `?` row for every item it finds unreviewed
(an `item` row for every unaccounted inventory item); it never changes a verdict.

- reviewer: ?
- date: ?
- commit: ?

| kind | file | verdict | note |
|---|---|---|---|
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/ItemNbt.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/block/DragonCrystalEnchantingTableBlock.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/block/DragonCrystalSmithingTableBlock.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/block/entity/DragonCrystalEnchantingTableBlockEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/DragonCultArmorClientExtensions.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/gui/SummonWraithGuiScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/gui/scroll/ScrollRecipeRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/layer/DragonCultistItemLayer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/layer/LostWraithSealLayer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/layer/TheLastEndSwordWraithLayer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/layer/TheLastEndSwordWraithTheLastEndLayer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/layer/ThePastShadowOfTheQueenItemLayer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonArmorModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCrystalEnchantingTableDisplayModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCrystalEnchantingTableModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCultPaladinArmorModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCultPaladinModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCultPriestArmorModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCultPriestModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCultistArmorModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/DragonCultistModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/GuardianOfSealedSpireModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/LostWraithModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/TheLastEndSwordWraithModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/overlay/JustifiedDefenceOverlay.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonArmorRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonCrystalEnchantingTableDisplayItemRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonCultPaladinArmorRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonCultPaladinRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonCultPriestArmorRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonCultistArmorRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonCultistRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/DragonShieldRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/LightningSpearRenderUtil.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/LostWraithRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/TheLastEndSwordWraithRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/shader/TheLastEndRenderTypes.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/client/shader/ZeroPointEngineShader.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/cataclysm/CataclysmEventHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/cataclysm/CataclysmMedals.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/CuriosEffectHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/DimensionExplorer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/DragonCrystalCrown.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/DragonCrystalNecklace.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/DragonCrystalRing.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/ExtremeLifeSupportDevice.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/TheGiversPain.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/WingsThatCoverTheWorld.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/LuckModifierCraftingRecipe.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItemRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockModel.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/lucky_event/ArenaBattleHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/lucky_event/events/TheLastSwordChosenEvent.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/lucky_event/events/VoidBountyEvent.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/dialogue/NpcDialogueManager.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/effect/PhasingEffect.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/DragonCultPaladinEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/DragonCultPriestEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/GroundRuptureFragmentEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/GuardianOfSealedSpireEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/GuardianSaberEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/LightningSpearProjectile.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/QueenBlinkBlade.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/TheLastEndEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/ai/DragonCultPriestLightningGoal.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/ai/GuardianPickupWeaponGoal.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/ai/LostWraithEndStrikeGoal.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/ai/LostWraithPunchGoal.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/event/ClientEventHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/event/DefenceEventHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/event/ServerEventHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/event/TheLastSwordQuestHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/event/WraithSummonEventHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/init/ModShaders.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/AncientEnergyCore.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DisposableEnergyBattery.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCrystal.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCrystalArmorItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCrystalSmithingTableBlockItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCrystalSoulStone.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCrystalSword.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCrystalUpgradeTemplate.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCultHornItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCultPaladinArmorItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCultPriestArmorItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCultistArmorItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonSoulLanternItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonSword.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/KnightGreatswordItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/LightningSpearItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/PaperNote.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/PriestStaffItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/SwordSoulStone.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/TheLastEndArmorItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/TheLastEndScroll.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/TheLastEndSwordItems.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/TheLastEndSwordWraithLevel13SpawnEgg.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/TheLastSword.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/TheLastSwordYouNeverForgot.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/ZeroPointEngine.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/item/display/DragonCrystalEnchantingTableDisplayItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/mixin/EndCrystalMixin.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/mixin/LivingEntityMixin.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/mixin/PlayerMixin.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/mixin/SmithingTransformRecipeAccessor.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/network/ChangeModePacket.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/network/SyncSummonGuiPacket.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/summon/WraithSummonSavedData.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/test/UltraTestSwordItem.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/util/EntityUtil.java | ? | ? |
| overlay | .multiversion/targets/1.21.5/java/net/the_last_sword/util/damage/AdvancedEquipmentDamageHandler.java | ? | ? |
| renames | .multiversion/targets/1.21.5/renames.tsv @f779be91749b | ? | ? |
| resources | .multiversion/targets/1.21.5/resources/META-INF/accesstransformer.cfg @da1532e0fcc2 | ? | ? |
| resources | .multiversion/targets/1.21.5/resources/assets/the_last_sword/geckolib @5483cc454fde | ? | ? |
| resources | .multiversion/targets/1.21.5/resources/assets/the_last_sword/items @898dfe071c13 | ? | ? |
| resources | .multiversion/targets/1.21.5/resources/assets/the_last_sword/models @cabccc755d37 | ? | ? |
| resources | .multiversion/targets/1.21.5/resources/assets/the_last_sword/textures @69325450eeb5 | ? | ? |
| resources | .multiversion/targets/1.21.5/resources/data/the_last_sword/recipe @cb8439c95bd5 | ? | ? |
| resources | .multiversion/targets/1.21.5/resources/data/the_last_sword/trim_material @4fdbeb46ab1e | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/block/DragonCrystalEnchantingTableBlock.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/curios/WingsThatCoverTheWorld.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/LuckModifierCraftingRecipe.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/compat/lucky_block/lucky_event/events/ArenaEvent.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/GuardianSaberEntity.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/QueenBlinkBlade.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/init/ModEntityRenderers.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/init/ModShaders.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCrystalArmorItem.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCultPaladinArmorItem.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCultPriestArmorItem.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/item/DragonCultistArmorItem.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/item/LightningSpearItem.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/item/TheLastEndSwordItems.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/mixin/LivingEntityMixin.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/mixin/PlayerMixin.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/summon/WraithSummonSavedData.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/test/TestEntity.java | ? | ? |
| stub | .multiversion/targets/1.21.5/java/net/the_last_sword/test/UltraTestSwordItem.java | ? | ? |
| item | main:net/the_last_sword/client/DragonCrystalEnchantingTableClientExtensions.java#render-class:class DragonCrystalEnchantingTableClientExtensions : IClientItemExtensions | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.COLOR_DEPTH_WRITE | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/layer/TestEntityTheLastEndLayer.java#render-use:RenderType.entityTranslucentCull | ? | ? |
| item | main:net/the_last_sword/client/model/WingsThatCoverTheWorldModel.java#render-class:class WingsThatCoverTheWorldModel : AgeableListModel | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:GameRenderer.getPositionColorShader | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearItemRenderer.java#render-class:class LightningSpearItemRenderer : BlockEntityWithoutLevelRenderer | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.LIGHTNING_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.LIGHTNING_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:GameRenderer.getParticleShader | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.ADDITIVE_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.BLOCK_SHEET | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.BLOCK_SHEET_MIPPED | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.VIEW_OFFSET_Z_LAYERING | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItemRenderer.java#render-use:RenderType.entityTranslucentCull | ? | ? |
| item | main:net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockRenderer.java#render-use:RenderType.entityTranslucentCull | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/init/ModInstruments.java#registry:registry INSTRUMENT | ? | ? |
| item | main:net/the_last_sword/mixin/EnderDragonRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/EnderDragonRendererMixin.java#injection:@ModifyArg method=render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="INVOKE",target="Lnet/minecraft/client/renderer/texture/OverlayTexture;pack(FZ)I") | ? | ? |
| item | main:net/the_last_sword/mixin/EntityRenderDispatcherMixin.java#injection:@ModifyVariable method=render @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/GameRendererMixin.java#injection:@Inject method=renderConfusionOverlay @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
