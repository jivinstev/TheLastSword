package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.dialogue.NpcDialogueManager;

public record NpcDialogueChoicePacket(int optionIndex) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<NpcDialogueChoicePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "npc_dialogue_choice_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, NpcDialogueChoicePacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> NpcDialogueChoicePacket.encode(msg, buf), NpcDialogueChoicePacket::decode);

    @Override
    public CustomPacketPayload.Type<NpcDialogueChoicePacket> type() {
        return TYPE;
    }

    public static final int CLOSE_DIALOGUE = -1;

    public static void encode(NpcDialogueChoicePacket message, FriendlyByteBuf buffer) {
        buffer.writeVarInt(message.optionIndex);
    }

    public static NpcDialogueChoicePacket decode(FriendlyByteBuf buffer) {
        return new NpcDialogueChoicePacket(buffer.readVarInt());
    }

    public static void handle(NpcDialogueChoicePacket message, IPayloadContext context) {
        ServerPlayer player = ((ServerPlayer) context.player());
        context.enqueueWork(() -> {
            if (player != null) {
                NpcDialogueManager.select(player, message.optionIndex);
            }
        });
    }
}
