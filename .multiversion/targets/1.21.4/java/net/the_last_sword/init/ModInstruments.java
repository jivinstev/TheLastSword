package net.the_last_sword.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Instrument;
import net.neoforged.bus.api.IEventBus;
import net.the_last_sword.TheLastSwordMod;

public final class ModInstruments {

    //与原版山羊角一致的吹奏时长与传播距离
    public static final int USE_DURATION = 140;
    public static final float RANGE = 256.0F;

    //拜龙教号角，音色沿用原版袭击号角
    //1.21.2 made minecraft:instrument a datapack registry: a DeferredRegister entry is never bound and the server fails
    //to load registries. The instrument is the data file data/the_last_sword/instrument/dragon_cult_horn.json
    //(sound event.raid.horn, use_duration 7.0 s = USE_DURATION ticks, range RANGE, the same translation key).

    //号角物品可用的乐器池
    public static final TagKey<Instrument> DRAGON_CULT_HORNS =
            TagKey.create(Registries.INSTRUMENT,
                    ResourceLocation.fromNamespaceAndPath(TheLastSwordMod.MOD_ID, "dragon_cult_horns"));

    private ModInstruments() {
    }

    public static void register(IEventBus eventBus) {
        //nothing to register in code on 1.21.2+: the instrument comes from data
    }
}
