# Review: Minecraft 1.21.11 (version branch neoforge-1.21.11)

An independent review by a FRESH agent that was given only this version's changes: every file that differs
from the parent branch (neoforge-1.21.10) at their merge-base (`git diff <merge-base>..neoforge-1.21.11`), read against the parent's
file, its STUBS.json and .port/1.21.11/renames.tsv. One row per STUBS.json entry, per `changed` file, per
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
| changed | src/main/java/net/the_last_sword/block/entity/DragonCrystalEnchantingTableBlockEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/TheLastEndScrollScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/LostWraithSealLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TestEntityTheLastEndLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TheLastEndSwordWraithLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TheLastEndSwordWraithTheLastEndLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultPaladinModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultPriestModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/DragonCultistModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/GuardianOfSealedSpireModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/LostWraithModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/TheLastEndSwordWraithModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DangerousSkillPreviewRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonArmorRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCrystalEnchantingTableDisplayItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPaladinRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultistRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonLightingRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonShieldRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LightningSpearRenderUtil.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LostWraithEndStrikeEffectRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LostWraithRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/PhasingBufferSource.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndLightingRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordProjectileRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordWraithRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/WingsThatCoverTheWorldRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/shader/TheLastEndRenderTypes.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/shader/ZeroPointEngineShader.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/jei/DragonCrystalSmithingCategory.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/LuckyWellEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/TheLastEndEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/ClientEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/DefenceEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCrystalUpgradeTemplate.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCultPaladinArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCultPriestArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/display/DragonCrystalEnchantingTableDisplayItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/CameraMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/LevelRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/ServerPlayerMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/test/UltraTestSwordItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/util/EntityUtil.java | ? | ? |
| renames | .port/1.21.11/renames.tsv @a3ed5545182c | ? | ? |
| resources | src/main/resources/META-INF/accesstransformer.cfg @a58c46ff61c0 | ? | ? |
| stub | src/main/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/DragonCultPaladinModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/DragonCultPriestModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/DragonCultistModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/GuardianOfSealedSpireModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/LostWraithModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/TheLastEndSwordWraithModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/model/WingsThatCoverTheWorldModel.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/DragonCultPaladinRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/DragonCultistRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/LostWraithRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/QueenBlinkBladeRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordProjectileRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordWraithRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/ArenaEvent.java | ? | ? |
| stub | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/LuckyWellEvent.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/init/ModInstruments.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonCrystalArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonCultPaladinArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonCultPriestArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonCultistArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/LightningSpearItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/recipe/DragonCrystalSmithingRecipe.java | ? | ? |
| stub | src/main/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| stub | src/main/java/net/the_last_sword/summon/WraithSummonSavedData.java | ? | ? |
| stub | src/main/java/net/the_last_sword/test/TestEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/test/UltraTestSwordItem.java | ? | ? |
| item | main:net/the_last_sword/client/DragonCrystalEnchantingTableClientExtensions.java#render-class:class DragonCrystalEnchantingTableClientExtensions : IClientItemExtensions | ? | ? |
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
| item | main:net/the_last_sword/client/renderer/DragonLightingRenderer.java#render-use:RenderType.lightning | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderStateShard.TRANSLUCENT_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/DragonShieldRenderer.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearBurstRenderer.java#event:@SubscribeEvent RenderLevelStageEvent | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.COLOR_WRITE | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.LEQUAL_DEPTH_TEST | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.LIGHTNING_TRANSPARENCY | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.NO_CULL | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderStateShard.POSITION_COLOR_SHADER | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderType.CompositeState | ? | ? |
| item | main:net/the_last_sword/client/renderer/LightningSpearRenderUtil.java#render-use:RenderType.create | ? | ? |
| item | main:net/the_last_sword/client/renderer/LostWraithEndStrikeEffectRenderer.java#render-use:RenderType.lightning | ? | ? |
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
| item | main:net/the_last_sword/mixin/EnderDragonRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/EnderDragonRendererMixin.java#injection:@ModifyArg method=render(Lnet/minecraft/world/entity/boss/enderdragon/EnderDragon;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="INVOKE",target="Lnet/minecraft/client/renderer/texture/OverlayTexture;pack(FZ)I") | ? | ? |
| item | main:net/the_last_sword/mixin/EntityRenderDispatcherMixin.java#injection:@ModifyVariable method=render @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/GameRendererMixin.java#injection:@Inject method=renderConfusionOverlay @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LevelRendererMixin.java#injection:@ModifyVariable method=setupRender @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityMixin.java#injection:@Inject method=hurt @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity; @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=setItemSlot @At(value="HEAD") | ? | ? |
