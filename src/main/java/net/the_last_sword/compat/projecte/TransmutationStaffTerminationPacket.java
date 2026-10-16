package net.the_last_sword.compat.projecte;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.compat.projecte.client.TransmutationStaffTerminationRenderer;

public record TransmutationStaffTerminationPacket(ResourceLocation dimension, Vec3 position, float radius) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<TransmutationStaffTerminationPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "transmutation_staff_termination_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, TransmutationStaffTerminationPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> TransmutationStaffTerminationPacket.encode(msg, buf), TransmutationStaffTerminationPacket::decode);

    @Override
    public CustomPacketPayload.Type<TransmutationStaffTerminationPacket> type() {
        return TYPE;
    }

    public static void encode(TransmutationStaffTerminationPacket packet, FriendlyByteBuf buffer) {
        buffer.writeResourceLocation(packet.dimension());
        buffer.writeDouble(packet.position().x);
        buffer.writeDouble(packet.position().y);
        buffer.writeDouble(packet.position().z);
        buffer.writeFloat(packet.radius());
    }

    public static TransmutationStaffTerminationPacket decode(FriendlyByteBuf buffer) {
        return new TransmutationStaffTerminationPacket(buffer.readResourceLocation(),
            new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble()), buffer.readFloat());
    }

    public static void handle(TransmutationStaffTerminationPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) TransmutationStaffTerminationRenderer.receive(packet); });
    }
}
