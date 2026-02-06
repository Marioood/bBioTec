package net.marioood.bbiotec;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BBioTec.MODID);

    public static final RegistryObject<Block> INTESTINE = registerBlock("intestine",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> CLUMP_OF_PROTEINS = registerBlock("clump_of_proteins",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> IRRADIATOR = registerBlock("irradiator",
            () -> new BlockIrradiator(BlockBehaviour.Properties.of().strength(1f).sound(SoundType.METAL)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}