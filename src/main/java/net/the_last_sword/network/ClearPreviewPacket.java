package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

//服务器发送给客户端的清除挖掘预览包
public class ClearPreviewPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ClearPreviewPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "clear_preview_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClearPreviewPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> msg.encode(buf), ClearPreviewPacket::decode);

    @Override
    public CustomPacketPayload.Type<ClearPreviewPacket> type() {
        return TYPE;
    }

    public ClearPreviewPacket() {}

    //解码
    public static ClearPreviewPacket decode(FriendlyByteBuf buf) {
        return new ClearPreviewPacket();
    }

    //编码
    public void encode(FriendlyByteBuf buf) {}

    //处理
    public static void handle(ClearPreviewPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.clearPreviews(); });
    }
}
