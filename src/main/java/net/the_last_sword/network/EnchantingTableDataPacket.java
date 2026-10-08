package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

// 龙水晶附魔台能量数据同步包（服务端→客户端）
public class EnchantingTableDataPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<EnchantingTableDataPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "enchanting_table_data_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantingTableDataPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> EnchantingTableDataPacket.encode(msg, buf), EnchantingTableDataPacket::decode);

    @Override
    public CustomPacketPayload.Type<EnchantingTableDataPacket> type() {
        return TYPE;
    }

    private final int containerId;
    private final int energy;
    private final int maxEnergy;
    private final int totalPowerTime;

    public EnchantingTableDataPacket(int containerId, int energy, int maxEnergy, int totalPowerTime) {
        this.containerId = containerId;
        this.energy = energy;
        this.maxEnergy = maxEnergy;
        this.totalPowerTime = totalPowerTime;
    }

    public static void encode(EnchantingTableDataPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.containerId);
        buf.writeInt(msg.energy);
        buf.writeInt(msg.maxEnergy);
        buf.writeInt(msg.totalPowerTime);
    }

    public static EnchantingTableDataPacket decode(FriendlyByteBuf buf) {
        return new EnchantingTableDataPacket(buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void handle(EnchantingTableDataPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.syncEnchantingTable(
                        msg.containerId, msg.energy, msg.maxEnergy, msg.totalPowerTime); });
    }
}
