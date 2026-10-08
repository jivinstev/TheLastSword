package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;

// 感知扫描结果同步包（服务端→客户端）
public class PerceptionScanPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PerceptionScanPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "perception_scan_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PerceptionScanPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> PerceptionScanPacket.encode(msg, buf), PerceptionScanPacket::decode);

    @Override
    public CustomPacketPayload.Type<PerceptionScanPacket> type() {
        return TYPE;
    }

    public enum ScanType {
        HOSTILE(0xFF0000),
        FRIENDLY(0x00FF00),
        NEUTRAL(0xFFFF00);

        public final int color;

        ScanType(int color) {
            this.color = color;
        }
    }

    private final Map<Integer, ScanType> scannedEntities;
    private final int glowDurationSeconds;

    public PerceptionScanPacket(Map<Integer, ScanType> scannedEntities, int glowDurationSeconds) {
        this.scannedEntities = scannedEntities;
        this.glowDurationSeconds = glowDurationSeconds;
    }

    public static void encode(PerceptionScanPacket msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.glowDurationSeconds);
        buf.writeInt(msg.scannedEntities.size());
        for (var entry : msg.scannedEntities.entrySet()) {
            buf.writeInt(entry.getKey());
            buf.writeByte(entry.getValue().ordinal());
        }
    }

    public static PerceptionScanPacket decode(FriendlyByteBuf buf) {
        int duration = buf.readInt();
        int size = buf.readInt();
        Map<Integer, ScanType> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            int entityId = buf.readInt();
            ScanType type = ScanType.values()[buf.readByte()];
            map.put(entityId, type);
        }
        return new PerceptionScanPacket(map, duration);
    }

    public static void handle(PerceptionScanPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.updatePerceptionScan(
                        msg.scannedEntities, msg.glowDurationSeconds); });
    }
}
