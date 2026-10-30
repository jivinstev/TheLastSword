# Playtests

These scripts play this port in the real Minecraft client, the way a player would, and grade each step.
Nothing here is part of the build: Gradle never reads this folder, and the port's gates do not run these.

- [`PLAN.md`](PLAN.md) lists the ten scenarios, what each proves, what the build loop changed, and the findings.
- `NN-*.playtest` are the scripts, one per scenario. `01-smoke.playtest` is the maintainer's smoke list.
- `11-story.playtest` plays the quest line stage by stage. `12-ruined-village.playtest` plays its one stage that needs a
  normal world.

A script is Minecraft commands plus `@` directives (`@step`, `@assert`, `@aim`, `@key`, `@expect`, `@shot`, ...).
The engine and the tools live in the migrator repository (`mc-mod-version-upgrade`). Its README's "Playtests"
section and `tools/playtest-lint.py` describe the format.

## Running them

You need a Mac (or Windows or Linux) with the Minecraft Launcher's files, and the migrator repository checked out.
The commands below run from that checkout.

1. **Make the test instances, once.** This makes one instance per Minecraft version this port supports, with the
   released jars and the Gate C carrier (the mod that runs the scripts). It never touches your own mods folder or
   profiles.

   ```bash
   python3 tools/real-install.py plan --fork OWNER/LIB --fork OWNER/THIS-REPO --all-versions --prerelease --with-carrier
   python3 tools/real-install.py install
   ```

2. **Check a script without Minecraft** (wrong ids, bad directives, forbidden debug content):

   ```bash
   python3 tools/playtest.py lint PATH/TO/playtests/01-smoke.playtest --jar MOD.jar --forbid the_last_sword:test_entity --forbid the_last_sword:test_entity_spawn_egg --forbid the_last_sword:ultra_test_sword
   ```

3. **Run it** on one version, or on every installed version:

   ```bash
   python3 tools/playtest.py run PATH/TO/playtests/01-smoke.playtest --mc 1.21.1
   ```

   ```bash
   python3 tools/playtest.py run PATH/TO/playtests/01-smoke.playtest --video
   ```

   A game window opens for each version in turn. Keep the screen awake and unlocked while it runs. `--video`
   records the screen; macOS asks once for Screen Recording permission.

4. **Read the result.** The command prints PASS or FAIL per version and the report's path. `report.html` holds every
   step with its key frames; failed steps start open. For a failure, `python3 tools/playtest.py explain REPORT_DIR`
   gives the failed step, what was seen and heard, and the log's tail.

In Claude Code, inside the migrator checkout, you can ask instead:

- `> Run the playtests in PATH/TO/playtests on every installed version, with video` (playtest-run);
- `> Why did that playtest fail?` (playtest-debug);
- `> Write a playtest: ...` (playtest-write);
- `> Plan playtests for this mod and build them` (playtest-plan).

## Never summon or give

`the_last_sword:test_entity`, its spawn egg and `the_last_sword:ultra_test_sword` are debug content. Lint every
script with them as `--forbid`.
