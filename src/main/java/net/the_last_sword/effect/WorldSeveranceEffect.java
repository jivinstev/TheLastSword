package net.the_last_sword.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

// 只展示恢复限制，实际持续时间由现世锚度管理器维护。
public class WorldSeveranceEffect extends MobEffect {
    public WorldSeveranceEffect() {
        super(MobEffectCategory.HARMFUL, 0x734D91);
    }
}
