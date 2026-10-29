# Playtest plan: The Last Sword (NeoForge port)

- **Made by:** MMVU's `playtest-plan` skill, 2026-10-25. N = 10.
- **Grounded in:** `the_last_sword-<mc>-neoforge-1.1.2-fix-fix.jar` and `epic-core-api-1.1.9-fix_mr.jar`, through
  `tools/playtest.py inventory`, and in the port branch's source.
- **forbid:** `the_last_sword:test_entity`, `the_last_sword:test_entity_spawn_egg`, `the_last_sword:ultra_test_sword`.
  These are debug content and are never summoned or given. Every script is linted with these ids passed as
  `--forbid`.

Scenario 1 is the maintainer's own smoke list. Scenarios 2-10 are ranked by the distinct code paths they reach that
no scenario above them reaches.

| # | Script | Intent | Code paths it reaches |
|---|---|---|---|
| 1 | `01-smoke.playtest` | The maintainer's smoke list: creative to survival on Normal, the wraith's music, a one-hit kill with the sword (the music stops), a few items, death to the guardians, respawn and re-arm, and the queen's narration | `TheLastEndSwordWraithEntity` spawn animation; `CombatMusicHandler` play and stop; `TheLastSwordYouNeverForgot.hurtEnemy` (ECA `hurt`, all-return); guardian AI against a player; death and respawn; the queen's boss show `range` trigger, `activate` and death sequence |
| 2 | `02-wraith-music-lifecycle.playtest` | The wraith's music starts only once it is ready, plays on through `/kill` (which the wraith shrugs off), and stops when the player leaves its 64-block range | `isReady` gating, the 20-tick music check, TrueHealth against `/kill`, `stopBattleMusic` on absence |
| 3 | `03-sword-projectile.playtest` | Right-clicking the sword fires its projectile, which kills at range | `TheLastSwordYouNeverForgot.use` and `TheLastSwordYouNeverForgotProjectile.shoot` |
| 4 | `04-queen-reward.playtest` | Defeating the queen grants The Giver's Pain and turns her into a talking NPC | `tickDeathSequence` (item at the appear tick, reward at tick 100); `finishDeathWithoutRemoval`; the NPC state refusing damage |
| 5 | `05-lost-wraith-dragon-egg.playtest` | Using a dragon egg on the Lost Wraith starts its boss show and dialogue | `LostWraithEntity` interaction (dragon egg while not ready); the `custom` boss show trigger; the encounter dialogue |
| 6 | `06-dragon-cult.playtest` | The dragon cult (cultist, paladin, priest) fights a survival player, the priest's dragon breath among it, and each dies to the sword | the cult entities' AI and attacks; the priest's dragon breath; their deaths to the ECA damage path |
| 7 | `07-keybinds.playtest` | The mod's three key bindings open their screens (defence config, summon GUI) and change the sword mode | `ModKeyMappings` handlers; the client screens |
| 8 | `08-guardian-of-sealed-spire.playtest` | The spire guardian boss can be summoned, fights and dies to the sword | `GuardianOfSealedSpireEntity` (it has no boss bar) |
| 9 | `09-dragon-crystal-armor.playtest` | A full dragon crystal armour set equips and grants its set effect without errors | the armour set bonus handlers and `attribute.the_last_sword.justified_defence` |
| 10 | `10-dragon-cult-horn.playtest` | Blowing the Dragon Cult horn far from a village sounds the horn and says on the action bar that no raid can start | `DragonCultHornItem.use`; `tryStartDragonCultRaid`'s unavailable branch |

What counts as the **mod's** failure in each scenario:

- a crash or exception from the mod's package;
- a promised sound, subtitle or line that never arrives;
- a kill that the code says is certain but does not happen.

The **script's** failures are the usual ones: a timing window that is too tight, an aim that misses, a world that
was not as the script assumed. playtest-debug tells the two apart.

## What the build loop changed, and why

The plan was built with `playtest-plan --build`: each scenario was written, run on 1.21.1, and debugged until the
script was right. Every change below was made after the script, not the mod, was found at fault:

- The lucky block (first scenario 10) is registered only when the Lucky Block mod is installed, so it was replaced
  with the Dragon Cult horn. The crown of countless stars in scenario 1 is likewise registered only with a companion
  mod, so the dragon crystal crown replaced it.
- The spire guardian has no boss bar in its source, so scenario 8 no longer expects one.
- In scenario 6, a diamond sword with Strength IX left a 40-health cultist standing after three full hits.
  Whether ordinary weapons should be able to kill the cult (the mod's justified-defence shield, perhaps) is a
  question for the mod's owner. The scenario proves the mod's own sword instead.
- Bosses shrug off `/kill` (their health is kept apart from vanilla's), so scenario 2 proves that instead of using it.

## Findings (the mod's, not the scripts')

- **Boss shows do not load on 1.21.10 and later** (1.21.10, 1.21.11, 26.1.2, 26.2, 26.3). The logs show
  `Loaded 1 BossShow definition(s) from JSON` and never `from mod data`; 1.21.1-1.21.8 load 2 from each. The queen's
  boss show never starts, so she never activates and takes no damage (scenarios 1 and 4). The Lost Wraith's
  dragon-egg wake-up (scenario 5) depends on its boss show too. The suspected cause is in ECA's 1.21.10 port:
  `BossShowManager.loadModDataDefinitions` swapped FML's removed `modFile.findResource` for a
  `getContents().getContentRoots()` walk that finds nothing inside a jar, and a catch-all returns null without a log.
- **On 1.21.8 the queen's boss-show cinematic freezes on its first frame.** The camera stays on the show's opening
  shot with `subtitle_1` showing for the rest of the run (20+ seconds measured). The activation lines arrive in chat,
  but every attack lands on a locked fight and does nothing, so she is never defeated. Nothing is logged.
  1.21.1, 1.21.4 and 1.21.5 play the same show to its end. The cause has not been traced yet: ECA's 1.21.8 boss-show
  playback (its client tick or camera path) is the first place to look.
- **Not a defect, but worth knowing:** The Last Sword You Never Forgot is not a one-shot against every mob. TLS caps
  each hit on its own mobs (`getDamageLimit`), and for mobs above level 5 a hit over the cap is ignored outright. The
  Dragon Cult paladin also blocks: inside its shield window `hurt` returns false. The wraith, the queen, the
  guardians and the cultist die in one hit; the paladin and the priest take many.

## Observed, by design

- **After The Last Sword You Never Forgot kills one of the mod's mobs (the queen, a raid cultist), the sword leaves
  the hand.** Defeating the queen with it removes it entirely from the player's inventory (1.21.1,
  1.21.4). The maintainer has seen it in play, so it is treated as the mod's design. No script asserts the sword
  stays after her; a step that needs it puts it in hand again.
- **TLS's mobs shrug off `/kill`.** The scripts kill them the way a player does, with the sword.
- **The Dragon Cult paladin and priest outlast many hits** (each hit capped, the paladin blocking), and how many varies
  run to run. Scenario 6 proves they fight; it does not chase them to death.
- **The structures' item frames carry the authors' world coordinates.** Each frame in the traveler's hold, the
  sealed spire and the ruined village's wizard tower stores `TileX/Y/Z` from the world the structure was built in.
  Generation logs `Block-attached entity at invalid position` once per frame. Usually the frames survive and the
  quest item in them can be taken (the story playtest takes the traveler's message from one). But once, on 1.21.1, the
  hold's frame had popped off by the time the player arrived, leaving the message on the floor; it could still be
  picked up. Re-saving the three templates (fresh `TileX/Y/Z`) would fix both the log line and the popping. The
  scripts take the item either way.

## The story playtest (`11-story.playtest`)

It plays the quest line stage by stage in a superflat world with structures. It's written with the playtest
language's bounded loops: each mob is struck until it falls, and each quest stage is polled for, never waited out.

- **2026-10-26, every stage passes up to the known sealed-spire crash** on Modrinth 1.21.1 and CurseForge 1.21.1 and
  1.21.4 (ECA's `_cf` jar, which leaves out its Java agent and AllReturn transformer, behaves the same). That covers:
  - the welcome and village hint;
  - the troubled blacksmith, and buying his scroll through the trade screen;
  - the dragon crystal;
  - the cult's raid, all three waves won, and the priest's letter;
  - the traveler's hold and its message;
  - the End's reset ruins and lost altar;
  - the Wound of Time;
  - the queen's trial.
- **The ruined village has its own script, `12-ruined-village.playtest`.** Only the normal overworld's worldgen places it,
  so it runs in a normal world with a fixed seed (seed 1, whose nearest ruined village has the wizard tower at its
  centre). On 1.21.1 it passes: the discovery, the dragon crystal taken from the tower's item frame with "Familiar Gem"
  granted, and the fourth mage's note.
- **The sealed spire's three stages run last.** Before TLS-056 was fixed its chunk crashed the game as it generated.
- **2026-10-27, the whole story passes, 17/17 steps,** on `the_last_sword-1.1.2-fix-fix-mc1.21.1-r2` (built from
  `af3a79d`, with the TLS-056 fix) and `eca-1.1.9-fix-mc1.21.1`, on both the Modrinth and the CurseForge builds:
  the sealed spire generates and is discovered, the Lost Wraith is woken with a dragon egg and freed, and the
  "where is she?" note is taken. Scripts 01-10 and 12 pass on both too. 1.21.4 does not have the fix yet.

## From CurseForge, 2026-10-28 (the published files, carrier v6 and its structure-tag fix)

Every version installed the way a player gets it: the port's CurseForge file and its three required relations (the
Epic Core API port, GeckoLib, Curios), each the newest file CurseForge serves for that version -- every one the same
sha256 as the r3 ladder's. Scripts 01-10 and 12 pass on all nine versions; the story 17/17 on all nine.

- New defects in the mod:
  - TLS-057 (S3): on 1.21.4 and later, the Curios accessories' stat bonuses never apply. Curios 10+ reads a curio's
    modifiers only from its `curios:attribute_modifiers` item data, and the port kept the per-item
    `getAttributeModifiers` override, built against Curios 9.5.1 on every branch, which nothing calls. Per-tick
    effects (the wings' flight) still work. Played on 26.1.2, 26.2 and 26.3; 1.21.4 to 1.21.11 from Curios's code.
  - TLS-058 (S3): on 26.2, the `perceptionGlowing` Entity mixin reads the entity id before it is assigned, so a
    monster spawner's display mob fails to load (the lost altar, the Wound of Time). Seen in one of two runs.
- New script: `13-curios-accessories` wears the ring, necklace, crown and wings in their Curios slots and checks
  each bonus comes and goes. 1.21.1: 5/5. 26.x: the wings pass, the three stat bonuses fail (TLS-057). 1.21.4 to
  1.21.11: every equip fails at `/curios replace` -- Curios 10 to 14 fill the command's slot list before the slots
  load, so it refuses every slot (Curios's bug; the Curios screen equips fine). It uses only ids every version
  shares: vanilla attribute ids changed at 1.21.2 and a script cannot branch on the version.
- Not the mod's:
  - The story's village stages failed on 1.21.4, 1.21.5 and 1.21.10 with carrier v6: its `@goto` matched a
    structure tag (`#minecraft:village`) against no structure and left the player outside the village. Fixed in the
    carrier (MMVU #201); with it every version passes them.
  - 26.1.2 crashed once in vanilla's light engine (a NullPointerException, no mod frame) during 12-ruined-village,
    while carriers were being built alongside. It passes on the rerun.
- Script fix: S15 woke the spire's own Lost Wraith, which hangs in chains inside a cage of blocks; on 1.21.1 the
  crosshair hit the cage and the egg never reached it. The step now summons a tagged wraith into a pocket of open air.

## The ladder, 2026-10-28 (CurseForge builds, the r3 pre-releases of every version, carrier v5)

Every script on 1.21.1, 1.21.4, 1.21.5, 1.21.8, 1.21.10, 1.21.11, 26.1.2, 26.2 and 26.3: 108 of 108 pass, the story
17/17 on every version. The r3 builds carry the TLS-056 fix (`ModHolders$Client.onClientThread`), and the sealed spire
no longer crashes anywhere, 1.21.4 and 1.21.5 included.

- No new defect in the mod. The same structures also carry a second stale Forge attribute id,
  `forge:entity_gravity`; it logged once (1.21.5, the traveler's hold). Same cause and fix as `step_height_addition`.
- Not the mod's:
  - Seven runs failed to open the game window: the display had slept. They pass on the rerun.
  - The ruined village went undiscovered on 1.21.10 and 26.2. The carrier's `@goto` took the first structure start in
    the chunk, a mineshaft's underground there, not the village's; fixed in the carrier (MMVU). With it, all three
    versions rerun reach the tower and are granted the stage at once. Standing in the tower grants it within 3 s, even
    after it is revoked.
- Script fix: both scripts allow `forge:(step_height_addition|entity_gravity)`.

## The ladder, 2026-10-27 (CurseForge builds, the r2 pre-releases of every version)

Every script on 1.21.4, 1.21.5, 1.21.8, 1.21.10, 1.21.11, 26.1.2, 26.2 and 26.3:

- Scripts 01-10 and 12 pass on every version. So the boss shows load on 1.21.10 and later, the queen can be fought
  on 1.21.5 and later, and 1.21.8's opening scene no longer freezes.
- The story passes 17/17 on 1.21.8, 1.21.10, 1.21.11, 26.1.2, 26.2 and 26.3. It reaches the sealed spire on 1.21.4 and
  1.21.5, which crash there (TLS-056). The fix is in none of these builds, and on 1.21.8 and later the spire happened
  to generate without crashing in this run: the crash depends on which thread equips the guardians.
- The structures' mobs carry a Forge 1.20.1 attribute id, `forge:step_height_addition`, in their saved data (the
  sealed spire's, the traveler's hold's and the wizard tower's). Loading each one logs an ERROR on every version. The
  mobs set their step height in code, so nothing in play changes. Re-saving the templates fixes this along with the
  stale frame coordinates.
- Script fixes from this pass:
  - The blacksmith is met on a platform above the village: on 1.21.11 a block stood between the player and him.
  - The ruined village's poll is 20 s.
  - The carrier's `@goto` found no start piece on 26.3 (MMVU #192). With that fix the story passes there.

## CurseForge builds (ECA's `_cf` jar), 2026-10-26

All twelve scripts were run on CurseForge-variant instances of 1.21.1 and 1.21.4. Every one passes there as on the
Modrinth builds; the story ends at the same known sealed-spire crash. ECA's `_cf` jar leaves out its Java agent and its
AllReturn transformer, and no script found a difference.
