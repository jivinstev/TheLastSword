package net.the_last_sword.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.the_last_sword.util.EntityUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

// 26.2: Minecraft.setScreen moved to Gui.setScreen (Minecraft keeps only setScreenAndShow) -- same body, same HEAD
// argument, and Gui still builds the DeathScreen for a null screen inside it, as Minecraft did. The player is read
// from Minecraft. (Class keeps its name: the mixin config lists it.)
@Mixin(Gui.class)
public class MinecraftMixin {

    //受保护玩家直接将死亡界面替换为null
    @ModifyVariable(method = "setScreen", at = @At("HEAD"), argsOnly = true)
    private Screen theLastSword$preventDeathScreen(Screen screen) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (screen instanceof DeathScreen && player != null && EntityUtil.hasProtection(player)) {
            return null;
        }
        return screen;
    }
}
