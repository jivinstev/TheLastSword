package net.the_last_sword.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class ClientEntityLookup {
    public static LivingEntity find(Level level, UUID entityUUID) {
        if (level instanceof ClientLevel clientLevel) {
            for (Entity entity : clientLevel.entitiesForRendering()) {
                if (entity instanceof LivingEntity living && entity.getUUID().equals(entityUUID)) {
                    return living;
                }
            }
        }
        return null;
    }
}
