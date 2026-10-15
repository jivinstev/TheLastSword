# Residue: gates

Stopped at **gates**: 1 gate(s) red: Gate C -- real client, `gauntlet`: FAIL -- Caused by: java.lang.IllegalArgumentException: .

## Failing gates

- Gate C -- real client, `gauntlet`: FAIL -- Caused by: java.lang.IllegalArgumentException: Attempted to retrieve data from GeoRenderState that does not exist. Check your code!

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.8 --from-stage gates --budget 3.0 --gates full --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
