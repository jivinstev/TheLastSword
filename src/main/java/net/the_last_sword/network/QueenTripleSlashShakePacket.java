package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.client.ClientPacketHandler;

public record QueenTripleSlashShakePacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<QueenTripleSlashShakePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "queen_triple_slash_shake_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, QueenTripleSlashShakePacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> QueenTripleSlashShakePacket.encode(msg, buf), QueenTripleSlashShakePacket::decode);

    @Override
    public CustomPacketPayload.Type<QueenTripleSlashShakePacket> type() {
        return TYPE;
    }

    public static void encode(QueenTripleSlashShakePacket packet, FriendlyByteBuf buffer) {
    }

    public static QueenTripleSlashShakePacket decode(FriendlyByteBuf buffer) {
        return new QueenTripleSlashShakePacket();
    }

    public static void handle(QueenTripleSlashShakePacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) ClientPacketHandler.triggerQueenTripleSlashScreenShake(); });
    }
}
