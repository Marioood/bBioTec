package net.marioood.bbiotec;

import net.marioood.bbiotec.block.*;
import net.marioood.bbiotec.saplinggen.MutantDandelionGenerator;
import net.marioood.bbiotec.saplinggen.TendrildendronGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
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

    public static final RegistryObject<Block> LIGHT_SKIN = registerBlock("light_skin",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.FROGLIGHT)));
    public static final RegistryObject<Block> SAND_SKIN = registerBlock("sand_skin",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.FROGLIGHT)));
    public static final RegistryObject<Block> HONEY_SKIN = registerBlock("honey_skin",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.FROGLIGHT)));
    public static final RegistryObject<Block> DARK_SKIN = registerBlock("dark_skin",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.FROGLIGHT)));
    public static final RegistryObject<Block> DECOMPOSING_SKIN = registerBlock("decomposing_skin",
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
    public static final RegistryObject<Block> URANIUM_BLOCK = registerBlock("uranium_block",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.METAL)
                            .requiresCorrectToolForDrops()
                            .strength(5.0F, 6.0F)
                            .sound(SoundType.METAL)
            ));
    public static final RegistryObject<Block> MISSING_TILES = registerBlock("missing_tiles",
            () -> new Block(
                    BlockBehaviour.Properties.of()
                            .mapColor(MapColor.STONE)
                            .requiresCorrectToolForDrops()
                            .strength(1.6F, 6.0F)
                            .sound(SoundType.STONE)
            ));



    public static final RegistryObject<Block> GIANT_THORN = registerBlock("giant_thorn",
            () -> new BlockGiantThorn(BlockBehaviour.Properties.of().strength(0.5f).sound(SoundType.WOOD).noOcclusion()));

    public static final RegistryObject<RotatedPillarBlock> GIANT_FLOWER_STEM = registerBlock("giant_flower_stem",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(0.25f).sound(SoundType.WOOD)));
    public static final RegistryObject<RotatedPillarBlock> GIANT_LEAF = registerBlock("giant_leaf",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.GRASS)));
    public static final RegistryObject<BlockGiantSeed> GIANT_SEED = registerBlock("giant_seed",
            () -> new BlockGiantSeed(BlockBehaviour.Properties.of().strength(4f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DANDELION_FLUFF = registerBlock("dandelion_fluff",
            () -> new Block(BlockBehaviour.Properties.of().
                    strength(0.125f)
                    .noOcclusion()
                    .sound(SoundType.WOOL)
            ) {
                @Override
                protected int getLightBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
                    return 1;
                }
            });

    public static final RegistryObject<Block> RED_PETAL_BLOCK = registerBlock("red_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> ORANGE_PETAL_BLOCK = registerBlock("orange_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> YELLOW_PETAL_BLOCK = registerBlock("yellow_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> LIGHT_BLUE_PETAL_BLOCK = registerBlock("light_blue_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> BLUE_PETAL_BLOCK = registerBlock("blue_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> PURPLE_PETAL_BLOCK = registerBlock("purple_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> MAGENTA_PETAL_BLOCK = registerBlock("magenta_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> PINK_PETAL_BLOCK = registerBlock("pink_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> WHITE_PETAL_BLOCK = registerBlock("white_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> LIGHT_GRAY_PETAL_BLOCK = registerBlock("light_gray_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> BLACK_PETAL_BLOCK = registerBlock("black_petal_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(0.125f).sound(SoundType.WOOL)));

    //must be at bottom, because some mod blocks are accessed in the constructors for these
    public static final RegistryObject<ModSaplingBlock> TENDRILDENDRON = registerBlock("tendrildendron",
            () -> new ModSaplingBlock(
                    new TendrildendronGenerator(),
                    BlockBehaviour.Properties.of()
                            .sound(SoundType.SLIME_BLOCK)
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .pushReaction(PushReaction.DESTROY)
                            .noOcclusion()
            ));

    public static final RegistryObject<ModSaplingBlock> MUTANT_DANDELION = registerBlock("mutant_dandelion",
            () -> new ModSaplingBlock(
                    new MutantDandelionGenerator(),
                    BlockBehaviour.Properties.of()
                            .sound(SoundType.GRASS)
                            .noCollission()
                            .randomTicks()
                            .instabreak()
                            .pushReaction(PushReaction.DESTROY)
                            .noOcclusion()
            ));

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