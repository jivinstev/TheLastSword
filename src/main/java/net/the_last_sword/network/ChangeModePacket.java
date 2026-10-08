package net.the_last_sword.network;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.item.DragonSword;
import net.the_last_sword.ItemNbt;
import net.the_last_sword.item.TheLastSword;
import net.the_last_sword.test.UltraTestSwordItem;
import net.the_last_sword.util.nbt.ItemModeHelper;

//模式切换网络包
public class ChangeModePacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<ChangeModePacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "change_mode_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ChangeModePacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> ChangeModePacket.encode(msg, buf), ChangeModePacket::decode);

    @Override
    public CustomPacketPayload.Type<ChangeModePacket> type() {
        return TYPE;
    }

    public ChangeModePacket() {
    }

    //解码
    public static ChangeModePacket decode(FriendlyByteBuf buf) {
        return new ChangeModePacket();
    }

    //编码
    public static void encode(ChangeModePacket msg, FriendlyByteBuf buf) {
    }

    //处理
    public static void handle(ChangeModePacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ((ServerPlayer) ctx.player());
            if (player == null) return;

            ItemStack stack = player.getMainHandItem();
            if (stack.isEmpty()) return;

            //检查物品是否支持模式切换（通过检查是否有Mode NBT）
            if (!ItemNbt.hasTag(stack) || !ItemNbt.getTag(stack).contains("the_last_sword.mode")) {
                return;
            }

            //获取当前模式和最大模式数
            int currentMode = ItemModeHelper.getMode(stack);
            int maxModes = ItemModeHelper.getMaxModes(stack);

            if (maxModes <= 1) {
                return; //只有一个模式不需要切换
            }

            //切换到下一个模式
            ItemModeHelper.cycleMode(stack, maxModes);
            int newMode = ItemModeHelper.getMode(stack);

            //刷新容器，让手持物品 slot 更新到客户端
            player.inventoryMenu.broadcastChanges();

            //获取模式翻译键
            String modeKey = getModeTranslationKey(stack, newMode);

            //客户端聊天提示
            player.displayClientMessage(
                    Component.translatable("item_tooltip.the_last_sword.mode")
                            .append(Component.translatable(modeKey)),
                    true
            );

            //播放音效
            ResourceLocation snd = ResourceLocation.parse("entity.ender_dragon.flap");
            player.level().playSound(null, player.blockPosition(),
                    BuiltInRegistries.SOUND_EVENT.get(snd),
                    SoundSource.PLAYERS, 1f, 1f);
        });
    }

    //根据物品类型和模式ID获取翻译键
    private static String getModeTranslationKey(ItemStack stack, int mode) {
        //检查是否是最终之剑
        if (stack.getItem() instanceof TheLastSword) {
            return switch (mode) {
                case 0 -> "item_tooltip.the_last_sword.normal_mode";
                case 1 -> "item_tooltip.the_last_sword.powerful_mining_mode";
                case 2 -> "item_tooltip.the_last_sword.summon_entity_mode";
                default -> "item_tooltip.the_last_sword.normal_mode";
            };
        }

        //检查是否是龙之剑
        if (stack.getItem() instanceof DragonSword) {
            return switch (mode) {
                case 0 -> "item_tooltip.the_last_sword.normal_mode";
                case 1 -> "item_tooltip.the_last_sword.summon_entity_mode";
                default -> "item_tooltip.the_last_sword.normal_mode";
            };
        }

        //检查是否是究极测试剑
        if (stack.getItem() instanceof UltraTestSwordItem) {
            return switch (mode) {
                case 0 -> "item_tooltip.the_last_sword.powerful_range_attack_mode";
                case 1 -> "item_tooltip.the_last_sword.mob_battle_mode";
                default -> "item_tooltip.the_last_sword.powerful_range_attack_mode";
            };
        }

        //默认返回普通模式
        return "item_tooltip.the_last_sword.normal_mode";
    }
}
