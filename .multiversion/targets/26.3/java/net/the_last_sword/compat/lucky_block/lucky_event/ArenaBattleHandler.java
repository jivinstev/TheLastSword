package net.the_last_sword.compat.lucky_block.lucky_event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.util.Prediction;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;

import java.util.UUID;

//竞技场对战追踪 - 给参战双方的 persistentData 打 UUID 互指标签, 任一方死亡时清除双方标签; 若玩家击杀对手则给予 64 附魔金苹果
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID)
public final class ArenaBattleHandler {

    private static final String TAG_KEY = "TLSArenaOpponent";
    private static final int REWARD_COUNT = 64;

    private ArenaBattleHandler() {}

    public static void tagParticipants(LivingEntity a, LivingEntity b) {
        a.getPersistentData().putIntArray(TAG_KEY, net.minecraft.core.UUIDUtil.uuidToIntArray(b.getUUID()));
        b.getPersistentData().putIntArray(TAG_KEY, net.minecraft.core.UUIDUtil.uuidToIntArray(a.getUUID()));
    }

    @SubscribeEvent
    public static void onLivingDeath(LivingDeathEvent event) {
        LivingEntity dying = event.getEntity();
        CompoundTag dyingTag = dying.getPersistentData();
        if (!net.the_last_sword.mv.Nbt.hasUuid(dyingTag, TAG_KEY)) return;

        UUID opponentUuid = net.the_last_sword.mv.Nbt.getUuid(dyingTag, TAG_KEY);
        dyingTag.remove(TAG_KEY);

        if (!(dying.level() instanceof ServerLevel serverLevel)) return;
        Entity opponent = serverLevel.getEntity(opponentUuid);
        if (opponent != null) {
            opponent.getPersistentData().remove(TAG_KEY);
        }

        if (dying instanceof Player) return;
        if (!(opponent instanceof Player player)) return;

        ItemStack reward = new ItemStack(Items.ENCHANTED_GOLDEN_APPLE, REWARD_COUNT);
        player.addItem(reward);
        if (!reward.isEmpty()) {
            player.drop(reward, false, Prediction.SERVER_ONLY);
        }
    }
}
