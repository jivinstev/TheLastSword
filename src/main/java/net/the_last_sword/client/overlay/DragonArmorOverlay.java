package net.the_last_sword.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.DragonArmorEnergyStatus;
import net.the_last_sword.configuration.DefenceConfig;
import net.the_last_sword.configuration.DefenceConfigData;
import net.the_last_sword.item.DragonArmorItem;

import java.util.Locale;

//龙之盔甲全套 HUD 叠加层
public class DragonArmorOverlay {

    public static final int ENERGY_DISPLAY_BASE_Y = 30;

    //叠加层材质
    public static final Identifier OVERLAY_TEXTURE =
        Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "textures/screens/dragon_armor_overlay.png");

    //在所有原版 HUD 渲染之后渲染叠加层
    public static void onRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
        //在 HOTBAR 渲染之后渲染，确保在最上层
        if (!event.getName().equals(VanillaGuiLayers.HOTBAR)) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        //检查是否穿戴全套龙之盔甲
        if (!DragonArmorItem.isFullSet(player)) return;

        DefenceConfigData.PerceptionModule perception = DefenceConfig.getPerceptionModule();
        if (!perception.enableHud
                && !perception.showArmorEnergy
                && !perception.showEnergyConsumption) return;

        if (perception.enableHud) {
            renderOverlay(event.getGuiGraphics());
        }
        if (perception.showArmorEnergy || perception.showEnergyConsumption) {
            renderEnergyStatus(event.getGuiGraphics(), mc);
        }
    }

    //渲染龙之盔甲叠加层
    private static void renderOverlay(GuiGraphicsExtractor gui) {
        int screenWidth = gui.guiWidth();
        int screenHeight = gui.guiHeight();

        net.the_last_sword.mv.LegacyGl.disableDepthTest();
        net.the_last_sword.mv.LegacyGl.depthMask(false);
        net.the_last_sword.mv.LegacyGl.enableBlend();
        net.the_last_sword.mv.LegacyGl.defaultBlendFunc();
        net.the_last_sword.mv.LegacyGl.setShader(net.the_last_sword.mv.LegacyGl.Vanilla.POSITION_TEX);
        net.the_last_sword.mv.LegacyGl.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        //绘制全屏叠加层
        gui.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, OVERLAY_TEXTURE, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);

        net.the_last_sword.mv.LegacyGl.depthMask(true);
        net.the_last_sword.mv.LegacyGl.enableDepthTest();
        net.the_last_sword.mv.LegacyGl.disableBlend();
        net.the_last_sword.mv.LegacyGl.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    private static void renderEnergyStatus(GuiGraphicsExtractor gui, Minecraft mc) {
        DefenceConfigData.PerceptionModule perception = DefenceConfig.getPerceptionModule();
        DefenceConfigData.HudOffset offset = DefenceConfig.getHudOffset("dragon_armor_energy_display");
        int y = ENERGY_DISPLAY_BASE_Y + offset.y;

        if (perception.showArmorEnergy) {
            String percentage = String.format(Locale.ROOT, "%.1f",
                    DragonArmorEnergyStatus.getEnergyPercentage());
            Component text = Component.translatable(
                    "gui.the_last_sword.perception_hud.current_energy", percentage);
            gui.text(mc.font,
                    text,
                    gui.guiWidth() / 2 - mc.font.width(text) / 2 + offset.x,
                    y, 0xFFFFFF, true);
            y += mc.font.lineHeight + 2;
        }

        if (perception.showEnergyConsumption) {
            Component text = Component.translatable(
                    "gui.the_last_sword.perception_hud.total_consumption",
                    DragonArmorEnergyStatus.getConsumptionPerTick());
            gui.text(mc.font,
                    text,
                    gui.guiWidth() / 2 - mc.font.width(text) / 2 + offset.x,
                    y, 0xFFFFFF, true);
        }
    }
}
