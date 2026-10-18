// OVERLAY-ONLY: fork-layer helper for Minecraft 1.21.9+ (not the authors' code).
package net.the_last_sword.mv;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;
import org.jetbrains.annotations.Nullable;

/**
 * NeoForge 1.21.9 replaced the energy capabilities ({@code Capabilities.EnergyStorage.ITEM/BLOCK}, typed on
 * {@link IEnergyStorage}) with {@code Capabilities.Energy.ITEM/BLOCK}, typed on the transactional {@link EnergyHandler}.
 * NeoForge ships the reading direction only ({@link IEnergyStorage#of(EnergyHandler)}); this class adds the
 * providing one, so code written against {@link IEnergyStorage} keeps its storage classes.
 */
public final class LegacyEnergy {
    private LegacyEnergy() {
    }

    /** What 1.21.1 read from the stack's {@code Capabilities.EnergyStorage.ITEM}. */
    public static @Nullable IEnergyStorage item(ItemStack stack) {
        EnergyHandler handler = Capabilities.Energy.ITEM.getCapability(stack, ItemAccess.forStack(stack));
        return handler == null ? null : IEnergyStorage.of(handler);
    }

    /** What 1.21.1 read from the level's {@code Capabilities.EnergyStorage.BLOCK} at pos and side. */
    public static @Nullable IEnergyStorage block(Level level, BlockPos pos, @Nullable Direction side) {
        EnergyHandler handler = level.getCapability(Capabilities.Energy.BLOCK, pos, side);
        return handler == null ? null : IEnergyStorage.of(handler);
    }

    /** An {@link IEnergyStorage} provided as an {@link EnergyHandler} (null stays null). */
    public static @Nullable EnergyHandler handler(@Nullable IEnergyStorage storage) {
        return storage == null ? null : new Handler(storage);
    }

    /**
     * The storage's energy as a transfer-API handler. A transaction never touches the storage: what it inserts or
     * extracts is kept as a pending change (reverted with the transaction when it is aborted) and applied to the
     * storage with its own receiveEnergy/extractEnergy only when the root transaction commits.
     */
    private static final class Handler extends SnapshotJournal<Integer> implements EnergyHandler {
        private final IEnergyStorage storage;
        private int pending;

        Handler(IEnergyStorage storage) {
            this.storage = storage;
        }

        private long stored() {
            return (long) storage.getEnergyStored() + pending;
        }

        @Override
        public long getAmountAsLong() {
            return Math.max(0L, stored());
        }

        @Override
        public long getCapacityAsLong() {
            return Math.max(0, storage.getMaxEnergyStored());
        }

        @Override
        public int insert(int amount, TransactionContext transaction) {
            TransferPreconditions.checkNonNegative(amount);
            if (amount == 0 || !storage.canReceive()) {
                return 0;
            }
            long room = Math.max(0L, getCapacityAsLong() - stored());
            int accepted = (int) Math.min(Math.min(amount, room), storage.receiveEnergy(amount, true));
            if (accepted > 0) {
                updateSnapshots(transaction);
                pending += accepted;
            }
            return accepted;
        }

        @Override
        public int extract(int amount, TransactionContext transaction) {
            TransferPreconditions.checkNonNegative(amount);
            if (amount == 0 || !storage.canExtract()) {
                return 0;
            }
            int extracted = (int) Math.min(Math.min(amount, getAmountAsLong()), storage.extractEnergy(amount, true));
            if (extracted > 0) {
                updateSnapshots(transaction);
                pending -= extracted;
            }
            return extracted;
        }

        @Override
        protected Integer createSnapshot() {
            return pending;
        }

        @Override
        protected void revertToSnapshot(Integer snapshot) {
            pending = snapshot;
        }

        @Override
        protected void onRootCommit(Integer originalState) {
            int change = pending;
            pending = 0;
            if (change > 0) {
                storage.receiveEnergy(change, false);
            } else if (change < 0) {
                storage.extractEnergy(-change, false);
            }
        }
    }
}
