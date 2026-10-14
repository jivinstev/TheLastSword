# Residue: loop

Stopped at **loop**: 63 compile errors.

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
63 errors in 16 files
By CATALOG.md entry (3 errors) -- read only these entries: V4
      3  §V4    e.g. net/the_last_sword/mv/RegIds.java:7: cannot find symbol: class ResourceLocation
Not in the catalogue (60 errors), by family:
     11  method does not override or implement a method from a supertype
      7  package software.bernie.geckolib.* does not exist
      7  cannot find symbol: class AnimationState
      7  cannot find symbol: method getAnimationProcessor
      6  cannot find symbol: method setRotX
      6  cannot find symbol: method setRotY
      4  cannot find symbol: method getName
      3  cannot find symbol: method forceRestart
  ... 7 more families, 9 errors
Files with the most errors (`--file <path>` lists one file's errors):
      6  net/the_last_sword/client/model/DragonCultPriestModel.java
      6  net/the_last_sword/client/model/DragonCultistModel.java
      6  net/the_last_sword/client/model/GuardianOfSealedSpireModel.java
      6  net/the_last_sword/client/model/LostWraithModel.java
      6  net/the_last_sword/client/model/TheLastEndSwordWraithModel.java
      6  net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java
      5  net/the_last_sword/client/layer/LostWraithSealLayer.java
      4  net/the_last_sword/client/model/DragonCultPaladinModel.java
  ... 8 more files
```

## The loop's record

- spent $7.3747
- still failing at the top tier: net/the_last_sword/client/layer/GuardianItemLayer.java, net/the_last_sword/client/layer/LostWraithSealLayer.java, net/the_last_sword/client/layer/TheLastEndSwordWraithLayer.java, net/the_last_sword/client/layer/TheLastEndSwordWraithTheLastEndLayer.java, net/the_last_sword/client/model/DragonCultPaladinModel.java, net/the_last_sword/client/model/DragonCultPriestModel.java, net/the_last_sword/client/model/DragonCultistModel.java, net/the_last_sword/client/model/GuardianOfSealedSpireModel.java, net/the_last_sword/client/model/LostWraithModel.java, net/the_last_sword/client/model/TheLastEndSwordWraithModel.java, net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java, net/the_last_sword/client/renderer/DragonArmorRenderer.java, net/the_last_sword/entity/TheLastEndSwordWraithEntity.java, net/the_last_sword/item/DragonArmorItem.java
- rows kept: 1
- row tried and reverted: `net.minecraft.world.entity.projectile.AbstractArrow	net.minecraft.world.entity.projectile.arrow.AbstractArrow` (compile None >= 831: reverted)
- row tried and reverted: `ResourceLocation	Identifier` (compile None >= 831: reverted)
- row tried and reverted: `net.minecraft.resources.ResourceLocation	net.minecraft.resources.Identifier` (compile None >= 831: reverted)
- row tried and reverted: `net.minecraft.client.renderer.RenderType	net.minecraft.client.renderer.rendertype.RenderType` (compile None >= 831: reverted)
- row tried and reverted: `client.renderer.RenderType	client.renderer.rendertype.RenderType` (compile 831 >= 831: reverted)
- row tried and reverted: `net.minecraft.world.level.GameRules	net.minecraft.world.level.gamerules.GameRules` (compile None >= 831: reverted)
- row tried and reverted: `net.minecraft.world.entity.animal.IronGolem	net.minecraft.world.entity.animal.golem.IronGolem` (compile None >= 831: reverted)
- row tried and reverted: `ResourceKey#location()	ResourceKey#identifier()` (compile 831 >= 831: reverted)
- row tried and reverted: `software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer	software.bernie.geckolib.renderer.layer.builtin.BlockAndItemGeoLayer` (compile None >= 237: reverted)
- row tried and reverted: `RenderType.entityCutout(	RenderTypes.entityCutout(` (compile 199 >= 199: reverted)
- STUB WARNING (a feature may have been deleted to clear an error): .multiversion/targets/1.21.11/java/net/the_last_sword/client/renderer/ThePastShadowOfTheQueenRenderer.java, .multiversion/targets/1.21.11/java/net/the_last_sword/client/renderer/DragonCultistRenderer.java, .multiversion/targets/1.21.11/java/net/the_last_sword/client/renderer/DragonCultPriestRenderer.java, .multiversion/targets/1.21.11/java/net/the_last_sword/client/renderer/GuardianOfSealedSpireRenderer.java, .multiversion/targets/1.21.11/java/net/the_last_sword/client/renderer/LostWraithRenderer.java, .multiversion/targets/1.21.11/java/net/the_last_sword/client/renderer/TheLastEndSwordWraithRenderer.java, .multiversion/targets/1.21.11/java/net/the_last_sword/client/renderer/DragonCultPaladinRenderer.java

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.11 --from-stage loop --budget 15.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
