package net.the_last_sword.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.the_last_sword.recipe.DragonCrystalSmithingRecipe;
import net.the_last_sword.recipe.DragonCrystalSmithingSerializer;

import java.util.ArrayList;
import java.util.List;

//服务端向客户端全量同步龙水晶锻造配方
public class SyncDragonCrystalRecipesPacket implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncDragonCrystalRecipesPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("the_last_sword", "sync_dragon_crystal_recipes_packet"));
    public static final StreamCodec<RegistryFriendlyByteBuf, SyncDragonCrystalRecipesPacket> STREAM_CODEC =
            StreamCodec.of((buf, msg) -> SyncDragonCrystalRecipesPacket.encode(msg, buf), SyncDragonCrystalRecipesPacket::decode);

    @Override
    public CustomPacketPayload.Type<SyncDragonCrystalRecipesPacket> type() {
        return TYPE;
    }

    private static final int MAX_RECIPE_COUNT = 4096;
    private static final DragonCrystalSmithingSerializer SERIALIZER = new DragonCrystalSmithingSerializer();

    private final List<DragonCrystalSmithingRecipe> recipes;

    public SyncDragonCrystalRecipesPacket(List<DragonCrystalSmithingRecipe> recipes) {
        this.recipes = List.copyOf(recipes);
    }

    public static void encode(SyncDragonCrystalRecipesPacket message, RegistryFriendlyByteBuf buffer) {
        buffer.writeVarInt(message.recipes.size());
        for (DragonCrystalSmithingRecipe recipe : message.recipes) {
            buffer.writeResourceLocation(recipe.getId());
            SERIALIZER.streamCodec().encode(buffer, recipe);
        }
    }

    public static SyncDragonCrystalRecipesPacket decode(RegistryFriendlyByteBuf buffer) {
        int recipeCount = buffer.readVarInt();
        if (recipeCount < 0 || recipeCount > MAX_RECIPE_COUNT) {
            throw new IllegalArgumentException("Invalid dragon crystal recipe count: " + recipeCount);
        }

        List<DragonCrystalSmithingRecipe> recipes = new ArrayList<>(recipeCount);
        for (int i = 0; i < recipeCount; i++) {
            ResourceLocation recipeId = buffer.readResourceLocation();
            recipes.add(SERIALIZER.streamCodec().decode(buffer).withId(recipeId));
        }
        return new SyncDragonCrystalRecipesPacket(recipes);
    }

    public static void handle(SyncDragonCrystalRecipesPacket message, IPayloadContext context) {
        context.enqueueWork(() -> { if (FMLEnvironment.dist == Dist.CLIENT) net.the_last_sword.client.ClientPacketHandler.replaceDragonCrystalRecipes(message.recipes); });
    }
}
