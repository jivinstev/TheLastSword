# Residue: loop

Stopped at **loop**: 0 errors, but 1 unreviewed stub signal(s) from earlier runs (STUBS.json).

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
stub: .multiversion/targets/1.21.4/java/net/the_last_sword/mixin/GameRendererMixin.java: removes 1 mixin injection handler(s): check each still applies somewhere, with the same effect
```

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.4 --from-stage loop --budget 1.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
