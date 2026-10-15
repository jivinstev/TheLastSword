package net.the_last_sword.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
        super(container, inventory, text);
        this.world = container.world;
        this.x = container.x;
        this.y = container.y;
        this.z = container.z;
        this.entity = container.entity;
        this.imageWidth = 176;
        this.imageHeight = 240;
    }

    private static final ResourceLocation texture = ResourceLocation.parse("the_last_sword:textures/screens/summon_wraith_gui.png");

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int gx, int gy) {
        guiGraphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, texture, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight, this.imageWidth, this.imageHeight);

        guiGraphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, ResourceLocation.parse("the_last_sword:textures/screens/summon_wraith_gui_background.png"), this.leftPos + 28, this.topPos + -38, 0, 0, 120, 186, 120, 186);

    }

    @Override
    public boolean keyPressed(KeyEvent keyEvent) {
        if (keyEvent.key() == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(keyEvent);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
    }

    @Override
    public void init() {
        super.init();
    }
}
