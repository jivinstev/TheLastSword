package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// 龙甲整套能量状态同步包（服务端→客户端）
public record DragonArmorEnergyStatusPacket(long currentEnergy, long maxEnergy, long consumptionPerTick) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DragonArmorEnergyStatusPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "dragon_armor_energy_status_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, DragonArmorEnergyStatusPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> DragonArmorEnergyStatusPacket.encode(msg, buf), DragonArmorEnergyStatusPacket::decode);

    @Override
    public CustomPacketPayload.Type<DragonArmorEnergyStatusPacket> type() {
        return TYPE;
    }

    public static void encode(DragonArmorEnergyStatusPacket msg, FriendlyByteBuf buf) {
        buf.writeLong(msg.currentEnergy);
        buf.writeLong(msg.maxEnergy);
        buf.writeLong(msg.consumptionPerTick);
    }

    public static DragonArmorEnergyStatusPacket decode(FriendlyByteBuf buf) {
        return new DragonArmorEnergyStatusPacket(buf.readLong(), buf.readLong(), buf.readLong());
    }

    public static void handle(DragonArmorEnergyStatusPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.updateDragonArmorEnergyStatus(
                        msg.currentEnergy, msg.maxEnergy, msg.consumptionPerTick); });
    }
}
