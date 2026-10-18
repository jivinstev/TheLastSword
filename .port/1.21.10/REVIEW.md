# Review: Minecraft 1.21.10 (version branch neoforge-1.21.10)

An independent review by a FRESH agent that was given only this version's changes: every file that differs
from the parent branch (neoforge-1.21.8) at their merge-base (`git diff <merge-base>..neoforge-1.21.10`), read against the parent's
file, its STUBS.json and .port/1.21.10/renames.tsv. One row per STUBS.json entry, per `changed` file, per
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
| changed | src/main/java/net/the_last_sword/block/DragonCrystalSmithingTableBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/ClientScreenHelper.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/ClientTooltipHelper.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/DragonCultArmorClientExtensions.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonArmorEnergyDisplayPositionScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/JourneyLogsScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/JustifiedDefenceOverlayPositionScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/NpcDialogueScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/SummonWraithGuiScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/SwordWraithAppearanceScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/TheLastEndScrollScreen.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/menu/DragonCrystalEnchantingTableMenu.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/gui/menu/DragonCrystalSmithingTableMenu.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/DragonCultistItemLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/LostWraithSealLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TestEntityTheLastEndLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TheLastEndSwordWraithLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/TheLastEndSwordWraithTheLastEndLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/layer/ThePastShadowOfTheQueenItemLayer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/BlinkRiftRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonArmorRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCrystalEnchantingTableRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPaladinRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonCultistRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/DragonLightingRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/EndCrystalRiftRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/GroundRuptureFragmentRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LightningSpearBurstRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LightningSpearProjectileRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/LostWraithRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/PhasingBufferSource.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/QueenBlinkBladeRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/QueenBlinkRenderUtil.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TestEntityRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndLightingRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordProjectileRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordWraithRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/curios/CuriosEffectHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/curios/ExtremeLifeSupportDevice.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlock.java | ? | ? |
| changed | src/main/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockRenderer.java | ? | ? |
| changed | src/main/java/net/the_last_sword/effect/VoidEnchantingEffect.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/DragonCultPriestEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/LightningSpearProjectile.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/LostWraithEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/QueenBlinkBlade.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/TheLastEndEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/DragonCultPriestBreathGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/SwordWraithBlockGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/ThePastShadowOfTheQueenEnchantGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/entity/ai/ThePastShadowOfTheQueenTripleSlashGoal.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/ClientEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/DefenceEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/EffectEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/ServerEventHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/event/TheLastSwordQuestHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/init/ModCapabilities.java | ? | ? |
| changed | src/main/java/net/the_last_sword/init/ModItems.java | ? | ? |
| changed | src/main/java/net/the_last_sword/init/ModKeyMappings.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DisposableEnergyBattery.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonCrystalSword.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/DragonSword.java | ? | ? |
| changed | src/main/java/net/the_last_sword/item/TheLastSword.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/EndCrystalMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/EnderDragonRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/EntityRenderDispatcherMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/LevelRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/LivingEntityRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/mixin/ScreenEffectRendererMixin.java | ? | ? |
| changed | src/main/java/net/the_last_sword/network/SetWraithAppearancePacket.java | ? | ? |
| changed | src/main/java/net/the_last_sword/summon/WraithFaction.java | ? | ? |
| changed | src/main/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| changed | src/main/java/net/the_last_sword/test/TestEntity.java | ? | ? |
| changed | src/main/java/net/the_last_sword/test/UltraTestSwordItem.java | ? | ? |
| changed | src/main/java/net/the_last_sword/util/ParticleUtil.java | ? | ? |
| changed | src/main/java/net/the_last_sword/util/damage/AdvancedEquipmentDamageHandler.java | ? | ? |
| changed | src/main/java/net/the_last_sword/worldgen/TravelersHoldStructure.java | ? | ? |
| deleted | src/main/java/net/the_last_sword/mv/GeoInventoryEntity.java | ? | ? |
| renames | .port/1.21.10/renames.tsv @26838a546af3 | ? | ? |
| resources | src/main/resources/assets/the_last_sword/geckolib @3ebfa9f7e9c0 | ? | ? |
| resources | src/main/resources/assets/the_last_sword/items @fafa0a678a3a | ? | ? |
| stub | src/main/java/net/the_last_sword/client/layer/DragonCultistItemLayer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/layer/GuardianItemLayer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/QueenBlinkBladeRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/TheLastEndSwordProjectileRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/QueenBlinkBlade.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/DragonArmorItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/item/LightningSpearItem.java | ? | ? |
| stub | src/main/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| stub | src/main/java/net/the_last_sword/test/TestEntity.java | ? | ? |
| stub | src/main/java/net/the_last_sword/test/UltraTestSwordItem.java | ? | ? |
| item | main:net/the_last_sword/client/DragonCrystalEnchantingTableClientExtensions.java#render-class:class DragonCrystalEnchantingTableClientExtensions : IClientItemExtensions | ? | ? |
| item | main:net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java#render-use:RenderSystem.setShaderColor | ? | ? |
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
| item | main:net/the_last_sword/client/layer/TestEntityTheLastEndLayer.java#render-use:RenderType.entityTranslucentCull | ? | ? |
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
| item | main:net/the_last_sword/event/ClientEventHandler.java#event:@SubscribeEvent RenderLevelStageEvent | ? | ? |
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
| item | main:net/the_last_sword/mixin/LevelRendererMixin.java#injection:@ModifyVariable method=setupRender @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityMixin.java#injection:@Inject method=hurt @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=drop(Lnet/minecraft/world/item/ItemStack;ZZ)Lnet/minecraft/world/entity/item/ItemEntity; @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/PlayerMixin.java#injection:@Inject method=setItemSlot @At(value="HEAD") | ? | ? |
