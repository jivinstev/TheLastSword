package net.the_last_sword.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

//服务器发送给客户端的竞技场预览方框包
public class ArenaPreviewPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ArenaPreviewPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "arena_preview_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ArenaPreviewPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> msg.encode(buf), ArenaPreviewPacket::decode);

    @Override
    public CustomPacketPayload.Type<ArenaPreviewPacket> type() {
        return TYPE;
    }

    private final BlockPos minPos;
    private final BlockPos maxPos;

    public ArenaPreviewPacket(BlockPos minPos, BlockPos maxPos) {
        this.minPos = minPos;
        this.maxPos = maxPos;
    }

    //编码
    public void encode(FriendlyByteBuf buf) {
        buf.writeBlockPos(minPos);
        buf.writeBlockPos(maxPos);
    }

    //解码
    public static ArenaPreviewPacket decode(FriendlyByteBuf buf) {
        return new ArenaPreviewPacket(buf.readBlockPos(), buf.readBlockPos());
    }

    //处理
    public static void handle(ArenaPreviewPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.setArenaPreview(msg.minPos, msg.maxPos); });
    }
}
