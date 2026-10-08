package net.the_last_sword.network;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;

//同步唤灵GUI魂石数据网络包（服务端→客户端）
public class SyncSummonGuiPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncSummonGuiPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "sync_summon_gui_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncSummonGuiPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> SyncSummonGuiPacket.encode(msg, buf), SyncSummonGuiPacket::decode);

    @Override
    public CustomPacketPayload.Type<SyncSummonGuiPacket> type() {
        return TYPE;
    }

    private final ItemStack soulStone;

    public SyncSummonGuiPacket(ItemStack soulStone) {
        this.soulStone = soulStone;
    }

    //编码
    public static void encode(SyncSummonGuiPacket msg, RegistryFriendlyByteBuf buf) {
        Tag tag = msg.soulStone.save(buf.registryAccess());
        buf.writeNbt(tag);
    }

    //解码
    public static SyncSummonGuiPacket decode(RegistryFriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        ItemStack soulStone = ItemStack.EMPTY;
        if (tag != null) {
            soulStone = ItemStack.parseOptional(buf.registryAccess(), tag);
        }
        return new SyncSummonGuiPacket(soulStone);
    }

    //处理
    public static void handle(SyncSummonGuiPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.syncSummonGui(msg.soulStone); });
    }
}
