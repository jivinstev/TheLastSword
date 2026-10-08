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

import java.util.HashSet;
import java.util.Set;

//服务器发送给客户端的挖掘预览方块包
public class PreviewBlocksPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PreviewBlocksPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "preview_blocks_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PreviewBlocksPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> msg.encode(buf), PreviewBlocksPacket::decode);

    @Override
    public CustomPacketPayload.Type<PreviewBlocksPacket> type() {
        return TYPE;
    }

    private final Set<BlockPos> blocks;

    public PreviewBlocksPacket(Set<BlockPos> blocks) {
        this.blocks = new HashSet<>(blocks);
    }

    //解码
    public static PreviewBlocksPacket decode(FriendlyByteBuf buf) {
        int size = buf.readInt();
        Set<BlockPos> blocks = new HashSet<>();
        for (int i = 0; i < size; i++) {
            blocks.add(buf.readBlockPos());
        }
        return new PreviewBlocksPacket(blocks);
    }

    //编码
    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(blocks.size());
        for (BlockPos pos : blocks) {
            buf.writeBlockPos(pos);
        }
    }

    //处理
    public static void handle(PreviewBlocksPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.setMiningPreview(msg.blocks); });
    }
}
