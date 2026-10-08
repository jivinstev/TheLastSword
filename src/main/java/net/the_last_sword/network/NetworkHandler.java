package net.the_last_sword.network;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

//网络包管理器
public class NetworkHandler {

    private static final String PROTOCOL_VERSION = "12";

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    //注册所有网络包
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PROTOCOL_VERSION);
        registrar.playToClient(LightningSpearConfigPacket.TYPE, LightningSpearConfigPacket.STREAM_CODEC, LightningSpearConfigPacket::handle);
        registrar.playToClient(LightningSpearBurstPacket.TYPE, LightningSpearBurstPacket.STREAM_CODEC, LightningSpearBurstPacket::handle);
        registrar.playToClient(OpenWraithAppearanceScreenPacket.TYPE, OpenWraithAppearanceScreenPacket.STREAM_CODEC, OpenWraithAppearanceScreenPacket::handle);
        registrar.playToServer(SetWraithAppearancePacket.TYPE, SetWraithAppearancePacket.STREAM_CODEC, SetWraithAppearancePacket::handle);
        registrar.playToClient(DangerousSkillPreviewPacket.TYPE, DangerousSkillPreviewPacket.STREAM_CODEC, DangerousSkillPreviewPacket::handle);
        registrar.playToClient(LostWraithEndStrikeEffectPacket.TYPE, LostWraithEndStrikeEffectPacket.STREAM_CODEC, LostWraithEndStrikeEffectPacket::handle);
        registrar.playToClient(QueenExecutionCameraPacket.TYPE, QueenExecutionCameraPacket.STREAM_CODEC, QueenExecutionCameraPacket::handle);
        registrar.playToClient(QueenTripleSlashShakePacket.TYPE, QueenTripleSlashShakePacket.STREAM_CODEC, QueenTripleSlashShakePacket::handle);
        registrar.playToClient(NpcDialogueStatePacket.TYPE, NpcDialogueStatePacket.STREAM_CODEC, NpcDialogueStatePacket::handle);
        registrar.playToServer(NpcDialogueChoicePacket.TYPE, NpcDialogueChoicePacket.STREAM_CODEC, NpcDialogueChoicePacket::handle);
        registrar.playToServer(ChangeModePacket.TYPE, ChangeModePacket.STREAM_CODEC, ChangeModePacket::handle);

        //挖掘预览系统网络包
        registrar.playToClient(PreviewBlocksPacket.TYPE, PreviewBlocksPacket.STREAM_CODEC, PreviewBlocksPacket::handle);

        registrar.playToClient(ClearPreviewPacket.TYPE, ClearPreviewPacket.STREAM_CODEC, ClearPreviewPacket::handle);

        registrar.playToServer(CancelPreviewPacket.TYPE, CancelPreviewPacket.STREAM_CODEC, CancelPreviewPacket::handle);

        //唤灵GUI系统网络包
        registrar.playToServer(OpenSummonGuiPacket.TYPE, OpenSummonGuiPacket.STREAM_CODEC, OpenSummonGuiPacket::handle);

        registrar.playToClient(SyncSummonGuiPacket.TYPE, SyncSummonGuiPacket.STREAM_CODEC, SyncSummonGuiPacket::handle);

        //防御配置同步网络包
        registrar.playToServer(DefenceConfigPacket.TYPE, DefenceConfigPacket.STREAM_CODEC, DefenceConfigPacket::handle);

        //附魔应用网络包
        registrar.playToServer(EnchantmentApplyPacket.TYPE, EnchantmentApplyPacket.STREAM_CODEC, EnchantmentApplyPacket::handle);

        //附魔台能量数据同步包
        registrar.playToClient(EnchantingTableDataPacket.TYPE, EnchantingTableDataPacket.STREAM_CODEC, EnchantingTableDataPacket::handle);

        //感知扫描结果同步包
        registrar.playToClient(PerceptionScanPacket.TYPE, PerceptionScanPacket.STREAM_CODEC, PerceptionScanPacket::handle);

        //龙甲整套能量与耗电速率同步包
        registrar.playToClient(DragonArmorEnergyStatusPacket.TYPE, DragonArmorEnergyStatusPacket.STREAM_CODEC, DragonArmorEnergyStatusPacket::handle);

        //竞技场预览包
        registrar.playToClient(ArenaPreviewPacket.TYPE, ArenaPreviewPacket.STREAM_CODEC, ArenaPreviewPacket::handle);

        //龙套护盾触发包
        registrar.playToClient(DragonShieldPacket.TYPE, DragonShieldPacket.STREAM_CODEC, DragonShieldPacket::handle);

        registrar.playToClient(JustifiedDefenceFlashPacket.TYPE, JustifiedDefenceFlashPacket.STREAM_CODEC, JustifiedDefenceFlashPacket::handle);

        //龙水晶锻造配方同步包（服务端配置为唯一数据源）
        registrar.playToClient(SyncDragonCrystalRecipesPacket.TYPE, SyncDragonCrystalRecipesPacket.STREAM_CODEC, SyncDragonCrystalRecipesPacket::handle);

        //终焉卷轴打开及被毁村庄坐标同步
        registrar.playToClient(OpenLastEndScrollPacket.TYPE, OpenLastEndScrollPacket.STREAM_CODEC, OpenLastEndScrollPacket::handle);

        //纸条阅读GUI打开包
        registrar.playToClient(OpenPaperNotePacket.TYPE, OpenPaperNotePacket.STREAM_CODEC, OpenPaperNotePacket::handle);

        //纸条收集确认包
        registrar.playToServer(ConfirmPaperNotePacket.TYPE, ConfirmPaperNotePacket.STREAM_CODEC, ConfirmPaperNotePacket::handle);
    }

    //发送到服务端
    public static <MSG extends CustomPacketPayload> void sendToServer(MSG message) {
        PacketDistributor.sendToServer(message);
    }

    //发送到特定玩家
    public static <MSG extends CustomPacketPayload> void sendToPlayer(MSG message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }

    //发送到追踪该实体的所有玩家（包括单人游戏）
    public static <MSG extends CustomPacketPayload> void sendToTrackingClients(MSG message, Entity entity) {
        if (entity.level() instanceof ServerLevel serverLevel) {
            PacketDistributor.sendToPlayersTrackingEntity(entity, message);
        }
    }

    //发送到追踪实体的玩家，并在实体为玩家时包含其自身客户端
    public static <MSG extends CustomPacketPayload> void sendToTrackingClientsAndSelf(MSG message, Entity entity) {
        if (entity.level() instanceof ServerLevel) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, message);
        }
    }
}
