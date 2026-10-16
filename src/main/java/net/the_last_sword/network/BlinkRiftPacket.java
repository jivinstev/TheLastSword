package net.the_last_sword.network;

import java.util.UUID;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.client.renderer.BlinkRiftRenderer;

public record BlinkRiftPacket(ResourceLocation dimension, UUID caster, Vec3 position, float yaw, int phase) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<BlinkRiftPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "blink_rift_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BlinkRiftPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> BlinkRiftPacket.encode(msg, buf), BlinkRiftPacket::decode);

    @Override
    public CustomPacketPayload.Type<BlinkRiftPacket> type() {
        return TYPE;
    }

    public static void encode(BlinkRiftPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.dimension());
        buffer.writeUUID(packet.caster());
        buffer.writeDouble(packet.position().x);
        buffer.writeDouble(packet.position().y);
        buffer.writeDouble(packet.position().z);
        buffer.writeFloat(packet.yaw());
        buffer.writeVarInt(packet.phase());
    }

    public static BlinkRiftPacket decode(FriendlyByteBuf buffer) {
        return new BlinkRiftPacket(buffer.readResourceLocation(), buffer.readUUID(),
            new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
            buffer.readFloat(), buffer.readVarInt());
    }

    public static void handle(BlinkRiftPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) BlinkRiftRenderer.receive(packet); });
    }
}
