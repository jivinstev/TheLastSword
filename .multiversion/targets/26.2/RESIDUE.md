# Residue: gates

Stopped at **gates**: 1 gate(s) red: Gate C -- real client, `spawn`: FAIL -- java.lang.IllegalStateException: Missing registry:.

## Failing gates

- Gate C -- real client, `spawn`: FAIL -- java.lang.IllegalStateException: Missing registry: ResourceKey[minecraft:root / minecraft:trade_set]

## To resume

`python3 tools/fork-port.py --repo . --target 26.2 --from-stage gates --budget 1.0 --gates full --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
