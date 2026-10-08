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

public record QueenExecutionCameraPacket(boolean active, float yaw) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<QueenExecutionCameraPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "queen_execution_camera_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, QueenExecutionCameraPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> QueenExecutionCameraPacket.encode(msg, buf), QueenExecutionCameraPacket::decode);

    @Override
    public CustomPacketPayload.Type<QueenExecutionCameraPacket> type() {
        return TYPE;
    }

    public static void encode(QueenExecutionCameraPacket packet, FriendlyByteBuf buffer) {
        buffer.writeBoolean(packet.active());
        buffer.writeFloat(packet.yaw());
    }

    public static QueenExecutionCameraPacket decode(FriendlyByteBuf buffer) {
        return new QueenExecutionCameraPacket(buffer.readBoolean(), buffer.readFloat());
    }

    public static void handle(QueenExecutionCameraPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) ClientPacketHandler.setQueenExecutionCamera(packet.active(), packet.yaw()); });
    }
}
