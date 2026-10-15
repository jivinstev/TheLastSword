package net.the_last_sword.client;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.the_last_sword.TheLastSwordMod;
import net.the_last_sword.init.ModItems;

// 1.21.4 only: 1.21.1's DragonCrystalArmorItem.getArmorTexture (layer_1 for helmet/chestplate/boots, layer_2 for
// leggings) is now the client extension's getArmorTexture. The equipment asset equipment/dragon_crystal_armor.json
// (the material's asset id) supplies the humanoid and humanoid_leggings layers it is asked for.
@EventBusSubscriber(modid = TheLastSwordMod.MOD_ID, value = Dist.CLIENT)
public final class DragonCrystalArmorClientExtensions {
    private static final ResourceLocation LAYER_1 =
            ResourceLocation.parse("the_last_sword:textures/models/armor/dragon_crystal_armor_layer_1.png");
    private static final ResourceLocation LAYER_2 =
            ResourceLocation.parse("the_last_sword:textures/models/armor/dragon_crystal_armor_layer_2.png");

    private DragonCrystalArmorClientExtensions() {
    }

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
            @Override
            public ResourceLocation getArmorTexture(ItemStack stack, EquipmentClientInfo.LayerType type,
                                                    EquipmentClientInfo.Layer layer, ResourceLocation _default) {
                return type == EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS ? LAYER_2 : LAYER_1;
            }
        }, ModItems.DRAGON_CRYSTAL_ARMOR_HELMET.get(), ModItems.DRAGON_CRYSTAL_ARMOR_CHESTPLATE.get(),
                ModItems.DRAGON_CRYSTAL_ARMOR_LEGGINGS.get(), ModItems.DRAGON_CRYSTAL_ARMOR_BOOTS.get());
    }
}
