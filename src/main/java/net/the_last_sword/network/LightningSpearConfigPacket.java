package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.client.LightningSpearClientSettings;
import net.the_last_sword.configuration.LightningSpearSettings;

public record LightningSpearConfigPacket(LightningSpearSettings settings) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<LightningSpearConfigPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "lightning_spear_config_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, LightningSpearConfigPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> LightningSpearConfigPacket.encode(msg, buf), LightningSpearConfigPacket::decode);

    @Override
    public CustomPacketPayload.Type<LightningSpearConfigPacket> type() {
        return TYPE;
    }

    public static void encode(LightningSpearConfigPacket packet, FriendlyByteBuf buffer) {
        LightningSpearSettings settings = packet.settings();
        buffer.writeVarInt(settings.cooldownTicks());
        buffer.writeVarInt(settings.chargeTicks());
        buffer.writeDouble(settings.range());
        buffer.writeDouble(settings.burstSize());
        buffer.writeDouble(settings.damage());
        buffer.writeVarInt(settings.slowLevel());
        buffer.writeVarInt(settings.slowTicks());
    }

    public static LightningSpearConfigPacket decode(FriendlyByteBuf buffer) {
        return new LightningSpearConfigPacket(new LightningSpearSettings(buffer.readVarInt(), buffer.readVarInt(),
            buffer.readDouble(), buffer.readDouble(), buffer.readDouble(), buffer.readVarInt(), buffer.readVarInt()));
    }

    public static void handle(LightningSpearConfigPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) LightningSpearClientSettings.receive(packet.settings()); });
    }
}
