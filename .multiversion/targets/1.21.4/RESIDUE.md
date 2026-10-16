# Residue: loop

Stopped at **loop**: 0 errors, but 4 stub signal(s) and 0 overlay(s) of the shared library.

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
stub: .multiversion/targets/1.21.4/java/net/the_last_sword/client/renderer/EndCrystalRiftRenderer.java: drops @Override from getTextureLocation and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead
stub: .multiversion/targets/1.21.4/java/net/the_last_sword/entity/EndCrystalRift.java: drops @Override from getBoundingBoxForCulling and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead
stub: .multiversion/targets/1.21.4/java/net/the_last_sword/init/ModItems.java: removes 5 registration/listener/send call(s)
stub: .multiversion/targets/1.21.4/java/net/the_last_sword/test/TestEntity.java: empties 1 method body
```

## To resume

`python3 tools/fork-port.py --repo /home/user/tls-layer --target 1.21.4 --from-stage loop --budget 15.0 --gates none`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
