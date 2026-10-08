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

import java.util.UUID;

public record JustifiedDefenceFlashPacket(int entityId, UUID entityUuid) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<JustifiedDefenceFlashPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "justified_defence_flash_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, JustifiedDefenceFlashPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> JustifiedDefenceFlashPacket.encode(msg, buf), JustifiedDefenceFlashPacket::decode);

    @Override
    public CustomPacketPayload.Type<JustifiedDefenceFlashPacket> type() {
        return TYPE;
    }

    public static void encode(JustifiedDefenceFlashPacket packet, FriendlyByteBuf buffer) {
        buffer.writeVarInt(packet.entityId);
        buffer.writeUUID(packet.entityUuid);
    }

    public static JustifiedDefenceFlashPacket decode(FriendlyByteBuf buffer) {
        return new JustifiedDefenceFlashPacket(buffer.readVarInt(), buffer.readUUID());
    }

    public static void handle(JustifiedDefenceFlashPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) ClientPacketHandler.triggerJustifiedDefenceFlash(
                        packet.entityId, packet.entityUuid); });
    }
}
