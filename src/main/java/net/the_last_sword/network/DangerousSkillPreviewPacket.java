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
import net.the_last_sword.client.renderer.DangerousSkillPreviewRenderer;

import java.util.UUID;

public record DangerousSkillPreviewPacket(ResourceLocation dimension, int entityId, UUID entityUuid,
                                         Vec3 origin, Vec3 forward, double width, double length,
                                         double height, long endTick, boolean active) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DangerousSkillPreviewPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "dangerous_skill_preview_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DangerousSkillPreviewPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> DangerousSkillPreviewPacket.encode(msg, buf), DangerousSkillPreviewPacket::decode);

    @Override
    public CustomPacketPayload.Type<DangerousSkillPreviewPacket> type() {
        return TYPE;
    }

    public static void encode(DangerousSkillPreviewPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.dimension);
        buffer.writeVarInt(packet.entityId);
        buffer.writeUUID(packet.entityUuid);
        buffer.writeDouble(packet.origin.x);
        buffer.writeDouble(packet.origin.y);
        buffer.writeDouble(packet.origin.z);
        buffer.writeDouble(packet.forward.x);
        buffer.writeDouble(packet.forward.z);
        buffer.writeDouble(packet.width);
        buffer.writeDouble(packet.length);
        buffer.writeDouble(packet.height);
        buffer.writeLong(packet.endTick);
        buffer.writeBoolean(packet.active);
    }

    public static DangerousSkillPreviewPacket decode(FriendlyByteBuf buffer) {
        return new DangerousSkillPreviewPacket(buffer.readResourceLocation(), buffer.readVarInt(),
                buffer.readUUID(), new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                new Vec3(buffer.readDouble(), 0.0, buffer.readDouble()), buffer.readDouble(),
                buffer.readDouble(), buffer.readDouble(), buffer.readLong(), buffer.readBoolean());
    }

    public static void handle(DangerousSkillPreviewPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) DangerousSkillPreviewRenderer.receive(packet); });
    }
}
