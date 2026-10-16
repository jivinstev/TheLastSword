# Residue: loop

Stopped at **loop**: 0 errors, but 3 stub signal(s) and 0 overlay(s) of the shared library.

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
stub: .multiversion/targets/1.21.5/java/net/the_last_sword/init/ModEntityRenderers.java: removes 1 registration/listener/send call(s)
stub: .multiversion/targets/1.21.5/java/net/the_last_sword/init/ModShaders.java: empties 1 method body
stub: .multiversion/targets/1.21.5/java/net/the_last_sword/mixin/LivingEntityMixin.java: empties 1 method body
```

## To resume

`python3 tools/fork-port.py --repo /home/user/tls-layer --target 1.21.5 --from-stage loop --budget 15.0 --gates full --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-wt/snapshot-1215 --allow-undone-library`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
