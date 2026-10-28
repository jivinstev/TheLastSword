Gates for `the_last_sword` at `5a17792` for Minecraft 1.21.11 (full environment) -- run by CI with no model and no fixes applied:

| check | result | detail |
|---|---|---|
| author's own build (`build`) | PASS | the_last_sword-1.21.11-neoforge-1.1.2-fix-fix.jar |
| Gate A -- mixin-config integrity | PASS | 2 test(s) |
| Data -- nothing NeoForge would silently ignore | PASS | 1.21-clean |
| Wiring -- subscribers and capabilities against `origin/neoforge-1.21.10` (report) | REPORT | 2 to read: capability registration gone: register*(new IClientItemExtensions() { @Override public ResourceLocation getArmorTexture(ItemStack stack, EquipmentClientInfo.Lay) is not in the tree any more (CATALOG 12e); null side answered with null: src/main/java/net/the_last_sword/init/ModCapabilities.java:53 -- a menu that binds through getCapability(..., pos, null) is detached from the block (CATALOG 12e augment) |
| Payloads -- every StreamCodec.unit payload has equal instances | PASS | audit-unit-codecs: checked 434 file(s), 0 finding(s) |
| Data loads -- the server rejected none of the mod's data files | PASS | no rejection logged |
| Gate B -- headless server loads the mod, GameTests | PASS | 6 required test(s) passed |
| Gate C -- real client, `launch` | PASS | PASS — reached title screen, client loaded without crashing |
| Gate C -- real client, `spawn` | PASS | PASS — created world, spawned the_last_sword creatures, world ticked 200 ticks with no crash; 11 entity types spawned; warning: 2 logged exception(s) in other mods' code, e.g. java.lang.NullPointerException thrown in jeresources.collection.TradeList.addMerchantRecipe |
| Gate C -- real client, `battle` | PASS | PASS — battle royale ran 600 ticks with no crash (survivors: team A 41, team B 40); warning: 2 logged exception(s) in other mods' code, e.g. java.lang.NullPointerException thrown in jeresources.collection.TradeList.addMerchantRecipe |
| Gate C -- real client, `gauntlet` | PASS | PASS — gauntlet ran 10 item/block/use/effect steps with no crash; warning: 2 logged exception(s) in other mods' code, e.g. java.lang.NullPointerException thrown in jeresources.collection.TradeList.addMerchantRecipe |

Not covered by any automated gate: gameplay by a person.
