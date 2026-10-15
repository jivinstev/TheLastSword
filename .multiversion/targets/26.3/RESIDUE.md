# Residue: gates

Stopped at **gates**: 1 gate(s) red: Gate C -- real client, `battle`: FAIL -- Caused by: java.lang.IllegalStateException: Faile.

## Failing gates

- Gate C -- real client, `battle`: FAIL -- Caused by: java.lang.IllegalStateException: Failed to find or load pipeline minecraft:pipeline/lost_wraith_seal

## To resume

`python3 tools/fork-port.py --repo . --target 26.3 --from-stage gates --budget 3.0 --gates full --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
