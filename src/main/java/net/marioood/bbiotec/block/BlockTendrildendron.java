package net.marioood.bbiotec.block;

import com.mojang.serialization.MapCodec;
import net.marioood.bbiotec.ModBlocks;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockTendrildendron extends BushBlock implements BonemealableBlock {
    public static MapCodec<BlockTendrildendron> CODEC = simpleCodec(BlockTendrildendron::new);

    public static final IntegerProperty STAGE = BlockStateProperties.STAGE;
    protected static final float AABB_OFFSET = 6.0F;
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0);

    @Override
    public MapCodec<BlockTendrildendron> codec() {
        return CODEC;
    }

    public BlockTendrildendron(BlockBehaviour.Properties p_55979_) {
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
            //wavy rounded cylinder (aka COCK)
            Block[] skinColors = {
                ModBlocks.LIGHT_SKIN.get(),
                ModBlocks.SAND_SKIN.get(),
                ModBlocks.HONEY_SKIN.get(),
                ModBlocks.DARK_SKIN.get(),
                ModBlocks.DECOMPOSING_SKIN.get()
            };
            BlockState skin = skinColors[pLevel.random.nextInt(skinColors.length)].defaultBlockState();
            BlockState muscle = ModBlocks.CLUMP_OF_PROTEINS.get().defaultBlockState();
            BlockState lipids = ModBlocks.CLUMP_OF_LIPIDS.get().defaultBlockState();
            byte dataMuscle = 1;
            byte dataLipids = 2;
            byte dataSkin = 3;

            int width = pLevel.random.nextInt(4) * 2 + 3;
            int height = pLevel.random.nextInt(21) + 12;
            byte[] tendrilData1 = new byte[width * width * height]; //ordered like (z + x * width + y * width * width)
            byte[] tendrilData2 = new byte[width * width * height];

            int topRadius = width / 2 + pLevel.random.nextInt(4);
            double wavyPeriod = Math.PI * (pLevel.random.nextDouble() * 3 + 1);
            double wavyOffset = pLevel.random.nextDouble();
            //base shape gen
            for(int y = 0; y < height; y++) {
                double rMul = 1;
                if(y > height - topRadius && y > topRadius) {
                    rMul = (double)(height - y) / topRadius;
                } else if(height - topRadius > y && topRadius > y) {
                    rMul = (double)(y + 1) / topRadius;
                }
                rMul = 1 - (1 - rMul) * (1 - rMul);
                double wavy = (((double)y / height) + wavyOffset) * wavyPeriod;
                wavy = Math.cos(wavy) / 2 + 0.5;
                double sideRadius = rMul * (wavy / 2 + 0.5) * ((double)width / 2);

                for(int x = 0; x < width; x++) {
                    for(int z = 0; z < width; z++) {
                        int idx = z + x * width + y * width * width;
                        double xSqr = x - (width / 2); //should use integer division to stay centered
                        double zSqr = z - (width / 2);
                        double dist = Math.sqrt(xSqr * xSqr + zSqr * zSqr);

                        if(dist <= sideRadius) {
                            tendrilData1[idx] = dataMuscle;
                        }
                    }
                }
            }
            //lipid and skin layer gen
            byte[] tendrilInputData = tendrilData1;
            byte[] tendrilOutputData = tendrilData2;
            for(int i = 0; i < 2; i++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        for (int z = 0; z < width; z++) {
                            int idx = z + x * width + y * width * width;
                            if (tendrilInputData[idx] == 0) {
                                tendrilOutputData[idx] = 0;
                            } else {
                                int nXData = x == 0 ? 0 : tendrilInputData[z + (x - 1) * width + y * width * width];
                                int pXData = x == width - 1 ? 0 : tendrilInputData[z + (x + 1) * width + y * width * width];
                                int nYData = y == 0 ? 0 : tendrilInputData[z + x * width + (y - 1) * width * width];
                                int pYData = y == height - 1 ? 0 : tendrilInputData[z + x * width + (y + 1) * width * width];
                                int nZData = z == 0 ? 0 : tendrilInputData[z - 1 + x * width + y * width * width];
                                int pZData = z == width - 1 ? 0 : tendrilInputData[z + 1 + x * width + y * width * width];

                                if (
                                    nXData == 0 || pXData == 0 ||
                                    nYData == 0 || pYData == 0 ||
                                    nZData == 0 || pZData == 0
                                ) {
                                    tendrilOutputData[idx] = dataSkin;
                                } else if (
                                    nXData == dataSkin || pXData == dataSkin ||
                                    nYData == dataSkin || pYData == dataSkin ||
                                    nZData == dataSkin || pZData == dataSkin
                                ) {
                                    tendrilOutputData[idx] = dataLipids;
                                } else {
                                    tendrilOutputData[idx] = dataMuscle;
                                }
                            }

                        }
                    }
                }
                if(tendrilInputData == tendrilData1) {
                    tendrilInputData = tendrilData2;
                    tendrilOutputData = tendrilData1;
                } else {
                    tendrilInputData = tendrilData1;
                    tendrilOutputData = tendrilData2;
                }
            }
            //put into world
            for(int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    for (int z = 0; z < width; z++) {
                        int idx = z + x * width + y * width * width;
                        if(tendrilInputData[idx] != 0) {
                            BlockPos newPos =
                                    new BlockPos(pPos.getX() + x - width / 2, pPos.getY() + y - topRadius, pPos.getZ() + z - width / 2);

                            Block existingBlock = pLevel.getBlockState(newPos).getBlock();
                            if(existingBlock.defaultBlockState().is(BlockTags.REPLACEABLE_BY_TREES) || existingBlock == ModBlocks.TENDRILDENDRON.get() || existingBlock == Blocks.AIR) {
                                BlockState curBlock = muscle;

                                if(tendrilInputData[idx] == dataLipids)
                                    curBlock = lipids;
                                else if(tendrilInputData[idx] == dataSkin)
                                    curBlock = skin;

                                pLevel.setBlock(newPos, curBlock, 3);
                            }
                        }

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
