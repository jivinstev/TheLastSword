package net.the_last_sword.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.item.DragonSoulLanternItem;
import net.the_last_sword.item.DragonSword;
import net.the_last_sword.item.TheLastSword;
import net.the_last_sword.util.nbt.ItemModeHelper;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
public final class SummonInputHandler {
    private static boolean summonUsedWhileHeld;

    private SummonInputHandler() {
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onInteraction(InputEvent.InteractionKeyMappingTriggered event) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (!event.isUseItem() || player == null || minecraft.level == null) return;
        if (!isSummonAction(player, player.getItemInHand(event.getHand()))) return;

        // 双手共用一次按下，持续按住或换手不能将刚召出的剑灵立即唤回。
        if (summonUsedWhileHeld) {
            event.setCanceled(true);
            event.setSwingHand(false);
        } else {
            summonUsedWhileHeld = true;
        }
    }

    private static boolean isSummonAction(Player player, ItemStack stack) {
        if (stack.getItem() instanceof DragonSoulLanternItem) return !player.isCrouching();
        if (player.getOffhandItem().is(Items.SHIELD)
                && TheLastSwordConfiguration.getBlockCancelUseSafely()) return false;
        if (stack.getItem() instanceof TheLastSword) return ItemModeHelper.getMode(stack) == 2;
        return stack.getItem() instanceof DragonSword && ItemModeHelper.getMode(stack) != 0;
    }

    // 原始释放事件覆盖同一 tick 内松开再按下，也支持重新绑定使用键。
    @SubscribeEvent
    public static void onKey(InputEvent.Key event) {
        if (event.getAction() == GLFW.GLFW_RELEASE
                && Minecraft.getInstance().options.keyUse.matches(event.getKey(), event.getScanCode())) {
            summonUsedWhileHeld = false;
        }
    }

    @SubscribeEvent(receiveCanceled = true)
    public static void onMouse(InputEvent.MouseButton.Pre event) {
        if (event.getAction() == GLFW.GLFW_RELEASE
                && Minecraft.getInstance().options.keyUse.matchesMouse(event.getButton())) {
            summonUsedWhileHeld = false;
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!summonUsedWhileHeld) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null || minecraft.level == null || !minecraft.options.keyUse.isDown()) {
            summonUsedWhileHeld = false;
        }
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        summonUsedWhileHeld = false;
    }
}
