package net.the_last_sword.network;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.block.entity.DragonCrystalEnchantingTableBlockEntity;
import net.the_last_sword.configuration.TheLastSwordConfiguration;

import java.util.HashMap;
import java.util.Map;

// 附魔应用网络包（支持增加和减少附魔）
public class EnchantmentApplyPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<EnchantmentApplyPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "enchantment_apply_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, EnchantmentApplyPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> EnchantmentApplyPacket.encode(msg, buf), EnchantmentApplyPacket::decode);

    @Override
    public CustomPacketPayload.Type<EnchantmentApplyPacket> type() {
        return TYPE;
    }


    private final BlockPos pos;
    // 值为目标等级，0表示移除
    private final Map<ResourceLocation, Integer> enchantments;

    public EnchantmentApplyPacket(BlockPos pos, Map<ResourceLocation, Integer> enchantments) {
        this.pos = pos;
        this.enchantments = enchantments;
    }

    public static EnchantmentApplyPacket decode(FriendlyByteBuf buf) {
        BlockPos pos = buf.readBlockPos();
        int size = buf.readInt();
        Map<ResourceLocation, Integer> enchantments = new HashMap<>();
        for (int i = 0; i < size; i++) {
            ResourceLocation id = buf.readResourceLocation();
            int level = buf.readInt();
            enchantments.put(id, level);
        }
        return new EnchantmentApplyPacket(pos, enchantments);
    }

    public static void encode(EnchantmentApplyPacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeInt(msg.enchantments.size());
        for (var entry : msg.enchantments.entrySet()) {
            buf.writeResourceLocation(entry.getKey());
            buf.writeInt(entry.getValue());
        }
    }

    public static void handle(EnchantmentApplyPacket msg, IPayloadContext ctx) {
        ctx.enqueueWork(() -> {
            ServerPlayer player = ((ServerPlayer) ctx.player());
            if (player == null) return;

            BlockEntity be = player.level().getBlockEntity(msg.pos);
            if (!(be instanceof DragonCrystalEnchantingTableBlockEntity enchantingTable)) return;

            if (player.distanceToSqr(msg.pos.getX() + 0.5, msg.pos.getY() + 0.5, msg.pos.getZ() + 0.5) > 64) return;

            ItemStack stack = enchantingTable.getItem(1);
            if (stack.isEmpty()) return;

            ItemEnchantments currentEnchants = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
            Map<Enchantment, Integer> existingEnchants = new HashMap<>();
            for (Holder<Enchantment> holder : currentEnchants.keySet()) {
                existingEnchants.put(holder.value(), currentEnchants.getLevel(holder));
            }
            var enchRegistry = player.level().registryAccess().registryOrThrow(Registries.ENCHANTMENT);

            // 分别计算增加消耗和减少返还
            long totalEnergyCost = 0;
            int totalXpReturn = 0;
            Map<Enchantment, Integer> targetLevels = new HashMap<>();

            for (var entry : msg.enchantments.entrySet()) {
                Enchantment ench = enchRegistry.get(entry.getKey());
                if (ench == null) continue;

                int currentLevel = existingEnchants.getOrDefault(ench, 0);
                int targetLevel = Math.max(0, Math.min(255, entry.getValue()));

                if (targetLevel == currentLevel) continue;

                int diff = targetLevel - currentLevel;
                if (diff > 0) {
                    totalEnergyCost += (long) TheLastSwordConfiguration.getEnchantingTableEnchantEnergyCostSafely() * diff;
                } else {
                    totalXpReturn += TheLastSwordConfiguration.getEnchantingTableRemoveXpReturnSafely() * (-diff);
                }
                targetLevels.put(ench, targetLevel);
            }

            if (targetLevels.isEmpty()) return;

            // 检查电量是否足够
            long currentEnergy = enchantingTable.getEnergyStorage().getEnergyStored();
            if (totalEnergyCost > currentEnergy) return;

            // 消耗电量
            if (totalEnergyCost > 0) {
                int toExtract = (int) Math.min(totalEnergyCost, Integer.MAX_VALUE);
                enchantingTable.extractEnergy(toExtract);
            }

            // 应用附魔变更
            for (var entry : targetLevels.entrySet()) {
                if (entry.getValue() == 0) {
                    existingEnchants.remove(entry.getKey());
                } else {
                    existingEnchants.put(entry.getKey(), entry.getValue());
                }
            }
            ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
            for (var entry : existingEnchants.entrySet()) {
                if (entry.getValue() > 0) {
                    mutable.set(enchRegistry.wrapAsHolder(entry.getKey()), entry.getValue());
                }
            }
            stack.set(DataComponents.ENCHANTMENTS, mutable.toImmutable());

            // 返还经验
            if (totalXpReturn > 0) {
                player.giveExperiencePoints(totalXpReturn);
            }

            enchantingTable.setChanged();
        });
    }
}
