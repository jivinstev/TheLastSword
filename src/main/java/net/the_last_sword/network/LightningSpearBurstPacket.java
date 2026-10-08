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
import net.the_last_sword.client.renderer.LightningSpearBurstRenderer;

public record LightningSpearBurstPacket(ResourceLocation dimension, Vec3 position, float radius, long seed) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<LightningSpearBurstPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "lightning_spear_burst_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LightningSpearBurstPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> LightningSpearBurstPacket.encode(msg, buf), LightningSpearBurstPacket::decode);

    @Override
    public CustomPacketPayload.Type<LightningSpearBurstPacket> type() {
        return TYPE;
    }

    public static void encode(LightningSpearBurstPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.dimension());
        buffer.writeDouble(packet.position().x);
        buffer.writeDouble(packet.position().y);
        buffer.writeDouble(packet.position().z);
        buffer.writeFloat(packet.radius());
        buffer.writeLong(packet.seed());
    }

    public static LightningSpearBurstPacket decode(FriendlyByteBuf buffer) {
        return new LightningSpearBurstPacket(buffer.readResourceLocation(),
                new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()),
                buffer.readFloat(), buffer.readLong());
    }

    public static void handle(LightningSpearBurstPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) LightningSpearBurstRenderer.receive(packet); });
    }
}
