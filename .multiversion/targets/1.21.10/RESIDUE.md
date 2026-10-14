# Residue: loop

Stopped at **loop**: 7 compile errors.

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
7 errors in 4 files
Not in the catalogue (7 errors), by family:
      4  method does not override or implement a method from a supertype
      3  cannot find symbol: method renderStackForBone
Files with the most errors (`--file <path>` lists one file's errors):
      3  net/the_last_sword/client/layer/GuardianItemLayer.java
      2  net/the_last_sword/client/layer/ThePastShadowOfTheQueenItemLayer.java
      1  net/the_last_sword/client/DragonCultArmorClientExtensions.java
      1  net/the_last_sword/client/layer/LostWraithSealLayer.java
```

## The loop's record

- spent $4.8532
- still failing at the top tier: net/the_last_sword/client/DragonCultArmorClientExtensions.java, net/the_last_sword/client/layer/GuardianItemLayer.java, net/the_last_sword/client/layer/LostWraithSealLayer.java, net/the_last_sword/client/layer/ThePastShadowOfTheQueenItemLayer.java
- rows kept: 7
- row tried and reverted: `.isClientSide	.isClientSide()` (compile None >= 360: reverted)
- row tried and reverted: `.level().isClientSide	.level().isClientSide()` (compile None >= 360: reverted)
- row tried and reverted: `createBaseRenderState	createRenderState` (compile 360 >= 360: reverted)
- row tried and reverted: `player.level().isClientSide)	player.level().isClientSide())` (compile 360 >= 360: reverted)
- row tried and reverted: `getGameProfile().getName()	getGameProfile().name()` (compile 360 >= 360: reverted)
- row tried and reverted: `player.getServer()	player.serverLevel().getServer()` (compile 337 >= 337: reverted)
- row tried and reverted: `!level.isClientSide &&	!level.isClientSide() &&` (compile 317 >= 317: reverted)
- row tried and reverted: `FMLEnvironment.dist	FMLLoader.getDist()` (compile None >= 317: reverted)
- row tried and reverted: `.getCapability(Capabilities.Energy.ITEM)	Capabilities.Energy.ITEM.getCapability(<stack>, ItemAccess.forStack(<stack>)) (receiver stack becomes first argument; import net.neoforged.neoforge.transfer.access.ItemAccess)` (a <placeholder> in the replacement)
- row tried and reverted: `.getCapability(Capabilities.Energy.ITEM)	Capabilities.Energy.ITEM.getCapability(<stack>, ItemAccess.forStack(<stack>)) (receiver stack becomes first argument; import net.neoforged.neoforge.transfer.access.ItemAccess)` (a <placeholder> in the replacement)
- STUB WARNING (a feature may have been deleted to clear an error): .multiversion/targets/1.21.10/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java, .multiversion/targets/1.21.10/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java, .multiversion/targets/1.21.10/java/net/the_last_sword/client/layer/DragonCultistItemLayer.java, .multiversion/targets/1.21.10/java/net/the_last_sword/test/TestEntity.java

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.10 --from-stage loop --budget 15.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
