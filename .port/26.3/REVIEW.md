# Review: Minecraft 26.3 (version branch neoforge-26.3)

An independent review by a FRESH agent that was given only this version's changes: every file that differs
from the parent branch (neoforge-26.2) at their merge-base (`git diff <merge-base>..neoforge-26.3`), read against the parent's
file, its STUBS.json and .port/26.3/renames.tsv. One row per STUBS.json entry, per `changed` file, per
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
| changed | src/main/java/net/the_last_sword/block/DragonSoulLanternBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/block/entity/DragonCrystalEnchantingTableBlockEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/block/entity/DragonCrystalSmithingTableBlockEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/ClientPacketHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/ClientScreenHelper.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/ClientTooltipHelper.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonArmorEnergyDisplayPositionScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/JustifiedDefenceOverlayPositionScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/NpcDialogueScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/SummonWraithGuiScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/TheLastEndScrollScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/menu/DragonCrystalEnchantingTableMenu.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/menu/SummonWraithGuiMenu.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/LostWraithSealLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TestEntityTheLastEndLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/EndCrystalRiftRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/GroundRuptureFragmentRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/PhasingBufferSource.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/QueenBlinkBladeRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/WingsThatCoverTheWorldRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/shader/TheLastEndRenderTypes.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/curios/ExtremeLifeSupportDevice.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockDisplayItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/ArenaBattleHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/EndResetSkyEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/LuckyWellEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/WoundOfTimeLaunchEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/dialogue/NpcDialogueManager.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/GuardianOfSealedSpireEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/GuardianSaberEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/GuardianPickupWeaponGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/ClientEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/DefenceEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/EnderDragonEvent.java | ? | ? |
| changed | src/main/java/net/the_last_sword/init/ModKeyMappings.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/TheLastEndSwordItems.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/EndCrystalMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/InventoryMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/LivingEntityMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/ScreenEffectRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/network/OpenLastEndScrollPacket.java | ? | ? |
| changed | src/main/java/net/the_last_sword/raid/DragonCultRaid.java | ? | ? |
| changed | src/main/java/net/the_last_sword/util/EntityUtil.java | ? | ? |
| renames | .port/26.3/renames.tsv @66b971929bdb | ? | ? |
| resources | src/main/resources/assets/minecraft/atlases @e66ec7b7f42f | ? | ? |
| resources | src/main/resources/assets/the_last_sword/textures @7489737e10a3 | ? | ? |
| resources | src/main/resources/data/the_last_sword/advancement @5cecdb4778ac | ? | ? |
| resources | src/main/resources/data/the_last_sword/loot_table @1913eb15a078 | ? | ? |
| resources | src/main/resources/data/the_last_sword/trim_material @b8d03ae6c7ee | ? | ? |
| resources | src/main/resources/data/the_last_sword/worldgen @bc661598e5d6 | ? | ? |
| stub | src/main/java/net/the_last_sword/block/DragonCrystalEnchantingTableBlock.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/QueenBlinkBladeRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordProjectileRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlock.java | ? | ? |
| stub | src/main/java/net/the_last_sword/compat/lucky_block/lucky_event/events/ArenaEvent.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/GuardianSaberEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/event/EnderDragonEvent.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/TheLastEndSwordItems.java | ? | ? |
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
| item | main:net/the_last_sword/client/renderer/LightningSpearItemRenderer.java#render-class:class LightningSpearItemRenderer : BlockEntityWithoutLevelRenderer | ? | ? |
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
| item | main:net/the_last_sword/mixin/GameRendererMixin.java#injection:@Inject method=renderConfusionOverlay @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LevelRendererMixin.java#injection:@ModifyVariable method=setupRender @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LevelRendererMixin.java#mixin:@Mixin LevelRenderer | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityMixin.java#injection:@Inject method=hurt @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/MinecraftMixin.java#mixin:@Mixin Minecraft | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity; @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=setItemSlot @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/ScreenEffectRendererMixin.java#injection:@Inject method=renderScreenEffect @At(value="HEAD") | ? | ? |
