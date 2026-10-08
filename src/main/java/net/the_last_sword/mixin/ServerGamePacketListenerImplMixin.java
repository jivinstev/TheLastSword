package net.the_last_sword.mixin;

import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//踢出和危险数据包防护 (kick/packet parts live in ServerCommonPacketListenerImplMixin)
@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {

    @Shadow public ServerPlayer player;

    //捕获玩家手动切换飞行的真实意图，写入NBT供恢复逻辑使用
    @Inject(method = "handlePlayerAbilities", at = @At("HEAD"))
    private void tls$captureFlightIntent(ServerboundPlayerAbilitiesPacket packet, CallbackInfo ci) {
        this.player.getPersistentData().putBoolean("PlayerFlightIntent", packet.isFlying());
    }
}
