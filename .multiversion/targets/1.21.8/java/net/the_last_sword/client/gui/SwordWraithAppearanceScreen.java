package net.the_last_sword.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySpawnReason;
import net.the_last_sword.entity.TheLastEndSwordWraithAppearance;
import net.the_last_sword.entity.TheLastEndEntity;
import net.the_last_sword.entity.TheLastEndSwordWraithEntity;
import net.the_last_sword.init.ModEntities;
import net.the_last_sword.network.NetworkHandler;
import net.the_last_sword.network.SetWraithAppearancePacket;

public class SwordWraithAppearanceScreen extends Screen {
    private static final int FRAME_WIDTH = 120;
    private static final int FRAME_HEIGHT = 140;
    private final InteractionHand hand;
    private TheLastEndSwordWraithAppearance appearance;
    private TheLastEndSwordWraithEntity preview;
    private int frameLeft;
    private int frameTop;

    public SwordWraithAppearanceScreen(InteractionHand hand, TheLastEndSwordWraithAppearance appearance) {
        super(Component.translatable("gui.the_last_sword.sword_wraith_appearance.title"));
        this.hand = hand;
        this.appearance = appearance;
    }

    @Override
    protected void init() {
        frameLeft = (width - FRAME_WIDTH) / 2;
        frameTop = Math.max(30, (height - FRAME_HEIGHT) / 2 - 12);

        addRenderableWidget(Button.builder(Component.literal("<"), button -> select(appearance.previous()))
                .bounds(frameLeft - 30, frameTop + FRAME_HEIGHT / 2 - 10, 20, 20)
                .build());
        addRenderableWidget(Button.builder(Component.literal(">"), button -> select(appearance.next()))
                .bounds(frameLeft + FRAME_WIDTH + 10, frameTop + FRAME_HEIGHT / 2 - 10, 20, 20)
                .build());

        rebuildPreview();
    }

    private void select(TheLastEndSwordWraithAppearance selected) {
        appearance = selected;
        rebuildPreview();
        NetworkHandler.sendToServer(new SetWraithAppearancePacket(hand, selected));
    }

    private void rebuildPreview() {
        if (minecraft == null || minecraft.level == null) {
            preview = null;
            return;
        }
        preview = ModEntities.THE_LAST_END_SWORD_WRAITH.get().create(minecraft.level, EntitySpawnReason.LOAD);
        if (preview != null) {
            preview.setAppearance(appearance);
            preview.setAppearancePreview(true);
            preview.setAnimationState(TheLastEndEntity.STATE_IDLE);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (preview != null) {
            preview.tickCount++;
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        //1.21.6: Screen.renderWithTooltip draws the background before render; a second renderBackground blurs twice and throws
        guiGraphics.drawCenteredString(font, title, width / 2, frameTop - 20, 0xFFFFFF);
        guiGraphics.fill(frameLeft - 2, frameTop - 2, frameLeft + FRAME_WIDTH + 2,
                frameTop + FRAME_HEIGHT + 2, 0xFF6F5A8A);
        guiGraphics.fill(frameLeft, frameTop, frameLeft + FRAME_WIDTH,
                frameTop + FRAME_HEIGHT, 0xCC120F1A);

        if (preview != null) {
            guiGraphics.enableScissor(frameLeft + 1, frameTop + 1,
                    frameLeft + FRAME_WIDTH - 1, frameTop + FRAME_HEIGHT - 1);
            int bottom = frameTop + FRAME_HEIGHT - 8;
            renderPreviewFollowsMouse(guiGraphics,
                    width / 2 - 50, bottom - 100, width / 2 + 50, bottom, 48,
                    0.0625F, width / 2.0F - mouseX, frameTop + FRAME_HEIGHT / 2.0F - mouseY, preview);
            guiGraphics.disableScissor();
        }

        guiGraphics.drawCenteredString(font, Component.translatable(appearance.getNameKey()),
                width / 2, frameTop + FRAME_HEIGHT + 10, 0xFFFFFF);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // 1.21.8: NeoForge's InventoryScreen.renderEntityInInventory builds the preview's state with the no-argument
    // createRenderState(), which GeckoLib 5.2 answers with null, so the GeckoLib wraith preview crashes the screen. This is
    // vanilla's renderEntityInInventoryFollowsMouse/FollowsAngle/renderEntityInInventory, unchanged but for the state:
    // createRenderState(entity, partialTick) (the path 1.21.10's InventoryScreen itself takes, which GeckoLib fills) plus
    // the packed light GeckoLib 5.2 otherwise records only on the world path.
    private static void renderPreviewFollowsMouse(GuiGraphics graphics, int x1, int y1, int x2, int y2, int scale,
            float yOffset, float mouseX, float mouseY, net.minecraft.world.entity.LivingEntity entity) {
        float cx = (x1 + x2) / 2.0F;
        float cy = (y1 + y2) / 2.0F;
        float angleX = (float) Math.atan((cx - mouseX) / 40.0F);
        float angleY = (float) Math.atan((cy - mouseY) / 40.0F);
        graphics.enableScissor(x1, y1, x2, y2);
        org.joml.Quaternionf pose = new org.joml.Quaternionf().rotateZ((float) Math.PI);
        org.joml.Quaternionf camera = new org.joml.Quaternionf().rotateX(angleY * 20.0F * (float) (Math.PI / 180.0));
        pose.mul(camera);
        float bodyRot = entity.yBodyRot;
        float yRot = entity.getYRot();
        float xRot = entity.getXRot();
        float headRotO = entity.yHeadRotO;
        float headRot = entity.yHeadRot;
        entity.yBodyRot = 180.0F + angleX * 20.0F;
        entity.setYRot(180.0F + angleX * 40.0F);
        entity.setXRot(-angleY * 20.0F);
        entity.yHeadRot = entity.getYRot();
        entity.yHeadRotO = entity.getYRot();
        float entityScale = entity.getScale();
        org.joml.Vector3f translation = new org.joml.Vector3f(0.0F, entity.getBbHeight() / 2.0F + yOffset * entityScale, 0.0F);
        @SuppressWarnings({"rawtypes", "unchecked"})
        net.minecraft.client.renderer.entity.EntityRenderer renderer =
                net.minecraft.client.Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
        @SuppressWarnings("unchecked")
        net.minecraft.client.renderer.entity.state.EntityRenderState state = renderer.createRenderState(entity, 1.0F);
        state.hitboxesRenderState = null;
        // GeckoLib 5.2 captures the entity's light only on the world path (its EntityRenderDispatcher mixin), and its
        // render asks for it: a GUI preview is drawn full-bright, as vanilla's picture-in-picture entity renderer does
        if (state instanceof software.bernie.geckolib.renderer.base.GeoRenderState geo) {
            geo.addGeckolibData(software.bernie.geckolib.constant.DataTickets.PACKED_LIGHT,
                    net.minecraft.client.renderer.LightTexture.FULL_BRIGHT);
        }
        graphics.submitEntityRenderState(state, scale / entityScale, translation, pose, camera, x1, y1, x2, y2);
        entity.yBodyRot = bodyRot;
        entity.setYRot(yRot);
        entity.setXRot(xRot);
        entity.yHeadRotO = headRotO;
        entity.yHeadRot = headRot;
        graphics.disableScissor();
    }
}
