package net.the_last_sword.mixin;

import net.minecraft.network.DisconnectionDetails;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerCombatKillPacket;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.server.network.ServerCommonPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.the_last_sword.util.EntityUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//踢出和危险数据包防护 (send/disconnect are declared in the common superclass)
@Mixin(ServerCommonPacketListenerImpl.class)
public abstract class ServerCommonPacketListenerImplMixin {

    private boolean tls$isProtected() {
        return (Object) this instanceof ServerGamePacketListenerImpl game
                && EntityUtil.hasProtection(game.player);
    }

    @Inject(method = "disconnect(Lnet/minecraft/network/DisconnectionDetails;)V",
            at = @At("HEAD"), cancellable = true)
    private void tls$blockKick(DisconnectionDetails reason, CallbackInfo ci) {
        if (tls$isProtected()) {
            ci.cancel();
        }
    }

    @Inject(method = "send(Lnet/minecraft/network/protocol/Packet;Lnet/minecraft/network/PacketSendListener;)V",
            at = @At("HEAD"), cancellable = true)
    private void tls$blockDangerPackets(Packet<?> packet, PacketSendListener listener, CallbackInfo ci) {
        if (packet instanceof ClientboundDisconnectPacket
                || packet instanceof ClientboundSetHealthPacket
                || packet instanceof ClientboundPlayerCombatKillPacket) {
            if (tls$isProtected()) {
                ci.cancel();
            }
        }
    }
}
