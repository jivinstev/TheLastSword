# Residue: measure

Stopped at **measure**: the compile does not count: NOT A COUNT -- compileJava never ran. Blocking failure: > Configure project : FAILURE: Build failed with an exception. * What went wrong: > Process 'command 'python3'' finished with non-zero exit value 1 > Run with --stacktrace option to get the stack trace. > Run with --info or --debug option to get more log output. > Run with --scan to get full insights..

## Remaining compile errors (compile-summary.py, grouped by CATALOG entry / family)

```
NOT A COUNT -- compileJava never ran. Blocking failure: > Configure project : FAILURE: Build failed with an exception. * What went wrong: > Process 'command 'python3'' finished with non-zero exit value 1 > Run with --stacktrace option to get the stack trace. > Run with --info or --debug option to get more log output. > Run with --scan to get full insights.
```

## To resume

`python3 tools/fork-port.py --repo . --target 1.21.5 --from-stage measure --budget 15.0 --gates ab --port-checkout jivinstev/EpicCoreAPI=/home/user/eca-layer`

Fix what is listed here, and put every fix in the shared library (templates/fork-layer/shared/<t>) or a converter, not in one fork.
