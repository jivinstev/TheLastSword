# Cascade decisions (append-only; tools/fork-branches.py cascade)

| date | decision | file | version | choice | by | reasoning |
|---|---|---|---|---|---|---|
| 2026-10-21 | 4c329f18ad6e | `src/main/java/net/the_last_sword/compat/irons_spellbooks/IronsSpellsCompat.java` | 1.21.4 | content | fixer agent (default policy) | optional integration kept; the authors' change applied around ours: priest staff matched by registry item (ModItems.PRIEST_STAFF) instead of instanceof PriestStaffItem, so the casting variant is covered too |
| 2026-10-21 | e8267802c360 | `src/main/java/net/the_last_sword/item/PriestStaffItem.java` | 1.21.4 | content | fixer agent (default policy) | authors-fix taken: createProperties(), sneak-use -> static useGuard (own cooldown check), appendStaffTooltip(casting) as the head has them; adapted to 1.21.2+ shapes (InteractionResult, cooldowns keyed by stack, RegIds.stamp kept) and getEnchantmentValue()=32 carried as Item.Properties.enchantable(32) |
| 2026-10-21 | c089b104cadf | `src/main/java/net/the_last_sword/summon/WraithSummonManager.java` | 1.21.4 | content | fixer agent (default policy) | the authors' summon-input guard is already in the merged file; our 1.21.2+ shape kept around it (cooldown keyed by the weapon stack, fixer commit 2e7feba) |
