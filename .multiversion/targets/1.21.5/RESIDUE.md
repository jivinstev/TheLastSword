# Residue: gates

Stopped at **gates**: 1 gate(s) red: Gate C -- real client, `spawn`: FAIL -- java.nio.file.NoSuchFileException: /home/user/TheL.

## Failing gates

- Gate C -- real client, `spawn`: FAIL -- java.nio.file.NoSuchFileException: /home/user/TheLastSword/run/config/neoforge-client-5.toml.bak

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.5 --from-stage gates --budget 3.0 --gates full --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
