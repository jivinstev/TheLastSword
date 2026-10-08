package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.item.PaperNote;

//玩家点击「我已知晓」，服务端登记收集
public class ConfirmPaperNotePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ConfirmPaperNotePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "confirm_paper_note_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ConfirmPaperNotePacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> ConfirmPaperNotePacket.encode(msg, buf), ConfirmPaperNotePacket::decode);

    @Override
    public CustomPacketPayload.Type<ConfirmPaperNotePacket> type() {
        return TYPE;
    }

    private final String noteId;

    public ConfirmPaperNotePacket(String noteId) {
        this.noteId = noteId;
    }

    public static void encode(ConfirmPaperNotePacket message, FriendlyByteBuf buffer) {
        buffer.writeUtf(message.noteId);
    }

    public static ConfirmPaperNotePacket decode(FriendlyByteBuf buffer) {
        return new ConfirmPaperNotePacket(buffer.readUtf());
    }

    public static void handle(ConfirmPaperNotePacket message, IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = ((ServerPlayer) context.player());
            if (player != null && PaperNote.isValidNoteId(message.noteId)) {
                PaperNote.markCollected(player, message.noteId);
            }
        });
    }
}
