package net.marioood.bbiotec.datagen;

import net.marioood.bbiotec.BBioTec;
import net.marioood.bbiotec.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BBioTec.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CLUMP_OF_PROTEINS.get());
        blockWithItem(ModBlocks.CLUMP_OF_LIPIDS.get());
        blockWithItem(ModBlocks.SKIN.get());

        blockWithItem(ModBlocks.RED_PETAL_BLOCK.get());
        blockWithItem(ModBlocks.YELLOW_PETAL_BLOCK.get());
        blockWithItem(ModBlocks.LIGHT_GRAY_PETAL_BLOCK.get());

        blockWithItem(ModBlocks.URANIUM_ORE.get());

        logBlockWithItem(ModBlocks.INTESTINE);
        logBlockWithItem(ModBlocks.GIANT_FLOWER_STEM);
        logBlockWithItem(ModBlocks.RAW_URANIUM_BLOCK);

    }

    private void blockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    private void blockItem(Block block) {
        simpleBlockItem(block, cubeAll(block));
    }

    private void logBlockWithItem(RegistryObject<RotatedPillarBlock> reg) {
        logBlock(reg.get());
        simpleBlockItem(reg.get(),
                models().cubeColumn(reg.getId().getPath(),
                        blockTexture(reg.get()),
                        extend(blockTexture(reg.get()), "_top")
                ));
    }

    private ResourceLocation extend(ResourceLocation rl, String suffix) {
        return rl.withSuffix(suffix);
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }
}
