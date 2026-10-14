# Residue: loop

Stopped at **loop**: 0 errors, but 2 unreviewed stub signal(s) from earlier runs (STUBS.json).

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
stub: .multiversion/targets/1.21.8/java/net/the_last_sword/compat/jade/HealBanElement.java: drops @Override from getSize and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead
stub: .multiversion/targets/1.21.8/java/net/the_last_sword/compat/jade/JustifiedDefenceElement.java: drops @Override from getSize and keeps the method: it then overrides nothing and never runs -- port it to the new signature, or delete it and say what vanilla now does instead
```

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.8 --from-stage loop --budget 15.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
