package net.marioood.bbiotec;

import com.mojang.logging.LogUtils;
import net.marioood.bbiotec.entity.ModBlockEntities;
import net.marioood.bbiotec.screen.EvisceratorScreen;
import net.marioood.bbiotec.screen.IrradiatorScreen;
import net.marioood.bbiotec.screen.ModMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BBioTec.MODID)
public class BBioTec
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "bbiotec";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public BBioTec()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.CELLULOSE);
            event.accept(ModItems.PROTEINS);
            event.accept(ModItems.GLUCOSE_SYRUP);
            event.accept(ModItems.RAW_URANIUM);
            event.accept(ModItems.URANIUM_INGOT);
            event.accept(ModItems.SAFETY_SHEARS);
        }

        if(event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(ModItems.BARBEQUE_SANDWICH);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ModBlocks.INTESTINE);
            event.accept(ModBlocks.CLUMP_OF_PROTEINS);
            event.accept(ModBlocks.CLUMP_OF_LIPIDS);
            event.accept(ModBlocks.LIGHT_SKIN);
            event.accept(ModBlocks.SAND_SKIN);
            event.accept(ModBlocks.HONEY_SKIN);
            event.accept(ModBlocks.DARK_SKIN);
            event.accept(ModBlocks.DECOMPOSING_SKIN);
            event.accept(ModBlocks.GIANT_FLOWER_STEM);
            event.accept(ModBlocks.GIANT_LEAF);
            event.accept(ModBlocks.GIANT_SEED);
            event.accept(ModBlocks.GIANT_THORN);
            event.accept(ModBlocks.TENDRILDENDRON);

            event.accept(ModBlocks.EVISCERATOR);
            event.accept(ModBlocks.IRRADIATOR);
            event.accept(ModBlocks.URANIUM_ORE);
            event.accept(ModBlocks.RAW_URANIUM_BLOCK);
            event.accept(ModBlocks.URANIUM_BLOCK);
        }

        if(event.getTabKey() == CreativeModeTabs.COLORED_BLOCKS) {
            event.accept(ModBlocks.WHITE_PETAL_BLOCK);
            event.accept(ModBlocks.LIGHT_GRAY_PETAL_BLOCK);
            event.accept(ModBlocks.BLACK_PETAL_BLOCK);
            event.accept(ModBlocks.RED_PETAL_BLOCK);
            event.accept(ModBlocks.ORANGE_PETAL_BLOCK);
            event.accept(ModBlocks.YELLOW_PETAL_BLOCK);
            event.accept(ModBlocks.LIGHT_BLUE_PETAL_BLOCK);
            event.accept(ModBlocks.BLUE_PETAL_BLOCK);
            event.accept(ModBlocks.PURPLE_PETAL_BLOCK);
            event.accept(ModBlocks.MAGENTA_PETAL_BLOCK);
            event.accept(ModBlocks.PINK_PETAL_BLOCK);
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.EVISCERATOR.get(), EvisceratorScreen::new);
            MenuScreens.register(ModMenuTypes.IRRADIATOR.get(), IrradiatorScreen::new);
            //ItemBlockRenderTypes.setRenderLayer(ModBlocks.GIANT_THORN.get(), RenderType.cutout());
        }
    }
}
