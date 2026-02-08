package net.marioood.bbiotec;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
  public static final DeferredRegister<Item> ITEMS =
    DeferredRegister.create(ForgeRegistries.ITEMS, BBioTec.MODID);
    
  public static final RegistryObject<Item> CELLULOSE =
          ITEMS.register("cellulose", () -> new Item(new Item.Properties()));

  public static final RegistryObject<Item> PROTEINS =
          ITEMS.register("proteins", () -> new Item(new Item.Properties()));

  public static final RegistryObject<Item> BARBEQUE_SANDWICH =
          ITEMS.register("barbeque_sandwich", () -> new Item(new Item.Properties().food(ModFoodProperties.BARBEQUE_SANDWICH)));

  public static final RegistryObject<Item> GLUCOSE_SYRUP =
          ITEMS.register("glucose_syrup", () -> new Item(new Item.Properties().food(ModFoodProperties.GLUCOSE_SYRUP)));

  public static final RegistryObject<Item> RAW_URANIUM =
          ITEMS.register("raw_uranium", () -> new Item(new Item.Properties()));

  public static final RegistryObject<Item> URANIUM_INGOT =
          ITEMS.register("uranium_ingot", () -> new Item(new Item.Properties()));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}