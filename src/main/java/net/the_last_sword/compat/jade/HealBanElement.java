package net.the_last_sword.compat.jade;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.Element;
import snownee.jade.api.ui.IDisplayHelper;

//实体信息浮标中的禁疗剩余时间使用现世锚度图标，便于区分普通负面效果
public class HealBanElement extends Element {

    private static final Identifier ICON =
            Identifier.fromNamespaceAndPath("the_last_sword", "textures/screens/world_anchor_full.png");
    private static final int ICON_SIZE = 9;
    private static final int LINE_HEIGHT = 10;

    private final String text;

    public HealBanElement(int remainingSeconds) {
        //前缀两空格用于对齐原有生命值行的视觉间距
        this.text = String.format("  %ds", remainingSeconds);
        //Jade 19 (1.21.8): getSize() is gone; an element states its size in the width/height fields it lays out by
        Font font = Minecraft.getInstance().font;
        this.width = ICON_SIZE + font.width(text);
        this.height = LINE_HEIGHT;
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int x, int y, float partialTick) {
        guiGraphics.blit(net.minecraft.client.renderer.RenderPipelines.GUI_TEXTURED, ICON, x, y, 0, 0, ICON_SIZE, ICON_SIZE, ICON_SIZE, ICON_SIZE);
        //沿用浮标主题渲染，保持阴影和文字颜色一致
        IDisplayHelper.get().drawText(guiGraphics, text, x + ICON_SIZE, y, IThemeHelper.get().getNormalColor());
    }

    @Override
    public net.minecraft.network.chat.Component getNarration() {
        return net.minecraft.network.chat.Component.literal(text.trim());
    }
}
