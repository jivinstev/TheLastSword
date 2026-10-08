package net.the_last_sword.init;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;
import org.lwjgl.glfw.GLFW;

//按键映射定义和注册
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModKeyMappings {

    //切换模式按键
    public static final KeyMapping CHANGE_SWORD_MODE = new KeyMapping(
            "key.the_last_sword.change_sword_mode",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_CONTROL,
            "key.categories.the_last_sword"
    );

    //防御配置按键
    public static final KeyMapping OPEN_DEFENCE_CONFIG = new KeyMapping(
            "key.the_last_sword.open_defence_config",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_LEFT_ALT,
            "key.categories.the_last_sword"
    );

    //打开唤灵GUI按键
    public static final KeyMapping OPEN_SUMMON_GUI = new KeyMapping(
            "key.the_last_sword.open_summon_gui",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_Z,
            "key.categories.the_last_sword"
    );

    //注册按键映射
    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(CHANGE_SWORD_MODE);
        event.register(OPEN_DEFENCE_CONFIG);
        event.register(OPEN_SUMMON_GUI);
    }
}
