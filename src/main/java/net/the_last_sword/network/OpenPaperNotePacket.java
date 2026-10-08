package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

//服务端确认纸条信息后打开客户端阅读GUI
public class OpenPaperNotePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenPaperNotePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "open_paper_note_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenPaperNotePacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> OpenPaperNotePacket.encode(msg, buf), OpenPaperNotePacket::decode);

    @Override
    public CustomPacketPayload.Type<OpenPaperNotePacket> type() {
        return TYPE;
    }

    private final String noteId;
    private final String nameKey;
    private final String guiContentKey;
    private final boolean collected;

    public OpenPaperNotePacket(String noteId, String nameKey, String guiContentKey, boolean collected) {
        this.noteId = noteId;
        this.nameKey = nameKey;
        this.guiContentKey = guiContentKey;
        this.collected = collected;
    }

    public static void encode(OpenPaperNotePacket message, FriendlyByteBuf buffer) {
        buffer.writeUtf(message.noteId);
        buffer.writeUtf(message.nameKey);
        buffer.writeUtf(message.guiContentKey);
        buffer.writeBoolean(message.collected);
    }

    public static OpenPaperNotePacket decode(FriendlyByteBuf buffer) {
        return new OpenPaperNotePacket(
            buffer.readUtf(),
            buffer.readUtf(),
            buffer.readUtf(),
            buffer.readBoolean()
        );
    }

    public static void handle(OpenPaperNotePacket message, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.openPaperNote(
                        message.noteId, message.nameKey, message.guiContentKey, message.collected); });
    }
}
