package net.marioood.bbiotec.screen;

import net.marioood.bbiotec.BBioTec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, BBioTec.MODID);

    public static final RegistryObject<MenuType<EvisceratorMenu>> EVISCERATOR =
            MENUS.register("eviscerator_menu", () -> IForgeMenuType.create(EvisceratorMenu::new));

    public static final RegistryObject<MenuType<IrradiatorMenu>> IRRADIATOR =
            MENUS.register("irradiator_menu", () -> IForgeMenuType.create(IrradiatorMenu::new));

    public static void register(IEventBus eventBus) {
        MENUS.register(eventBus);
    }
}
