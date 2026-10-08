package net.the_last_sword.network;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.client.gui.menu.SummonWraithGuiMenu;

//打开唤灵GUI网络包（客户端→服务端）
public class OpenSummonGuiPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<OpenSummonGuiPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "open_summon_gui_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenSummonGuiPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> OpenSummonGuiPacket.encode(msg, buf), OpenSummonGuiPacket::decode);

    @Override
    public CustomPacketPayload.Type<OpenSummonGuiPacket> type() {
        return TYPE;
    }

    public OpenSummonGuiPacket() {
    }

    //编码
    public static void encode(OpenSummonGuiPacket msg, FriendlyByteBuf buf) {
        //无需编码任何数据
    }

    //解码
    public static OpenSummonGuiPacket decode(FriendlyByteBuf buf) {
        return new OpenSummonGuiPacket();
    }

    //处理
    public static void handle(OpenSummonGuiPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ((ServerPlayer) ctx.player());
            if (player == null) {
                return;
            }

            //安全检查：防止任意区块生成
            if (!player.level().hasChunkAt(player.blockPosition())) {
                return;
            }

            //打开GUI
            BlockPos pos = player.blockPosition();
            player.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.translatable("gui.the_last_sword.summon_wraith_gui");
                }

                @Override
                public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                    FriendlyByteBuf buffer = new FriendlyByteBuf(Unpooled.buffer());
                    buffer.writeBlockPos(pos);
                    return new SummonWraithGuiMenu(id, inventory, buffer);
                }
            }, pos);
        });
    }
}
