package net.the_last_sword.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.init.ModAttributes;
import net.the_last_sword.util.EntityUtil;
import net.the_last_sword.util.InventoryProtectionPolicy;
import net.the_last_sword.util.health.PresentWorldAnchorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//玩家相关注入
@Mixin(Player.class)
public class PlayerMixin {

    //1.21.5: Player no longer declares setItemSlot or drop(ItemStack, boolean, boolean) -- both moved to LivingEntity
    //(CATALOG M30) -- so those two injections are in LivingEntityMixin, limited to players

    //拦截死亡时清空背包（keepInventory=false 时调用，取消后物品不掉落不消失）
    @Inject(method = "dropEquipment", at = @At("HEAD"), cancellable = true)
    private void tls$blockDropEquipment(CallbackInfo ci) {
        if (EntityUtil.hasInventoryProtection((Player) (Object) this)) {
            ci.cancel();
        }
    }

    // 持久化现世锚度血量到磁盘
    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void tls$savePresentWorldAnchor(CompoundTag tag, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        CompoundTag pd = player.getPersistentData();
        if ((pd.get(PresentWorldAnchorManager.NBT_ABSOLUTE_DESTRUCTION_PRESENT_WORLD_ANCHOR) instanceof net.minecraft.nbt.NumericTag)) {
            tag.putFloat(PresentWorldAnchorManager.NBT_ABSOLUTE_DESTRUCTION_PRESENT_WORLD_ANCHOR,
                    pd.getFloatOr(PresentWorldAnchorManager.NBT_ABSOLUTE_DESTRUCTION_PRESENT_WORLD_ANCHOR, 0.0F));
        }
        if (pd.contains("tlsIsProtected")) {
            tag.putBoolean("tlsIsProtected", pd.getBooleanOr("tlsIsProtected", false));
        }
        // 持久化肃正防御值
        AttributeInstance shield = player.getAttribute(ModAttributes.JUSTIFIED_DEFENCE);
        if (shield != null && shield.getValue() > 0) {
            tag.putDouble("tlsJustifiedDefence", shield.getValue());
        }
        tag.remove("tlsMaxJustifiedDefence");
    }

    // 从磁盘恢复现世锚度血量
    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void tls$loadPresentWorldAnchor(CompoundTag tag, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        CompoundTag pd = player.getPersistentData();
        if ((tag.get(PresentWorldAnchorManager.NBT_ABSOLUTE_DESTRUCTION_PRESENT_WORLD_ANCHOR) instanceof net.minecraft.nbt.NumericTag)) {
            pd.putFloat(PresentWorldAnchorManager.NBT_ABSOLUTE_DESTRUCTION_PRESENT_WORLD_ANCHOR,
                    tag.getFloatOr(PresentWorldAnchorManager.NBT_ABSOLUTE_DESTRUCTION_PRESENT_WORLD_ANCHOR, 0.0F));
        }
        PresentWorldAnchorManager.syncPresentWorldAnchor(player);
        if (tag.contains("tlsIsProtected")) {
            pd.putBoolean("tlsIsProtected", tag.getBooleanOr("tlsIsProtected", false));
        }
        // 恢复肃正防御值
        if (tag.contains("tlsJustifiedDefence")) {
            AttributeInstance shield = player.getAttribute(ModAttributes.JUSTIFIED_DEFENCE);
            if (shield != null) {
                shield.setBaseValue(tag.getDoubleOr("tlsJustifiedDefence", 0.0));
            }
        }
        AttributeInstance maxShield = player.getAttribute(ModAttributes.MAX_JUSTIFIED_DEFENCE);
        if (maxShield != null) {
            maxShield.setBaseValue(0.0);
        }
        tag.remove("tlsMaxJustifiedDefence");
    }
}
