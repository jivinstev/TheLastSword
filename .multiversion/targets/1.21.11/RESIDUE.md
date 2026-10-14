# Residue: loop

Stopped at **loop**: 48 compile errors.

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
48 errors in 14 files
Not in the catalogue (48 errors), by family:
     11  method does not override or implement a method from a supertype
      6  cannot find symbol: method setRotX
      6  cannot find symbol: method setRotY
      5  cannot find symbol: method getAnimationProcessor
      4  cannot find symbol: class AnimationState
      2  package software.bernie.geckolib.* does not exist
      2  cannot find symbol: method getAnimationState
      2  cannot find symbol: method forceRestart
  ... 9 more families, 10 errors
Files with the most errors (`--file <path>` lists one file's errors):
      6  net/the_last_sword/client/model/LostWraithModel.java
      5  net/the_last_sword/client/model/DragonCultPriestModel.java
      5  net/the_last_sword/client/model/GuardianOfSealedSpireModel.java
      4  net/the_last_sword/client/layer/LostWraithSealLayer.java
      4  net/the_last_sword/client/model/DragonCultPaladinModel.java
      4  net/the_last_sword/client/model/DragonCultistModel.java
      4  net/the_last_sword/client/model/TheLastEndSwordWraithModel.java
      4  net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java
  ... 6 more files
```

## The loop's record

- spent $3.8861
- still failing at the top tier: net/the_last_sword/client/layer/LostWraithSealLayer.java, net/the_last_sword/client/layer/TheLastEndSwordWraithLayer.java, net/the_last_sword/client/layer/TheLastEndSwordWraithTheLastEndLayer.java, net/the_last_sword/client/model/DragonCultPaladinModel.java, net/the_last_sword/client/model/DragonCultPriestModel.java, net/the_last_sword/client/model/DragonCultistModel.java, net/the_last_sword/client/model/GuardianOfSealedSpireModel.java, net/the_last_sword/client/model/LostWraithModel.java, net/the_last_sword/client/model/TheLastEndSwordWraithModel.java, net/the_last_sword/client/model/ThePastShadowOfTheQueenModel.java, net/the_last_sword/client/renderer/DragonArmorRenderer.java, net/the_last_sword/entity/TheLastEndSwordWraithEntity.java, net/the_last_sword/item/DragonArmorItem.java
- rows kept: 0

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.11 --from-stage loop --budget 10.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
