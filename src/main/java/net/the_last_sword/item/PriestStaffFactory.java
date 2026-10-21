package net.the_last_sword.item;

import net.minecraft.world.item.Item;

public final class PriestStaffFactory {
    private PriestStaffFactory() {
    }

    public static Item create() {
        // The Iron's Spellbooks casting variant is unavailable on this target.
        return new PriestStaffItem();
    }
}
