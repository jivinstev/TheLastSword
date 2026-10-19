package net.the_last_sword.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.the_last_sword.mv.MultiBufferSource;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.network.BlinkRiftPacket;

@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
public final class BlinkRiftRenderer {
    private static final List<Rift> RIFTS = new ArrayList<>();
    private static final Map<UUID, Rift> ACTORS = new HashMap<>();
    private static ClientLevel effectLevel;

    private BlinkRiftRenderer() {}

    public static void receive(BlinkRiftPacket packet) {
        update();
        if (effectLevel == null || !effectLevel.dimension().identifier().equals(packet.dimension())) return;
        if (packet.phase() == 3) {
            ACTORS.remove(packet.caster());
            return;
        }
        if (packet.phase() == 2) {
            ACTORS.remove(packet.caster());
            RIFTS.removeIf(rift -> rift.packet().caster().equals(packet.caster()));
            return;
        }
        if (RIFTS.size() >= 128) RIFTS.remove(0);
        Rift rift = new Rift(packet, effectLevel.getGameTime());
        RIFTS.add(rift);
        ACTORS.put(packet.caster(), rift);
    }

    private static void update() {
        ClientLevel level = Minecraft.getInstance().level;
        if (effectLevel != level) {
            RIFTS.clear();
            ACTORS.clear();
            effectLevel = level;
        }
        if (level != null) {
            RIFTS.removeIf(rift -> level.getGameTime() - rift.start() >= 50);
            ACTORS.values().removeIf(rift -> level.getGameTime() - rift.start() >= 50);
        }
    }

    private static boolean hidden(UUID caster) {
        update();
        Rift rift = ACTORS.get(caster);
        if (rift == null || effectLevel == null) return false;
        long age = effectLevel.getGameTime() - rift.start();
        return rift.packet().phase() == 0 ? age >= 15 : true;
    }

    @SubscribeEvent
    public static void tick(ClientTickEvent.Post event) {
        update();
    }

    @SubscribeEvent
    //1.21.9+: RenderPlayerEvent is typed on the avatar; its render state (AvatarRenderState) carries the entity id
    public static void player(RenderPlayerEvent.Pre<?> event) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level == null) return;
        net.minecraft.world.entity.Entity entity = level.getEntity(event.getRenderState().id);
        if (entity != null && hidden(entity.getUUID())) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void hand(RenderHandEvent event) {
        var player = Minecraft.getInstance().player;
        if (player != null && hidden(player.getUUID())) event.setCanceled(true);
    }

    @SubscribeEvent
    public static void render(RenderLevelStageEvent.AfterTranslucentParticles event) {
        update();
        if (effectLevel == null || RIFTS.isEmpty()) return;
        PoseStack pose = event.getPoseStack();
        //1.21.9+: the stage event carries no camera or partial tick (as LightningSpearBurstRenderer)
        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera().position();
        MultiBufferSource.BufferSource buffers = net.the_last_sword.mv.MultiBufferSource.main();
        for (Rift rift : RIFTS) {
            float age = effectLevel.getGameTime() - rift.start() + Minecraft.getInstance().getDeltaTracker().getGameTimeDeltaPartialTick(false);
            Vec3 offset = rift.packet().position().subtract(camera);
            pose.pushPose();
            pose.translate(offset.x, offset.y, offset.z);
            QueenBlinkRenderUtil.rift(pose, buffers, age, rift.packet().yaw());
            pose.popPose();
        }
        QueenBlinkRenderUtil.finishRiftBatch(buffers);
    }

    private record Rift(BlinkRiftPacket packet, long start) {}
}
