# Review: Minecraft 1.21.8

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
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/block/entity/DragonCrystalEnchantingTableBlockEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/block/entity/DragonCrystalSmithingTableBlockEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/DragonCrystalSmithingTableScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/JourneyLogsScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/NpcDialogueScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/PaperNoteScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/SummonWraithGuiScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/SwordWraithAppearanceScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/TheLastEndScrollScreen.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/layer/LostWraithSealLayer.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/overlay/DragonArmorOverlay.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/renderer/LightningSpearBurstRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/client/shader/TheLastEndRenderTypes.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/compat/curios/DragonCrystalNecklace.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/compat/curios/ExtremeLifeSupportDevice.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/compat/jade/HealBanElement.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/compat/jade/JustifiedDefenceElement.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlockEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/DragonCultPaladinEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/DragonCultistEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/GroundRuptureFragmentEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/GuardianOfSealedSpireEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/LightningSpearProjectile.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/LostWraithEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/QueenBlinkBlade.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/QueenEnhancedBlade.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/TheLastEndEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/TheLastEndSwordProjectile.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/event/ClientEventHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/event/ServerEventHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/event/TheLastSwordQuestHandler.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/item/TheLastEndScroll.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/mixin/LivingEntityMixin.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/mixin/PlayerMixin.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/mixin/ScreenEffectRendererMixin.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/mixin/ServerPlayerMixin.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/network/SyncSummonGuiPacket.java | ? | ? |
| overlay | .multiversion/targets/1.21.8/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| renames | .multiversion/targets/1.21.8/renames.tsv @cc8617de42d0 | ? | ? |
| resources | .multiversion/targets/1.21.8/resources/META-INF/accesstransformer.cfg @da1532e0fcc2 | ? | ? |
| resources | .multiversion/targets/1.21.8/resources/assets/the_last_sword/geckolib @5483cc454fde | ? | ? |
| resources | .multiversion/targets/1.21.8/resources/assets/the_last_sword/items @898dfe071c13 | ? | ? |
| resources | .multiversion/targets/1.21.8/resources/data/the_last_sword/recipe @cb8439c95bd5 | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/client/gui/DragonCrystalEnchantingTableScreen.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/client/renderer/LightningSpearItemRenderer.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/compat/jade/HealBanElement.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/compat/jade/JustifiedDefenceElement.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/QueenBlinkBlade.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/TheLastEndSwordWraithEntity.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/entity/ThePastShadowOfTheQueenEntity.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/mixin/PlayerMixin.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/mixin/ScreenEffectRendererMixin.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/summon/WraithSummonManager.java | ? | ? |
| stub | .multiversion/targets/1.21.8/java/net/the_last_sword/summon/WraithSummonSavedData.java | ? | ? |
| item | main:net/the_last_sword/client/DragonCrystalEnchantingTableClientExtensions.java#render-class:class DragonCrystalEnchantingTableClientExtensions : IClientItemExtensions | ? | ? |
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
| item | main:net/the_last_sword/mixin/LivingEntityMixin.java#injection:@Inject method=hurt @At(value="HEAD") | ? | ? |
| item | main:net/the_last_sword/mixin/LivingEntityRendererMixin.java#injection:@Inject method=render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V @At(value="HEAD") | ? | ? |
