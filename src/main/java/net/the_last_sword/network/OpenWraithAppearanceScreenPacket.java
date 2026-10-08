package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.client.ClientPacketHandler;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;

public class OpenWraithAppearanceScreenPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenWraithAppearanceScreenPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "open_wraith_appearance_screen_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenWraithAppearanceScreenPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> OpenWraithAppearanceScreenPacket.encode(msg, buf), OpenWraithAppearanceScreenPacket::decode);

    @Override
    public CustomPacketPayload.Type<OpenWraithAppearanceScreenPacket> type() {
        return TYPE;
    }

    private final InteractionHand hand;
    private final TheLastEndSwordWraithAppearance appearance;

    public OpenWraithAppearanceScreenPacket(InteractionHand hand, TheLastEndSwordWraithAppearance appearance) {
        this.hand = hand;
        this.appearance = appearance;
    }

    public static void encode(OpenWraithAppearanceScreenPacket message, FriendlyByteBuf buffer) {
        buffer.writeEnum(message.hand);
        buffer.writeEnum(message.appearance);
    }

    public static OpenWraithAppearanceScreenPacket decode(FriendlyByteBuf buffer) {
        return new OpenWraithAppearanceScreenPacket(
                buffer.readEnum(InteractionHand.class),
                buffer.readEnum(TheLastEndSwordWraithAppearance.class));
    }

    public static void handle(OpenWraithAppearanceScreenPacket message, IPayloadContext context) {
        context.enqueueWork(() -> ClientPacketHandler.openWraithAppearanceScreen(message.hand, message.appearance));
    }
}
