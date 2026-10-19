package net.the_last_sword.client.gui;

import net.the_last_sword.mv.GLFW; // 26.3: SDL key/button numbers (CATALOG V102)
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.the_last_sword.client.gui.menu.SummonWraithGuiMenu;

import java.util.HashMap;

public class SummonWraithGuiScreen extends AbstractContainerScreen<SummonWraithGuiMenu> {
    private final static HashMap<String, Object> guistate = SummonWraithGuiMenu.guistate;
    private final Level world;
    private final int x, y, z;
    private final Player entity;

    public SummonWraithGuiScreen(SummonWraithGuiMenu container, Inventory inventory, Component text) {
        //26.1: imageWidth/imageHeight are final; the lower versions' 176 x 240 goes to the sized constructor
        super(container, inventory, text, 176, 240);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;

    }

    private static final Identifier texture = Identifier.parse("the_last_sword:textures/screens/summon_wraith_gui.png");

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public void extractBackground(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
        //the dimmed world backdrop 1.21.11's render() drew (Screen.renderBackground) is Screen.extractBackground on 26.1,
        //which vanilla container screens call first (AbstractFurnaceScreen)
        super.extractBackground(guiGraphics, mouseX, mouseY, partialTicks);
        guiGraphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, Identifier.parse("the_last_sword:textures/screens/summon_wraith_gui_background.png"), this.leftPos + 28, this.topPos + -38, 0, 0, 120, 186, 120, 186);

    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (keyEvent.key() == GLFW.GLFW_KEY_ESCAPE) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(keyEvent);
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void init() {
        super.init();
    }
}
