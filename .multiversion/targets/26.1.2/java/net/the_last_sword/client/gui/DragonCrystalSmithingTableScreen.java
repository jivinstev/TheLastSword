package net.the_last_sword.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.client.gui.menu.DragonCrystalSmithingTableMenu;

public class DragonCrystalSmithingTableScreen extends AbstractContainerScreen<DragonCrystalSmithingTableMenu> {

    private final int bgWidth;
    private final int bgHeight;

    private static final Identifier TEXTURE =
        Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "textures/screens/dragon_crystal_smithing_table_gui.png");

    public DragonCrystalSmithingTableScreen(DragonCrystalSmithingTableMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.bgWidth = 256;
        this.bgHeight = 160;
        this.inventoryLabelY = this.bgHeight - 89;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphicsExtractor guiGraphics, float partialTick, int mouseX, int mouseY) {
        net.the_last_sword.mv.LegacyGl.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        net.the_last_sword.mv.LegacyGl.enableBlend();
        net.the_last_sword.mv.LegacyGl.defaultBlendFunc();

        guiGraphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, TEXTURE, this.leftPos, this.topPos, 0, 0, this.bgWidth, this.bgHeight, this.bgWidth, this.bgHeight);

        net.the_last_sword.mv.LegacyGl.disableBlend();
    }

    @Override
    protected void renderLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        // 不渲染标签
    }
}
