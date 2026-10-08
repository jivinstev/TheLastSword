package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.ArrayList;
import java.util.List;

//服务端确认卷轴记录的被毁村庄坐标与已收集纸条后打开客户端GUI
public class OpenLastEndScrollPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenLastEndScrollPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "open_last_end_scroll_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenLastEndScrollPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> OpenLastEndScrollPacket.encode(msg, buf), OpenLastEndScrollPacket::decode);

    @Override
    public CustomPacketPayload.Type<OpenLastEndScrollPacket> type() {
        return TYPE;
    }

    //已收集纸条条目（名字键+内容键）
    public record PaperNoteEntry(String nameKey, String contentKey) {
    }

    private final boolean hasLocation;
    private final int x;
    private final int z;
    private final List<PaperNoteEntry> collectedNotes;

    public OpenLastEndScrollPacket(boolean hasLocation, int x, int z, List<PaperNoteEntry> collectedNotes) {
        this.hasLocation = hasLocation;
        this.x = x;
        this.z = z;
        this.collectedNotes = collectedNotes;
    }

    public static void encode(OpenLastEndScrollPacket message, FriendlyByteBuf buffer) {
        buffer.writeBoolean(message.hasLocation);
        if (message.hasLocation) {
            buffer.writeInt(message.x);
            buffer.writeInt(message.z);
        }
        buffer.writeCollection(message.collectedNotes, (buf, entry) -> {
            buf.writeUtf(entry.nameKey());
            buf.writeUtf(entry.contentKey());
        });
    }

    public static OpenLastEndScrollPacket decode(FriendlyByteBuf buffer) {
        boolean hasLocation = buffer.readBoolean();
        int x = hasLocation ? buffer.readInt() : 0;
        int z = hasLocation ? buffer.readInt() : 0;
        List<PaperNoteEntry> collectedNotes = buffer.readCollection(ArrayList::new,
            buf -> new PaperNoteEntry(buf.readUtf(), buf.readUtf()));
        return new OpenLastEndScrollPacket(hasLocation, x, z, collectedNotes);
    }

    public static void handle(OpenLastEndScrollPacket message, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.openLastEndScroll(
                        message.hasLocation, message.x, message.z, message.collectedNotes); });
    }
}
