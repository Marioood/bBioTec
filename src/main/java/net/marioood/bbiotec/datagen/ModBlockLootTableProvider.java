package net.marioood.bbiotec.datagen;

import net.marioood.bbiotec.ModBlocks;
import net.marioood.bbiotec.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
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
        dropSelf(ModBlocks.SKIN.get());

        dropSelf(ModBlocks.RED_PETAL_BLOCK.get());
        dropSelf(ModBlocks.YELLOW_PETAL_BLOCK.get());
        dropSelf(ModBlocks.LIGHT_GRAY_PETAL_BLOCK.get());

        dropSelf(ModBlocks.GIANT_FLOWER_STEM.get());
        dropSelf(ModBlocks.GIANT_THORN.get());

        add(ModBlocks.URANIUM_ORE.get(),
                block -> createOreDrop(ModBlocks.URANIUM_ORE.get(), ModItems.RAW_URANIUM.get()));

        dropSelf(ModBlocks.IRRADIATOR.get());
        dropSelf(ModBlocks.EVISCERATOR.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
