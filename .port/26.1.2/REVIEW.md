# Review: Minecraft 26.1.2 (version branch neoforge-26.1.2)

An independent review by a FRESH agent that was given only this version's changes: every file that differs
from the parent branch (neoforge-1.21.11) at their merge-base (`git diff <merge-base>..neoforge-26.1.2`), read against the parent's
file, its STUBS.json and .port/26.1.2/renames.tsv. One row per STUBS.json entry, per `changed` file, per
resource group (`resources`: a directory with the digest of its changed files -- an edit makes it a new
row) and for renames.tsv (`renames`, with its digest); the files the rename rows alone made, the build
settings tools/fork-branches.py derives and added helper classes need no row; a file deleted here is a `deleted`
row unless drops.txt or a mixin_remove key accounts for it. Verdict `ok` (the change keeps
the behaviour, or the difference is right and the note says why) or `not-ok`. A hook item of the behaviour
inventory is accounted for only by a row whose note names its id, or by an `item` row for the id; a
reviewer may add `missing` (a file that should exist) and `shared` (a shared helper) rows. `reviewer` names
the reviewing agent, distinct from the fixer; `commit` is the commit reviewed (`git rev-parse --short HEAD`).
tools/fork-port.py adds a `?` row for every item it finds unreviewed; it never changes a verdict.

- reviewer: ?
- date: ?
- commit: ?

| kind | file | verdict | note |
|---|---|---|---|
| changed | src/main/java/net/the_last_sword/TheLastSwordMod.java | ? | ? |
| changed | src/main/java/net/the_last_sword/block/DragonCrystalEnchantingTableBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/block/DragonCrystalSmithingTableBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/block/DragonSoulLanternBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/block/entity/DragonCrystalEnchantingTableBlockEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/DragonCrystalEnchantingTableClientExtensions.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/DragonCultArmorClientExtensions.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/ArmorConfigScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/ArmorPieceConfigScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DefenceConfigScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonArmorConfigScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonArmorDefenceModuleScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonArmorEnergyDisplayPositionScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonArmorModuleConfigScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonArmorModuleDetailScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonCrystalArmorConfigScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonCrystalSmithingTableScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/JourneyLogsScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/JustifiedDefenceConfigScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/JustifiedDefenceOverlayPositionScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/NpcDialogueScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/PaperNoteScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/SummonWraithGuiScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/SwordWraithAppearanceScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/TheLastEndScrollScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/scroll/RecipeSlotElement.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/scroll/ScrollRecipeRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/DragonCultistItemLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/LostWraithSealLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TheLastEndSwordWraithLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TheLastEndSwordWraithTheLastEndLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/ThePastShadowOfTheQueenItemLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonArmorModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCrystalEnchantingTableDisplayModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCrystalEnchantingTableModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultPaladinArmorModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultPaladinModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultPriestArmorModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultPriestModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultistArmorModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultistModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/GuardianOfSealedSpireModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/LostWraithModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/TheLastEndSwordWraithModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/overlay/DragonArmorOverlay.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/overlay/JustifiedDefenceOverlay.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/overlay/PresentWorldAnchorHealthOverlay.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonArmorRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCrystalEnchantingTableDisplayItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCrystalEnchantingTableRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPaladinArmorRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPaladinRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPriestArmorRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultistArmorRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultistRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/GroundRuptureFragmentRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LostWraithRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/PhasingBufferSource.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordWraithRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/shader/TheLastEndRenderTypes.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/cataclysm/CataclysmMedals.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/jade/HealBanElement.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/jade/JustifiedDefenceElement.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/jei/DragonCrystalSmithingCategory.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/LuckModifierCraftingRecipe.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/LuckyWellEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/TrapEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/GuardianSaberEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/TheLastEndEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/DangerousSkillGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/LostWraithEndStrikeGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/LostWraithPunchGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/EnderDragonEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/ServerEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/TheLastSwordQuestHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/VillagerTradeHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/init/ModEntityRenderers.java | ? | ? |
| changed | src/main/java/net/the_last_sword/init/ModRecipes.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/AncientEnergyCore.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCrystalUpgradeTemplate.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCultHornItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCultPaladinArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCultPriestArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCultistArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/TheLastEndSwordItems.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/ZeroPointEngine.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/display/DragonCrystalEnchantingTableDisplayItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/CameraMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/EnderDragonRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/GameRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/LivingEntityRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/ScreenEffectRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/network/ChangeModePacket.java | ? | ? |
| changed | src/main/java/net/the_last_sword/recipe/DragonCrystalSmithingRecipe.java | ? | ? |
| changed | src/main/java/net/the_last_sword/recipe/DragonCrystalSmithingSerializer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| changed | src/main/java/net/the_last_sword/summon/WraithSummonSavedData.java | ? | ? |
| changed | src/main/java/net/the_last_sword/test/TestEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/test/TestUtil.java | ? | ? |
| changed | src/main/java/net/the_last_sword/util/EntityUtil.java | ? | ? |
| renames | .port/26.1.2/renames.tsv @f8a199540567 | ? | ? |
| resources | src/main/resources/data/minecraft/tags @9aa135e4c1d0 | ? | ? |
| resources | src/main/resources/data/the_last_sword/villager_trade @d6dc22ec8df7 | ? | ? |
| stub | src/main/java/net/the_last_sword/block/DragonSoulLanternBlock.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/DragonCrystalEnchantingTableClientExtensions.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/layer/DragonCultistItemLayer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/layer/ThePastShadowOfTheQueenItemLayer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/DragonCultPaladinModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/DragonCultPriestModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/DragonCultistModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/GuardianOfSealedSpireModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/LostWraithModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/TheLastEndSwordWraithModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/QueenBlinkBladeRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordProjectileRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/compat/lucky_block/LuckModifierCraftingRecipe.java | ? | ? |
| stub | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlock.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/GuardianSaberEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/event/EnderDragonEvent.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonCultPaladinArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonCultPriestArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonCultistArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/TheLastEndSwordItems.java | ? | ? |
| stub | src/main/java/net/the_last_sword/mixin/GameRendererMixin.java | ? | ? |
| stub | src/main/java/net/the_last_sword/recipe/DragonCrystalSmithingRecipe.java | ? | ? |
| stub | src/main/java/net/the_last_sword/recipe/DragonCrystalSmithingSerializer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/summon/WraithSummonSavedData.java | ? | ? |
| stub | src/main/java/net/the_last_sword/test/TestEntity.java | ? | ? |
| item | main:net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java#render-use:RenderSystem.setShaderColor | ? | ? |
| item | main:net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java#render-use:RenderType.endPortal | ? | ? |
| item | main:net/the_last_sword/client/gui/DragonCrystalSmithingTableScreen.java#render-use:RenderSystem.setShaderColor | ? | ? |
| item | main:net/the_last_sword/client/gui/JourneyLogsScreen.java#render-use:RenderSystem.setShaderColor | ? | ? |
| item | main:net/the_last_sword/client/gui/PaperNoteScreen.java#render-use:RenderSystem.setShaderColor | ? | ? |
| item | main:net/the_last_sword/client/gui/SummonWraithGuiScreen.java#render-use:RenderSystem.setShaderColor | ? | ? |
| item | main:net/the_last_sword/client/gui/TheLastEndScrollScreen.java#render-use:RenderSystem.setShaderColor | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.COLOR_DEPTH_WRITE | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/layer/LostWraithSealLayer.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/layer/TestEntityTheLastEndLayer.java#render-use:RenderType.entityTranslucentCull | ? | ? |
| item | main:net/the_last_sword/client/layer/TheLastEndSwordWraithLayer.java#render-use:RenderType.eyes | ? | ? |
| item | main:net/the_last_sword/client/model/WingsThatCoverTheWorldModel.java#render-class:class WingsThatCoverTheWorldModel : AgeableListModel | ? | ? |
| item | main:net/the_last_sword/client/overlay/DragonArmorOverlay.java#render-use:GameRenderer.getPositionTexShader | ? | ? |
| item | main:net/the_last_sword/client/overlay/DragonArmorOverlay.java#render-use:RenderSystem.setShader | ? | ? |
| item | main:net/the_last_sword/client/overlay/DragonArmorOverlay.java#render-use:RenderSystem.setShaderColor | ? | ? |
| item | main:net/the_last_sword/client/renderer/BlinkRiftRenderer.java#event:@SubscribeEvent RenderLevelStageEvent | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:GameRenderer.getPositionColorShader | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java#render-use:RenderType.lines | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonArmorRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonCrystalEnchantingTableDisplayItemRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonCultPaladinRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonCultPriestRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonCultistRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonLightingRenderer.java#render-use:RenderType.lightning | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearBurstRenderer.java#event:@SubscribeEvent RenderLevelStageEvent | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.LIGHTNING_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/renderer/LostWraithEndStrikeEffectRenderer.java#render-use:RenderType.lightning | ? | ? |
| item | main:net/the_last_sword/client/renderer/LostWraithRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.CompositeRenderType | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.armorCutoutNoCull | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entityCutout | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entityCutoutNoCull | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entityCutoutNoCullZOffset | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entityDecal | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entitySmoothCutout | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entitySolid | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.entityTranslucentEmissive | ? | ? |
| item | main:net/the_last_sword/client/renderer/PhasingBufferSource.java#render-use:RenderType.eyes | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.LIGHTNING_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/TheLastEndLightingRenderer.java#render-use:RenderType.endPortal | ? | ? |
| item | main:net/the_last_sword/client/renderer/TheLastEndSwordWraithRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java#render-use:RenderType.entityTranslucent | ? | ? |
| item | main:net/the_last_sword/client/renderer/WingsThatCoverTheWorldRenderer.java#render-use:RenderType.entityCutoutNoCull | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:GameRenderer.getParticleShader | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.ADDITIVE_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.BLOCK_SHEET | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.BLOCK_SHEET_MIPPED | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.LIGHTMAP | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.OVERLAY | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.TextureStateShard | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderStateShard.VIEW_OFFSET_Z_LAYERING | ? | ? |
| item | main:net/the_last_sword/client/shader/TheLastEndRenderTypes.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.BLOCK_SHEET_MIPPED | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderStateShard.VIEW_OFFSET_Z_LAYERING | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/shader/ZeroPointEngineShader.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItemRenderer.java#render-use:RenderType.entityTranslucentCull | ? | ? |
| item | main:net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockRenderer.java#render-use:RenderType.entityTranslucentCull | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#event:@SubscribeEvent RenderLevelStageEvent | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.ShaderStateShard | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderStateShard.TextureStateShard | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/event/ClientEventHandler.java#render-use:RenderType.lines | ? | ? |
| item | main:net/the_last_sword/event/ServerEventHandler.java#event:@SubscribeEvent BlockEvent.BreakEvent | ? | ? |
| item | main:net/the_last_sword/event/VillagerTradeHandler.java#event:@SubscribeEvent WandererTradesEvent | ? | ? |
| item | main:net/the_last_sword/init/ModInstruments.java#registry:registry INSTRUMENT | ? | ? |
| item | main:net/the_last_sword/mixin/CameraMixin.java#injection:@Inject method=setup @At(value="TAIL") | ? | ? |
| item | main:net/the_last_sword/mixin/EnderDragonRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/EnderDragonRendererMixin.java#injection:@ModifyArg method=render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="INVOKE",target="Lnet/minecraft/client/renderer/texture/OverlayTexture;pack(FZ)I") | ? | ? |
| item | main:net/the_last_sword/mixin/EntityRenderDispatcherMixin.java#injection:@ModifyVariable method=render @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LevelRendererMixin.java#injection:@ModifyVariable method=setupRender @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityMixin.java#injection:@Inject method=hurt @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity; @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=setItemSlot @At(value="HEAD") | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordProjectileRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/WingsThatCoverTheWorldRenderer.java | ? | ? |
| deleted | src/main/resources/assets/minecraft/atlases/blocks.json | ? | ? |
| resources | src/main/resources/assets/minecraft/atlases @6e0de28b83e0 | ? | ? |
