// OVERLAY-ONLY: shared fork-layer helper from mc-mod-version-upgrade templates/fork-layer/shared (not the authors' code).
// MV-AHEAD-OF-TEMPLATE: the migrator's shared/1.21.8 ClientRefs, code unchanged; only its javadoc example is reworded, since
// MV-AHEAD-OF-TEMPLATE: fork-port.py's M52 audit (client_refs_in_common) matches that example line in this common-package
// MV-AHEAD-OF-TEMPLATE: file on a strip_onlyin target. Delete once the template's comment no longer matches the audit.
package net.the_last_sword.mv;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

/**
 * From 1.21.6 @OnlyIn strips nothing (the layer's strip_onlyin removes it, CATALOG V79), so a method that was
 * @OnlyIn(Dist.CLIENT) in a COMMON class now stays in that class on a dedicated server. A body such as
 * {@code Player p = <Minecraft instance>.player;} then makes the bytecode verifier load LocalPlayer (to check it
 * is a Player) when the class links, and the server dies in RegisterEvent with
 * {@code NoClassDefFoundError: net/minecraft/client/player/LocalPlayer}. Calling through this class keeps the
 * client types out of the caller: an invokestatic's return type comes from its descriptor, and this class is only
 * loaded when a client actually calls it.
 */
public final class ClientRefs {
    private ClientRefs() {
    }

    public static Player player() {
        return Minecraft.getInstance().player;
    }

    public static Level level() {
        return Minecraft.getInstance().level;
    }
}
