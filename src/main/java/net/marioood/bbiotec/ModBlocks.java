package net.marioood.bbiotec;

import net.marioood.bbiotec.block.BlockEviscerator;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
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

    public static final RegistryObject<RotatedPillarBlock> INTESTINE = registerBlock("intestine",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> CLUMP_OF_PROTEINS = registerBlock("clump_of_proteins",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> CLUMP_OF_LIPIDS = registerBlock("clump_of_lipids",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.SLIME_BLOCK)));

    public static final RegistryObject<Block> SKIN = registerBlock("skin",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.FROGLIGHT)));



    public static final RegistryObject<Block> IRRADIATOR = registerBlock("irradiator",
            () -> new BlockIrradiator(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.METAL)));

    public static final RegistryObject<Block> EVISCERATOR = registerBlock("eviscerator",
            () -> new BlockEviscerator(BlockBehaviour.Properties.of().strength(2f).sound(SoundType.METAL)));

    public static final RegistryObject<Block> URANIUM_ORE = registerBlock("uranium_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(2F, 6.0F)
                    .sound(SoundType.STONE)
                    .lightLevel(x -> 5)
            ));

    public static final RegistryObject<RotatedPillarBlock> RAW_URANIUM_BLOCK = registerBlock("raw_uranium_block",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of()
                    .strength(4F, 6.0F)
                    .sound(SoundType.METAL)
                    .lightLevel(x -> 11)
            ));



    public static final RegistryObject<Block> GIANT_THORN = registerBlock("giant_thorn",
            () -> new BlockGiantThorn(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.WOOD).noOcclusion()));

    public static final RegistryObject<RotatedPillarBlock> GIANT_FLOWER_STEM = registerBlock("giant_flower_stem",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> RED_PETAL_BLOCK = registerBlock("red_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> YELLOW_PETAL_BLOCK = registerBlock("yellow_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> LIGHT_GRAY_PETAL_BLOCK = registerBlock("light_gray_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

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