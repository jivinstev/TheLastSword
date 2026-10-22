package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.the_last_sword.mv.IEnergyStorage;
import net.the_last_sword.ItemNbt;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.util.nbt.ItemEnergyStorage;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

public class ZeroPointEngine extends Item implements ICurioItem {
    private static final String CHARGING_ENABLED_TAG = "the_last_sword.charging_enabled";

    public ZeroPointEngine() {
        super(net.the_last_sword.mv.RegIds.stamp(new Item.Properties()).stacksTo(1).fireResistant().rarity(Rarity.EPIC));
    }

    // 能量能力通过 RegisterCapabilitiesEvent 注册（Capabilities.EnergyStorage.ITEM，见 ModCapabilities）
    public static IEnergyStorage createEnergyStorage(ItemStack stack) {
        return new ItemEnergyStorage(stack, TheLastSwordConfiguration::getZeroPointEngineMaxEnergySafely);
    }

    private static Optional<IEnergyStorage> energyOf(ItemStack stack) {
        return Optional.ofNullable(net.the_last_sword.mv.LegacyEnergy.item(stack));
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        if (slotChanged || oldStack.getItem() != newStack.getItem()
                || oldStack.getCount() != newStack.getCount()) return true;
        if (oldStack == newStack) return false;

        // 持续发电会同步新的物品数据，能量变化不应反复触发收起和抬手。
        CompoundTag oldTag = ItemNbt.getOrCreateTag(oldStack);
        CompoundTag newTag = ItemNbt.getOrCreateTag(newStack);
        oldTag.remove("the_last_sword.energy");
        newTag.remove("the_last_sword.energy");
        return !oldTag.equals(newTag);
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        return energyOf(stack).map(energy -> {
            int capacity = energy.getMaxEnergyStored();
            return capacity <= 0 ? 0 : Math.min(13, Math.round(13.0F * energy.getEnergyStored() / capacity));
        }).orElse(0);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return 0xBF00FF;
    }

    private boolean isChargingEnabled(ItemStack stack) {
        return ItemNbt.hasTag(stack) && ItemNbt.getTag(stack).getBooleanOr(CHARGING_ENABLED_TAG, false);
    }

    private Component chargingStatus(ItemStack stack) {
        return Component.translatable("item_tooltip.the_last_sword.ancient_energy_core.charging_"
                + (isChargingEnabled(stack) ? "enabled" : "disabled"));
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!player.isShiftKeyDown()) return InteractionResult.PASS;
        if (!level.isClientSide()) {
            boolean enable = !isChargingEnabled(stack);
            ItemNbt.update(stack, tag -> tag.putBoolean(CHARGING_ENABLED_TAG, enable));
            player.sendOverlayMessage(chargingStatus(stack));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void inventoryTick(ItemStack stack, ServerLevel level, Entity entity, EquipmentSlot slot) {
        super.inventoryTick(stack, level, entity, slot);
        tickEnergy(stack, entity);
    }

    @Override
    public void curioTick(SlotContext context, ItemStack stack) {
        tickEnergy(stack, context.entity());
    }

    private void tickEnergy(ItemStack stack, Entity entity) {
        if (entity.level().isClientSide() || !(entity instanceof Player player)) return;
        energyOf(stack).ifPresent(source -> {
            source.receiveEnergy(TheLastSwordConfiguration.getZeroPointEngineGenerationRateSafely(), false);
            if (!isChargingEnabled(stack) || source.getEnergyStored() <= 0) return;
            int rate = TheLastSwordConfiguration.getZeroPointEngineChargeRateSafely();
            if (rate <= 0) return;

            List<ItemStack> targets = new ArrayList<>();
            targets.add(player.getItemBySlot(EquipmentSlot.HEAD));
            targets.add(player.getItemBySlot(EquipmentSlot.CHEST));
            targets.add(player.getItemBySlot(EquipmentSlot.LEGS));
            targets.add(player.getItemBySlot(EquipmentSlot.FEET));
            for (int i = 0; i < player.getInventory().getNonEquipmentItems().size(); i++) {
                targets.add(player.getInventory().getNonEquipmentItems().get(i));
            }
            targets.add(player.getOffhandItem());
            CuriosApi.getCuriosInventory(player).ifPresent(handler -> {
                for (var slot : handler.getCurios().values()) {
                    var stacks = slot.getStacks();
                    for (int i = 0; i < stacks.getSlots(); i++) targets.add(stacks.getStackInSlot(i));
                }
            });

            // 少量补电后继续分配本刻余额，避免持续耗能的前排装备阻塞后续目标。
            Set<ItemStack> visited = Collections.newSetFromMap(new IdentityHashMap<>());
            visited.add(stack);
            int remaining = rate;
            for (ItemStack target : targets) {
                if (target.isEmpty() || !visited.add(target)) continue;
                remaining -= chargeItem(source, target, remaining);
                if (remaining <= 0 || source.getEnergyStored() <= 0) break;
            }
        });
    }

    private int chargeItem(IEnergyStorage source, ItemStack target, int limit) {
        return energyOf(target).map(receiver -> {
            if (receiver == source || !receiver.canReceive()
                    || receiver.getEnergyStored() >= receiver.getMaxEnergyStored()) return 0;
            int available = source.extractEnergy(limit, true);
            if (available <= 0) return 0;
            int received = receiver.receiveEnergy(available, false);
            if (received > 0) source.extractEnergy(received, false);
            return Math.max(0, received);
        }).orElse(0);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        energyOf(stack).ifPresent(energy ->
                tooltip.accept(Component.translatable("item_tooltip.the_last_sword.energy")
                        .append(": §a" + energy.getEnergyStored() + " §r/ " + energy.getMaxEnergyStored() + " FE")));
        tooltip.accept(Component.translatable("item_tooltip.the_last_sword.zero_point_engine",
                TheLastSwordConfiguration.getZeroPointEngineGenerationRateSafely(),
                TheLastSwordConfiguration.getZeroPointEngineChargeRateSafely(), chargingStatus(stack)));
        tooltip.accept(Component.translatable("item_tooltip_lore.the_last_sword.zero_point_engine")
                .withStyle(ChatFormatting.GRAY));
    }
}
