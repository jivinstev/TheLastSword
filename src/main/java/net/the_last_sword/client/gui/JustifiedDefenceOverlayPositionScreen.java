package net.the_last_sword.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.configuration.DefenceConfig;
import net.the_last_sword.configuration.DefenceConfigData;
import net.the_last_sword.network.DefenceConfigPacket;
import net.the_last_sword.network.NetworkHandler;

public class JustifiedDefenceOverlayPositionScreen extends Screen {
    private static final Identifier ICON_FULL =
        Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "textures/screens/justified_defence_full.png");

    private static final int ICON_SIZE = 9;
    private static final int ICON_SPACING = -8;
    private static final int MAX_ICONS = 10;

    private final Screen parent;
    private int offsetX;
    private int offsetY;
    private boolean dragging = false;
    private int dragStartX;
    private int dragStartY;

    public JustifiedDefenceOverlayPositionScreen(Screen parent) {
        super(Component.translatable("gui.the_last_sword.defence_config.justified_defence_position"));
        this.parent = parent;

        DefenceConfigData.HudOffset offset = DefenceConfig.getHudOffset("justified_defence_overlay");
        this.offsetX = offset.x;
        this.offsetY = offset.y;
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = this.width / 2;
        int bottomY = this.height - 30;

        //保存按钮
        this.addRenderableWidget(Button.builder(
            Component.translatable("gui.the_last_sword.justified_defence_position.save"),
            button -> {
                DefenceConfig.setHudOffset("justified_defence_overlay", offsetX, offsetY);
                DefenceConfig.save();
                NetworkHandler.sendToServer(new DefenceConfigPacket(DefenceConfig.getData()));

                if (this.minecraft != null) {
                    this.minecraft.setScreen(parent);
                }
            }
        ).bounds(centerX - buttonWidth - 5, bottomY, buttonWidth, buttonHeight).build());

        //重置按钮
        this.addRenderableWidget(Button.builder(
            Component.translatable("gui.the_last_sword.justified_defence_position.reset"),
            button -> {
                offsetX = 0;
                offsetY = 0;
            }
        ).bounds(centerX + 5, bottomY, buttonWidth, buttonHeight).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTick) {

        graphics.centeredText(this.font, this.title, this.width / 2, 20, 0xFFFFFFFF);

        graphics.centeredText(
            this.font,
            Component.translatable("gui.the_last_sword.justified_defence_position.hint"),
            this.width / 2,
            40,
            0xFFAAAAAA
        );

        renderJustifiedDefencePreview(graphics);

        graphics.text(
            this.font,
            Component.literal("Offset: X=" + offsetX + ", Y=" + offsetY),
            10,
            10,
            0xFFFFFFFF
        );

        super.extractRenderState(graphics, mouseX, mouseY, partialTick);
    }

    private void renderJustifiedDefencePreview(GuiGraphicsExtractor graphics) {
        int screenWidth = this.width;
        int screenHeight = this.height;

        int baseX = screenWidth / 2 + 91 - ICON_SIZE;
        int baseY = screenHeight - 49;

        int startX = baseX + offsetX;
        int startY = baseY + offsetY;

        int step = -ICON_SPACING;
        int leftX = startX + ICON_SPACING * (MAX_ICONS - 1);

        int totalWidth = (MAX_ICONS - 1) * step + ICON_SIZE;
        int boundingBoxX = leftX - 2;
        int boundingBoxY = startY - 2;
        int boundingBoxWidth = totalWidth + 4;
        int boundingBoxHeight = ICON_SIZE + 4;

        graphics.fill(boundingBoxX, boundingBoxY,
                     boundingBoxX + boundingBoxWidth,
                     boundingBoxY + boundingBoxHeight,
                     dragging ? 0x80FFFF00 : 0x80FFFFFF);

        net.the_last_sword.mv.LegacyGl.enableBlend();

        for (int i = 0; i < MAX_ICONS; i++) {
            int x = leftX + i * step;
            graphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, ICON_FULL, x, startY, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
        }

        net.the_last_sword.mv.LegacyGl.disableBlend();
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        double mouseX = event.x();
        double mouseY = event.y();
        int button = event.buttonInfo().button();
        if (button == 0) {
            int screenWidth = this.width;
            int screenHeight = this.height;

            int baseX = screenWidth / 2 + 91 - ICON_SIZE;
            int baseY = screenHeight - 49;

            int startX = baseX + offsetX;
            int startY = baseY + offsetY;

            int step = -ICON_SPACING;
            int leftX = startX + ICON_SPACING * (MAX_ICONS - 1);
            int totalWidth = (MAX_ICONS - 1) * step + ICON_SIZE;

            int boundingBoxX = leftX - 2;
            int boundingBoxY = startY - 2;
            int boundingBoxWidth = totalWidth + 4;
            int boundingBoxHeight = ICON_SIZE + 4;

            if (mouseX >= boundingBoxX && mouseX <= boundingBoxX + boundingBoxWidth &&
                mouseY >= boundingBoxY && mouseY <= boundingBoxY + boundingBoxHeight) {
                dragging = true;
                dragStartX = (int) mouseX - offsetX;
                dragStartY = (int) mouseY - offsetY;
                return true;
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        int button = event.buttonInfo().button();
        if (button == 0 && dragging) {
            dragging = false;
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dragX, double dragY) {
        double mouseX = event.x();
        double mouseY = event.y();
        if (dragging) {
            offsetX = (int) mouseX - dragStartX;
            offsetY = (int) mouseY - dragStartY;
            return true;
        }
        return super.mouseDragged(event, dragX, dragY);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(parent);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
