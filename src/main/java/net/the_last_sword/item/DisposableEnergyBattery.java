package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.function.Consumer;

//一次性能量电池：使用后消耗自身，立即为背包内所有FE未满的物品恢复能量
public class DisposableEnergyBattery extends Item {

    public DisposableEnergyBattery() {
        super(net.the_last_sword.mv.RegIds.stamp(new Item.Properties())
            .stacksTo(64)
            .fireResistant()
            .rarity(Rarity.UNCOMMON)
        );
    }

    //最大能量 = 恢复数值（统一配置）
    public static int getMaxEnergy() {
        return TheLastSwordConfiguration.getDisposableEnergyBatteryRestoreAmountSafely();
    }

    private static EnergyHandler energyOf(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        return net.neoforged.neoforge.transfer.access.ItemAccess.forStack(stack).getCapability(Capabilities.Energy.ITEM);
    }

    //插入能量并立即提交
    private static int receiveEnergy(EnergyHandler energy, int amount) {
        try (Transaction tx = Transaction.openRoot()) {
            int inserted = energy.insert(amount, tx);
            tx.commit();
            return inserted;
        }
    }

    private static boolean receiveInto(ItemStack target, int amount) {
        EnergyHandler energy = energyOf(target);
        if (energy == null) {
            return false;
        }
        return receiveEnergy(energy, amount) > 0;
    }

    //默认满电
    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        EnergyHandler energy = energyOf(stack);
        if (energy != null) {
            receiveEnergy(energy, getMaxEnergy());
        }
        return stack;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (level.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        int restoreAmount = TheLastSwordConfiguration.getDisposableEnergyBatteryRestoreAmountSafely();
        boolean anyCharged = false;

        //为背包内所有FE未满的物品恢复能量
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack target = player.getInventory().getItem(i);
            if (target == stack || target.isEmpty()) {
                continue;
            }
            boolean charged = receiveInto(target, restoreAmount);
            if (charged) {
                anyCharged = true;
            }
        }

        //饰品栏（所有Curios槽位）
        anyCharged |= CuriosApi.getCuriosInventory(player).map(handler -> {
            boolean curioCharged = false;
            for (var entry : handler.getCurios().entrySet()) {
                IDynamicStackHandler stacks = entry.getValue().getStacks();
                for (int i = 0; i < stacks.getSlots(); i++) {
                    ItemStack curioStack = stacks.getStackInSlot(i);
                    if (curioStack.isEmpty()) continue;
                    boolean charged = receiveInto(curioStack, restoreAmount);
                    if (charged) curioCharged = true;
                }
            }
            return curioCharged;
        }).orElse(false);

        //仅在确实充能后才消耗电池
        if (anyCharged) {
            stack.shrink(1);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        EnergyHandler energy = energyOf(stack);
        if (energy == null) return 0;
        int maxEnergy = energy.getCapacityAsInt();
        if (maxEnergy == 0) return 0;
        return Math.round(13.0F * energy.getAmountAsInt() / maxEnergy);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        EnergyHandler energy = energyOf(stack);
        if (energy == null) return 0x9B30FF;
        int maxEnergy = energy.getCapacityAsInt();
        if (maxEnergy == 0) return 0x9B30FF;
        float ratio = (float) energy.getAmountAsInt() / maxEnergy;
        if (ratio < 0.25F) {
            return 0xFF0000;
        } else if (ratio < 0.5F) {
            return 0xFF8C00;
        } else if (ratio < 0.75F) {
            return 0x9B30FF;
        } else {
            return 0xBF00FF;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        EnergyHandler energy = energyOf(stack);
        if (energy != null) {
            tooltipAdder.accept(Component.translatable("item_tooltip.the_last_sword.energy")
                .append(": §a" + energy.getAmountAsInt() + " §r/ " + energy.getCapacityAsInt() + " FE"));
        }
        tooltipAdder.accept(Component.translatable("item_tooltip.the_last_sword.disposable_energy_battery",
            TheLastSwordConfiguration.getDisposableEnergyBatteryRestoreAmountSafely()));
        tooltipAdder.accept(Component.translatable("item_tooltip_lore.the_last_sword.disposable_energy_battery")
            .withStyle(ChatFormatting.GRAY));
    }
}
