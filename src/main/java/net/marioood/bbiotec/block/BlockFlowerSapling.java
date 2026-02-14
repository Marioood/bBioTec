package net.marioood.bbiotec.block;

import com.mojang.serialization.MapCodec;
import net.marioood.bbiotec.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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

public class BlockFlowerSapling extends BushBlock implements BonemealableBlock {
    public static MapCodec<BlockFlowerSapling> CODEC = simpleCodec(BlockFlowerSapling::new);

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

    @Override
    public MapCodec<BlockFlowerSapling> codec() {
        return CODEC;
    }

    public BlockFlowerSapling(Properties p_55979_) {
        super(p_55979_);
        this.registerDefaultState(this.stateDefinition.any().setValue(STAGE, Integer.valueOf(0)));
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
            BlockState stem = ModBlocks.GIANT_FLOWER_STEM.get().defaultBlockState();
            BlockState petal = ModBlocks.YELLOW_PETAL_BLOCK.get().defaultBlockState();
            BlockState leafX = ModBlocks.GIANT_LEAF.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X);
            BlockState leafZ = ModBlocks.GIANT_LEAF.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z);

            int xPivot = pPos.getX();
            int zPivot = pPos.getZ();

            int height = 4 + pLevel.random.nextInt(4);
            double dRadius = 3 + pLevel.random.nextDouble() * 3;
            int iRadius = (int)dRadius;
            int iCornerRadius = (int)Math.round(dRadius / Math.sqrt(2));

            for(int y = 0; y < height; y++) {
                BlockPos posYPos = new BlockPos(
                        xPivot,
                        pPos.getY() + y,
                        zPivot
                );

                pLevel.setBlock(posYPos, stem, 3);
            }
            pLevel.setBlock(new BlockPos(xPivot + 1, pPos.getY() + height, zPivot), leafX, 3);
            pLevel.setBlock(new BlockPos(xPivot, pPos.getY() + height, zPivot + 1), leafZ, 3);
            pLevel.setBlock(new BlockPos(xPivot - 1, pPos.getY() + height, zPivot), leafX, 3);
            pLevel.setBlock(new BlockPos(xPivot, pPos.getY() + height, zPivot - 1), leafZ, 3);
            pLevel.setBlock(new BlockPos(xPivot, pPos.getY() + height, zPivot), ModBlocks.ORANGE_PETAL_BLOCK.get().defaultBlockState(), 3);

            height++;
            for(int i = 1; i < iRadius; i++) {
                BlockPos posXPos = new BlockPos(
                        xPivot + i,
                        pPos.getY() + height,
                        zPivot
                );
                BlockPos negXPos = new BlockPos(
                        xPivot - i,
                        pPos.getY() + height,
                        zPivot
                );
                BlockPos posZPos = new BlockPos(
                        xPivot,
                        pPos.getY() + height,
                        zPivot + i
                );
                BlockPos negZPos = new BlockPos(
                        xPivot,
                        pPos.getY() + height,
                        zPivot - i
                );

                pLevel.setBlock(posXPos, petal, 3);
                pLevel.setBlock(negXPos, petal, 3);
                pLevel.setBlock(posZPos, petal, 3);
                pLevel.setBlock(negZPos, petal, 3);
            }
            for(int i = 1; i < iCornerRadius; i++) {
                BlockPos pos1 = new BlockPos(
                        xPivot + i,
                        pPos.getY() + height,
                        zPivot + i
                );
                BlockPos pos2 = new BlockPos(
                        xPivot + i,
                        pPos.getY() + height,
                        zPivot - i
                );
                BlockPos pos3 = new BlockPos(
                        xPivot - i,
                        pPos.getY() + height,
                        zPivot + i
                );
                BlockPos pos4 = new BlockPos(
                        xPivot - i,
                        pPos.getY() + height,
                        zPivot - i
                );

                pLevel.setBlock(pos1, petal, 3);
                pLevel.setBlock(pos2, petal, 3);
                pLevel.setBlock(pos3, petal, 3);
                pLevel.setBlock(pos4, petal, 3);
            }

            int[] xMults = {1, 0, -1, 0};
            int[] zMults = {0, 1, 0, -1};
            BlockState[] leafStates = {leafX, leafZ, leafX, leafZ};

            for(int p = 0; p < 4; p++) {
                if(pLevel.random.nextInt(4) == 0) continue;

                int petalLen = 2 + pLevel.random.nextInt(4);
                int vertOffs = 0;
                int horzOffs = 1;

                for(int i = 0; i < petalLen; i++) {
                    BlockPos iPos = new BlockPos(
                            xPivot + horzOffs * xMults[p],
                            pPos.getY() + vertOffs,
                            zPivot + horzOffs * zMults[p]
                    );

                    pLevel.setBlock(iPos, leafStates[p], 3);

                    if(pLevel.random.nextInt(2) == 0) {
                        vertOffs++;
                    } else {
                        horzOffs++;
                    }
                }
            }
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
