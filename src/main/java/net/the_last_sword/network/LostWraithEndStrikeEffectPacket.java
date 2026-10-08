package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.client.renderer.LostWraithEndStrikeEffectRenderer;

import java.util.UUID;

public record LostWraithEndStrikeEffectPacket(ResourceLocation dimension, int entityId, UUID entityUuid,
                                               Vec3 position, long startTick, long damageTick,
                                               long endTick, boolean active) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<LostWraithEndStrikeEffectPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "lost_wraith_end_strike_effect_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LostWraithEndStrikeEffectPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> LostWraithEndStrikeEffectPacket.encode(msg, buf), LostWraithEndStrikeEffectPacket::decode);

    @Override
    public CustomPacketPayload.Type<LostWraithEndStrikeEffectPacket> type() {
        return TYPE;
    }

    public static void encode(LostWraithEndStrikeEffectPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.dimension);
        buffer.writeVarInt(packet.entityId);
        buffer.writeUUID(packet.entityUuid);
        buffer.writeDouble(packet.position.x);
        buffer.writeDouble(packet.position.y);
        buffer.writeDouble(packet.position.z);
        buffer.writeLong(packet.startTick);
        buffer.writeLong(packet.damageTick);
        buffer.writeLong(packet.endTick);
        buffer.writeBoolean(packet.active);
    }

    public static LostWraithEndStrikeEffectPacket decode(FriendlyByteBuf buffer) {
        return new LostWraithEndStrikeEffectPacket(buffer.readResourceLocation(), buffer.readVarInt(),
                buffer.readUUID(), new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                buffer.readLong(), buffer.readLong(), buffer.readLong(), buffer.readBoolean());
    }

    public static void handle(LostWraithEndStrikeEffectPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) LostWraithEndStrikeEffectRenderer.receive(packet); });
    }
}
