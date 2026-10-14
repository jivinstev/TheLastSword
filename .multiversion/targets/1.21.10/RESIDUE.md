# Residue: gates

Stopped at **gates**: 1 gate(s) red: Gate C -- real client, `spawn`: FAIL -- java.lang.IllegalStateException: Missing sampler M.

## Failing gates

- Gate C -- real client, `spawn`: FAIL -- java.lang.IllegalStateException: Missing sampler MaskSampler

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.10 --from-stage gates --budget 3.0 --gates full --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
