package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

//客户端发送给服务器的取消预览包
public class CancelPreviewPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CancelPreviewPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "cancel_preview_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, CancelPreviewPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> msg.encode(buf), CancelPreviewPacket::decode);

    @Override
    public CustomPacketPayload.Type<CancelPreviewPacket> type() {
        return TYPE;
    }

    public CancelPreviewPacket() {}

    //解码
    public static CancelPreviewPacket decode(FriendlyByteBuf buf) {
        return new CancelPreviewPacket();
    }

    //编码
    public void encode(FriendlyByteBuf buf) {}

    //处理
    public static void handle(CancelPreviewPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ((ServerPlayer) ctx.player());
            if (player != null) {
                //尝试取消玩家的挖掘预览和竞技场预览
                net.the_last_sword.event.ServerEventHandler.cancelMiningPreview(player);
                net.the_last_sword.event.ServerEventHandler.cancelArenaPreview(player);
            }
        });
    }
}
