package net.the_last_sword.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.ItemNbt;
import net.the_last_sword.network.NetworkHandler;
import net.the_last_sword.network.OpenLastEndScrollPacket;
import net.the_last_sword.network.OpenLastEndScrollPacket.PaperNoteEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class TheLastEndScroll extends Item {

    private static final String RUINED_VILLAGE_X_KEY = "RuinedVillageX";
    private static final String RUINED_VILLAGE_Z_KEY = "RuinedVillageZ";
    private static final int RUINED_VILLAGE_SEARCH_RADIUS = 256;
    private static final TagKey<Structure> RUINED_VILLAGE = TagKey.create(
        Registries.STRUCTURE,
        Identifier.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "ruined_village")
    );

    public TheLastEndScroll() {
        super(net.the_last_sword.mv.RegIds.stamp(new Item.Properties())
            .stacksTo(1)
            .fireResistant()
            .rarity(Rarity.EPIC)
        );
    }

    //右键打开教程书GUI
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            CompoundTag tag = ItemNbt.getOrCreateTag(itemStack);
            if (!tag.contains(RUINED_VILLAGE_X_KEY)
                    || !tag.contains(RUINED_VILLAGE_Z_KEY)) {
                BlockPos ruinedVillage = ((ServerLevel) level).findNearestMapStructure(
                    RUINED_VILLAGE,
                    serverPlayer.blockPosition(),
                    RUINED_VILLAGE_SEARCH_RADIUS,
                    false
                );
                if (ruinedVillage != null) {
                    tag.putInt(RUINED_VILLAGE_X_KEY, ruinedVillage.getX());
                    tag.putInt(RUINED_VILLAGE_Z_KEY, ruinedVillage.getZ());
                }
            }

            boolean hasLocation = tag.contains(RUINED_VILLAGE_X_KEY)
                && tag.contains(RUINED_VILLAGE_Z_KEY);
            NetworkHandler.sendToPlayer(new OpenLastEndScrollPacket(
                hasLocation,
                tag.getIntOr(RUINED_VILLAGE_X_KEY, 0),
                tag.getIntOr(RUINED_VILLAGE_Z_KEY, 0),
                collectPaperNotes(serverPlayer)
            ), serverPlayer);
        }

        return InteractionResult.SUCCESS;
    }

    //从玩家收集数据组装已收集纸条列表
    private static List<PaperNoteEntry> collectPaperNotes(ServerPlayer player) {
        List<PaperNoteEntry> notes = new ArrayList<>();
        for (String noteId : PaperNote.getCollectedNoteIds(player)) {
            Identifier id = Identifier.tryParse(noteId);
            Item item = id == null ? null : BuiltInRegistries.ITEM.getValue(id);
            if (item instanceof PaperNote note) {
                notes.add(new PaperNoteEntry(note.getNameKey(), note.getContentKey()));
            }
        }
        return notes;
    }

    //添加物品描述
    @Override
    public void appendHoverText(ItemStack stack, net.minecraft.world.item.Item.TooltipContext level, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
        builder.accept(Component.translatable("item_tooltip_lore.the_last_sword.the_last_end_scroll")
            .withStyle(ChatFormatting.GRAY));
    }
}
