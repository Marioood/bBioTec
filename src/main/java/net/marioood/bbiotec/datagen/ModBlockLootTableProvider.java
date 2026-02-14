package net.marioood.bbiotec.datagen;

import net.marioood.bbiotec.ModBlocks;
import net.marioood.bbiotec.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.INTESTINE.get());
        dropSelf(ModBlocks.CLUMP_OF_PROTEINS.get());
        dropSelf(ModBlocks.CLUMP_OF_LIPIDS.get());
        dropSelf(ModBlocks.LIGHT_SKIN.get());
        dropSelf(ModBlocks.SAND_SKIN.get());
        dropSelf(ModBlocks.HONEY_SKIN.get());
        dropSelf(ModBlocks.DARK_SKIN.get());
        dropSelf(ModBlocks.DECOMPOSING_SKIN.get());
        dropSelf(ModBlocks.TENDRILDENDRON.get());

        dropSelf(ModBlocks.RED_PETAL_BLOCK.get());
        dropSelf(ModBlocks.ORANGE_PETAL_BLOCK.get());
        dropSelf(ModBlocks.YELLOW_PETAL_BLOCK.get());
        dropSelf(ModBlocks.LIGHT_BLUE_PETAL_BLOCK.get());
        dropSelf(ModBlocks.BLUE_PETAL_BLOCK.get());
        dropSelf(ModBlocks.PURPLE_PETAL_BLOCK.get());
        dropSelf(ModBlocks.MAGENTA_PETAL_BLOCK.get());
        dropSelf(ModBlocks.PINK_PETAL_BLOCK.get());
        dropSelf(ModBlocks.WHITE_PETAL_BLOCK.get());
        dropSelf(ModBlocks.LIGHT_GRAY_PETAL_BLOCK.get());
        dropSelf(ModBlocks.BLACK_PETAL_BLOCK.get());

        dropSelf(ModBlocks.GIANT_FLOWER_STEM.get());
        dropSelf(ModBlocks.GIANT_LEAF.get());
        dropSelf(ModBlocks.GIANT_THORN.get());
        dropSelf(ModBlocks.MUTANT_DANDELION.get());

        add(ModBlocks.URANIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.URANIUM_ORE.get(), ModItems.RAW_URANIUM.get()));

       /* add(ModBlocks.GIANT_SEED.get(),
                LootTable.lootTable().withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))

                )
        );*/
        dropSelf(ModBlocks.GIANT_SEED.get());

        dropSelf(ModBlocks.RAW_URANIUM_BLOCK.get());
        dropSelf(ModBlocks.URANIUM_BLOCK.get());

        dropSelf(ModBlocks.IRRADIATOR.get());
        dropSelf(ModBlocks.EVISCERATOR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
