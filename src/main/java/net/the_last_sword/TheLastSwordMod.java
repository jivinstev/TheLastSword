package net.the_last_sword;

import net.eca.api.EcaAPI;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.the_last_sword.configuration.TheLastSwordConfigManager;
import net.the_last_sword.configuration.TheLastSwordConfiguration;
import net.the_last_sword.init.ModAttributes;
import net.the_last_sword.init.ModBlockEntities;
import net.the_last_sword.init.ModBlocks;
import net.the_last_sword.init.ModCreativeTabs;
import net.the_last_sword.init.ModEffects;
import net.the_last_sword.init.ModEnchantments;
import net.the_last_sword.init.ModEntities;
import net.the_last_sword.init.ModInstruments;
import net.the_last_sword.init.ModItems;
import net.the_last_sword.init.ModMenus;
import net.the_last_sword.init.ModRecipes;
import net.the_last_sword.init.ModSounds;
import net.the_last_sword.network.NetworkHandler;
import net.the_last_sword.worldgen.ModStructureTypes;
import net.the_last_sword.util.TheLastSwordLogger;
import net.the_last_sword.event.ClientEventHandler;
import net.the_last_sword.event.EnderDragonEvent;
import net.the_last_sword.compat.apotheosis.ApotheosisCompat;

import java.util.PriorityQueue;

@Mod(TheLastSwordMod.MOD_ID)
public class TheLastSwordMod {
    public static final String MOD_ID = "the_last_sword";

    //服务器任务调度系统
    private static final PriorityQueue<ScheduledTask> workQueue = new PriorityQueue<>();
    private static final Object queueLock = new Object();
    private static long currentServerTick = 0;

    //计划任务记录
    private record ScheduledTask(Runnable action, long executionTick) implements Comparable<ScheduledTask> {
        @Override
        public int compareTo(ScheduledTask other) {
            return Long.compare(this.executionTick, other.executionTick);
        }
    }

    public TheLastSwordMod(IEventBus modEventBus, ModContainer container) {
        ModAttributes.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEnchantments.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModEntities.register(modEventBus);
        ModMenus.register(modEventBus);
        ModRecipes.register(modEventBus);
        ModCreativeTabs.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        ModInstruments.register(modEventBus);
        ModStructureTypes.register(modEventBus);

        //注册内置资源包
        modEventBus.addListener(this::addPackFinders);
        modEventBus.addListener(EnderDragonEvent::onEntityAttributeModification);

        //神化mod（Apotheosis）兼容：通过IMC声明剑类类别
        modEventBus.register(new ApotheosisCompat());

        //注册网络包
        modEventBus.addListener(NetworkHandler::register);

        container.registerConfig(
                ModConfig.Type.COMMON,
                TheLastSwordConfiguration.SPEC,
                "TheLastSword-common.toml"
        );

        TheLastSwordConfigManager.initializeConfig();

        //初始化 ECA API 黑名单 - 保护最终之剑的实体数据字段
        initializeEcaProtection();
    }

    //初始化 ECA API 保护机制
    private void initializeEcaProtection() {
        try {
            //添加黑名单关键词，防止 ECA 的阶段2扫描修改这些字段
            EcaAPI.addHealthBlacklistKeyword("HEAL_BAN_TIME");
            EcaAPI.addHealthBlacklistKeyword("IS_PROTECTED");

            TheLastSwordLogger.info("ECA API protection initialized - TLS entity data fields are now protected");
        } catch (Exception e) {
            TheLastSwordLogger.error("Failed to initialize ECA API protection", e);
        }
    }

    //注册内置资源包
    private void addPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(MOD_ID, "classical_texture"),
                    PackType.CLIENT_RESOURCES,
                    Component.literal("The Last Sword Classical Texture Pack"),
                    PackSource.BUILT_IN,
                    false,
                    Pack.Position.TOP
            );
        }
    }

    //添加服务器任务到队列
    public static void queueServerWork(int delayTicks, Runnable action) {
        synchronized (queueLock) {
            workQueue.add(new ScheduledTask(action, currentServerTick + delayTicks));
        }
    }

    //服务器任务调度处理器
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.GAME)
    public static class ServerTaskHandler {
        @SubscribeEvent
        public static void onServerTick(ServerTickEvent.Post event) {
            synchronized (queueLock) {
                currentServerTick++;

                //执行所有到期任务
                while (!workQueue.isEmpty() && workQueue.peek().executionTick() <= currentServerTick) {
                    ScheduledTask task = workQueue.poll();
                    try {
                        task.action().run();
                    } catch (Exception e) {
                        TheLastSwordLogger.error("Error executing scheduled task", e);
                    }
                }
            }
        }
    }
}
