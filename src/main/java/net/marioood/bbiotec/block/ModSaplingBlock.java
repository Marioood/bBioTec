package net.marioood.bbiotec.block;

import com.mojang.serialization.MapCodec;
import net.marioood.bbiotec.ModBlocks;
import net.marioood.bbiotec.saplinggen.ModTreeGenerator;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ModSaplingBlock extends BushBlock implements BonemealableBlock {
    public static MapCodec<ModSaplingBlock> CODEC = simpleCodec(ModSaplingBlock::new);

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);
    private ModTreeGenerator treeGenerator;

    @Override
    public MapCodec<ModSaplingBlock> codec() {
        return CODEC;
    }

    public ModSaplingBlock(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
    }

    public ModSaplingBlock(ModTreeGenerator treeGenerator, Properties props) {
        this(props);
        this.treeGenerator = treeGenerator;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        //if (!pLevel.isAreaLoaded(pPos, 1)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light
        if (pLevel.getMaxLocalRawBrightness(pPos.above()) >= 9 && pRandom.nextInt(7) == 0) {
            this.advanceTree(pLevel, pPos, pState, pRandom);
        }
    }

    public void advanceTree(ServerLevel pLevel, BlockPos pPos, BlockState pState, RandomSource pRandom) {
        if (pState.getValue(STAGE) == 0) {
            pLevel.setBlock(pPos, pState.cycle(STAGE), 4);
        } else {
            treeGenerator.generate(pLevel, pPos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        return (double)pLevel.random.nextFloat() < 0.45;
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        this.advanceTree(pLevel, pPos, pState, pRandom);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STAGE);
    }
}
