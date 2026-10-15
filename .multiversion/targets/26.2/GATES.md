Gates for `the_last_sword` at `c2c7601` for Minecraft 26.2 (full environment) -- run by CI with no model and no fixes applied:

| check | result | detail |
|---|---|---|
| author's own build (`build`) | PASS | the_last_sword-26.2-neoforge-1.1.2-mc26.2.jar |
| Gate A -- mixin-config integrity | PASS | 2 test(s) |
| Data -- nothing NeoForge would silently ignore | PASS | 1.21-clean |
| Payloads -- every StreamCodec.unit payload has equal instances | PASS | audit-unit-codecs: checked 395 file(s), 0 finding(s) |
| Data loads -- the server rejected none of the mod's data files | PASS | no rejection logged |
| Gate B -- headless server loads the mod, GameTests | PASS | 11 required test(s) passed |
| Gate C -- real client, `launch` | PASS | PASS — reached title screen, client loaded without crashing |
| Gate C -- real client, `spawn` | PASS | PASS — created world, spawned the_last_sword creatures, world ticked 200 ticks with no crash; 11 entity types spawned; warning: 1 logged exception(s) in other mods' code, e.g. java.lang.IllegalStateException thrown in jeresources.util.VillagersHelper.initRegistry |
| Gate C -- real client, `battle` | PASS | PASS — battle royale ran 600 ticks with no crash (survivors: team A 41, team B 40); warning: 1 logged exception(s) in other mods' code, e.g. java.lang.IllegalStateException thrown in jeresources.util.VillagersHelper.initRegistry |
| Gate C -- real client, `gauntlet` | PASS | PASS — gauntlet ran 10 item/block/use/effect steps with no crash; warning: 1 logged exception(s) in other mods' code, e.g. java.lang.IllegalStateException thrown in jeresources.util.VillagersHelper.initRegistry |

Not covered by any automated gate: gameplay by a person.
