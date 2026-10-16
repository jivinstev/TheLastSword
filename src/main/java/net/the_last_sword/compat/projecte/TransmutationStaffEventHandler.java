package net.the_last_sword.compat.projecte;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;

@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public final class TransmutationStaffEventHandler {
    private TransmutationStaffEventHandler() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onInteractSpecific(PlayerInteractEvent.EntityInteractSpecific event) {
        InteractionResult result = handleInteraction(event, event.getTarget());
        if (result != null) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onInteract(PlayerInteractEvent.EntityInteract event) {
        InteractionResult result = handleInteraction(event, event.getTarget());
        if (result != null) {
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    // 1.21.1：取消结果只在具体事件上可设，返回结果由调用方写入（null 表示不处理）。
    private static InteractionResult handleInteraction(PlayerInteractEvent event, Entity target) {
        if (!TransmutationStaffBehavior.isStaff(event.getItemStack())) {
            return null;
        }
        if (event.getEntity().getCooldowns().isOnCooldown(event.getItemStack().getItem())) {
            return InteractionResult.FAIL;
        }
        if (TransmutationStaffBehavior.isTermination(event.getItemStack())) {
            return TransmutationStaffBehavior.use(
                event.getLevel(), event.getEntity(), event.getHand()).getResult();
        }
        if (!TransmutationStaffBehavior.isTransmutation(event.getItemStack())) {
            return null;
        }
        // 在实体自身交互前处理，避免交易或骑乘吞掉嬗变操作；已取消的保护事件不会进入此处。
        return TransmutationStaffActions.interactEntity(event.getEntity(), event.getHand(), target);
    }
}
