# Residue: gates

Stopped at **gates**: 1 gate(s) red: Gate B -- headless server loads the mod, GameTests: FAIL -- java.lang.NullPointerException.

## Failing gates

- Gate B -- headless server loads the mod, GameTests: FAIL -- java.lang.NullPointerException: Trying to access unbound value: ResourceKey[minecraft:block / the_last_sword:dragon_crystal_smithing_table]

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.4 --from-stage gates --budget 5.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
