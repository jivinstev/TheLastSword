// OVERLAY-ONLY: fork-layer helper for Minecraft 1.21.9+ (not the authors' code).
package net.the_last_sword.mv;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Function;
import java.util.function.IntConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.transfer.TransferPreconditions;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
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

    /** What 1.21.1 read from the stack's {@code Capabilities.EnergyStorage.ITEM}: changes go to this stack. */
    public static @Nullable IEnergyStorage item(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        EnergyHandler handler = Capabilities.Energy.ITEM.getCapability(stack, ItemAccess.forStack(stack));
        return handler == null ? null : IEnergyStorage.of(handler);
    }

    /** What 1.21.1 read from the level's {@code Capabilities.EnergyStorage.BLOCK} at pos and side. */
    public static @Nullable IEnergyStorage block(Level level, BlockPos pos, @Nullable Direction side) {
        EnergyHandler handler = level.getCapability(Capabilities.Energy.BLOCK, pos, side);
        return handler == null ? null : IEnergyStorage.of(handler);
    }

    /**
     * An item energy provider for {@code Capabilities.Energy.ITEM} over a storage that keeps its energy on the stack
     * (1.21.1's {@code (stack, context) -> storage(stack)}). The provider's stack may be a copy (ItemAccess.getCapability
     * passes {@code getResource().toStack()}), so every change is made on a copy of the accessed item and written back
     * by exchanging the access's items for it, in the caller's transaction -- as NeoForge's ItemAccessEnergyHandler
     * writes its data component.
     */
    public static ICapabilityProvider<ItemStack, ItemAccess, EnergyHandler> itemProvider(
            Function<ItemStack, ? extends IEnergyStorage> storage) {
        return (stack, access) -> access == null ? null : new ItemHandler(access, storage);
    }

    /**
     * {@code storage} as a block's {@link EnergyHandler}: each insert or extract changes the storage at once, through
     * its own receiveEnergy/extractEnergy (its limits apply to the state the transaction has made), and an aborted
     * transaction puts the energy back with {@code setEnergy}. One handler per storage, so every lookup in a
     * transaction shares one journal.
     */
    public static @Nullable EnergyHandler handler(@Nullable IEnergyStorage storage, IntConsumer setEnergy) {
        if (storage == null) {
            return null;
        }
        synchronized (HANDLERS) {
            WeakReference<StorageHandler> ref = HANDLERS.get(storage);
            StorageHandler h = ref == null ? null : ref.get();
            if (h == null) {
                h = new StorageHandler(storage, setEnergy);
                HANDLERS.put(storage, new WeakReference<>(h));
            }
            return h;
        }
    }

    // weak both ways: a handler a transaction holds stays (its journal entry references it), the storage is not kept
    private static final Map<IEnergyStorage, WeakReference<StorageHandler>> HANDLERS = new WeakHashMap<>();

    private static final class StorageHandler extends SnapshotJournal<Integer> implements EnergyHandler {
        private final IEnergyStorage storage;
        private final IntConsumer setEnergy;

        StorageHandler(IEnergyStorage storage, IntConsumer setEnergy) {
            this.storage = storage;
            this.setEnergy = setEnergy;
        }

        @Override
        public long getAmountAsLong() {
            return Math.max(0, storage.getEnergyStored());
        }

        @Override
        public long getCapacityAsLong() {
            return Math.max(0, storage.getMaxEnergyStored());
        }

        @Override
        public int insert(int amount, TransactionContext transaction) {
            TransferPreconditions.checkNonNegative(amount);
            if (amount == 0 || !storage.canReceive() || storage.receiveEnergy(amount, true) <= 0) {
                return 0;
            }
            updateSnapshots(transaction);
            return Math.max(0, storage.receiveEnergy(amount, false));
        }

        @Override
        public int extract(int amount, TransactionContext transaction) {
            TransferPreconditions.checkNonNegative(amount);
            if (amount == 0 || !storage.canExtract() || storage.extractEnergy(amount, true) <= 0) {
                return 0;
            }
            updateSnapshots(transaction);
            return Math.max(0, storage.extractEnergy(amount, false));
        }

        @Override
        protected Integer createSnapshot() {
            return storage.getEnergyStored();
        }

        @Override
        protected void revertToSnapshot(Integer snapshot) {
            setEnergy.accept(snapshot);
        }
    }

    private static final class ItemHandler implements EnergyHandler {
        private final ItemAccess access;
        private final Function<ItemStack, ? extends IEnergyStorage> storage;

        ItemHandler(ItemAccess access, Function<ItemStack, ? extends IEnergyStorage> storage) {
            this.access = access;
            this.storage = storage;
        }

        /** The storage over a fresh one-item copy of the accessed item, or null when the access holds nothing. */
        private @Nullable Copy copy() {
            ItemResource resource = access.getResource();
            if (resource.isEmpty() || access.getAmount() <= 0) {
                return null;
            }
            ItemStack stack = resource.toStack(1);
            return new Copy(stack, storage.apply(stack));
        }

        @Override
        public long getAmountAsLong() {
            Copy c = copy();
            return c == null ? 0 : Math.max(0, c.storage().getEnergyStored());
        }

        @Override
        public long getCapacityAsLong() {
            Copy c = copy();
            return c == null ? 0 : Math.max(0, c.storage().getMaxEnergyStored());
        }

        @Override
        public int insert(int amount, TransactionContext transaction) {
            TransferPreconditions.checkNonNegative(amount);
            Copy c = amount == 0 ? null : copy();
            if (c == null || !c.storage().canReceive()) {
                return 0;
            }
            int received = c.storage().receiveEnergy(amount, false);
            return received > 0 && writeBack(c, transaction) ? received : 0;
        }

        @Override
        public int extract(int amount, TransactionContext transaction) {
            TransferPreconditions.checkNonNegative(amount);
            Copy c = amount == 0 ? null : copy();
            if (c == null || !c.storage().canExtract()) {
                return 0;
            }
            int extracted = c.storage().extractEnergy(amount, false);
            return extracted > 0 && writeBack(c, transaction) ? extracted : 0;
        }

        /** Every item of the access becomes the changed copy (1.21.1 kept the energy on the whole stack). */
        private boolean writeBack(Copy c, TransactionContext transaction) {
            int count = access.getAmount();
            return access.exchange(ItemResource.of(c.stack()), count, transaction) == count;
        }

        private record Copy(ItemStack stack, IEnergyStorage storage) {
        }
    }
}
