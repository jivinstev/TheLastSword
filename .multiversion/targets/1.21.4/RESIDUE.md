# Residue: gates

Stopped at **gates**: 1 gate(s) red: Gate B -- headless server loads the mod, GameTests: FAIL -- Caused by: org.spongepowered.a.

## Failing gates

- Gate B -- headless server loads the mod, GameTests: FAIL -- Caused by: org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException: Critical injection failure: @ModifyConstant annotatio

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.4 --from-stage gates --budget 5.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
