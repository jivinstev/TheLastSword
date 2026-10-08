package net.the_last_sword.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;
import net.the_last_sword.ItemNbt;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import net.the_last_sword.item.SwordSoulStone;
import net.the_last_sword.summon.WraithSummonManager;

import java.util.UUID;

public class SetWraithAppearancePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SetWraithAppearancePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "set_wraith_appearance_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SetWraithAppearancePacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> SetWraithAppearancePacket.encode(msg, buf), SetWraithAppearancePacket::decode);

    @Override
    public CustomPacketPayload.Type<SetWraithAppearancePacket> type() {
        return TYPE;
    }

    private final InteractionHand hand;
    private final TheLastEndSwordWraithAppearance appearance;

    public SetWraithAppearancePacket(InteractionHand hand, TheLastEndSwordWraithAppearance appearance) {
        this.hand = hand;
        this.appearance = appearance;
    }

    public static void encode(SetWraithAppearancePacket message, FriendlyByteBuf buffer) {
        buffer.writeEnum(message.hand);
        buffer.writeEnum(message.appearance);
    }

    public static SetWraithAppearancePacket decode(FriendlyByteBuf buffer) {
        return new SetWraithAppearancePacket(
                buffer.readEnum(InteractionHand.class),
                buffer.readEnum(TheLastEndSwordWraithAppearance.class));
    }

    public static void handle(SetWraithAppearancePacket message, IPayloadContext context) {
        context.enqueueWork(() -> apply(message, ((ServerPlayer) context.player())));
    }

    private static void apply(SetWraithAppearancePacket message, ServerPlayer player) {
        if (player == null) {
            return;
        }
        ItemStack stack = player.getItemInHand(message.hand);
        if (!(stack.getItem() instanceof SwordSoulStone)) {
            return;
        }

        SwordSoulStone.setAppearance(stack, message.appearance);
        player.getInventory().setChanged();
        player.inventoryMenu.broadcastChanges();
        if (ItemNbt.getTag(stack) == null || !ItemNbt.getTag(stack).contains("wraith_uuid")) {
            return;
        }

        UUID wraithUuid;
        try {
            wraithUuid = UUID.fromString(ItemNbt.getTag(stack).getString("wraith_uuid"));
        } catch (IllegalArgumentException exception) {
            return;
        }

        MinecraftServer server = player.getServer();
        if (server == null) {
            return;
        }
        for (ServerLevel level : server.getAllLevels()) {
            Entity entity = level.getEntity(wraithUuid);
            if (entity instanceof TheLastEndSwordWraithEntity wraith) {
                wraith.setAppearance(message.appearance);
                WraithSummonManager.setWraithCustomName(wraith, player);
                return;
            }
        }
    }
}
