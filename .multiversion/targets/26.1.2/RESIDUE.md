# Residue: loop

Stopped at **loop**: 0 errors, but 7 stub signal(s) and 0 overlay(s) of the shared library.

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
stub: .multiversion/targets/26.1.2/java/net/the_last_sword/block/DragonSoulLanternBlock.java: deletes overriding method(s) getLightBlock: whatever they did is gone -- port each to the new API, or say why vanilla no longer needs it
stub: .multiversion/targets/26.1.2/java/net/the_last_sword/client/layer/DragonCultistItemLayer.java: drops @Override from submitItemStackRender and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead; deletes o
stub: .multiversion/targets/26.1.2/java/net/the_last_sword/client/layer/GuardianItemLayer.java: drops @Override from submitItemStackRender and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead; drops @Ov
stub: .multiversion/targets/26.1.2/java/net/the_last_sword/client/layer/ThePastShadowOfTheQueenItemLayer.java: drops @Override from submitItemStackRender and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead; deletes o
stub: .multiversion/targets/26.1.2/java/net/the_last_sword/compat/lucky_block/TheLastEndLuckyBlock.java: deletes overriding method(s) getLightBlock: whatever they did is gone -- port each to the new API, or say why vanilla no longer needs it
stub: .multiversion/targets/26.1.2/java/net/the_last_sword/event/EnderDragonEvent.java: drops @Override from save and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead
stub: .multiversion/targets/26.1.2/java/net/the_last_sword/recipe/DragonCrystalSmithingSerializer.java: drops @Override from codec, streamCodec and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead
```

## To resume

`python3 tools/fork-port.py --repo . --target 26.1.2 --from-stage loop --budget 10.0 --gates full --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
