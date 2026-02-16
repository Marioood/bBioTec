package net.marioood.bbiotec.saplinggen;

import net.marioood.bbiotec.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MutantDandelionGenerator extends ModTreeGenerator {
    private final BlockState stem = ModBlocks.GIANT_FLOWER_STEM.get().defaultBlockState();
    private final BlockState stemX = ModBlocks.GIANT_FLOWER_STEM.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X);
    private final BlockState stemZ = ModBlocks.GIANT_FLOWER_STEM.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z);
    private final BlockState leafX = ModBlocks.GIANT_LEAF.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.X);
    private final BlockState leafZ = ModBlocks.GIANT_LEAF.get().defaultBlockState().setValue(BlockStateProperties.AXIS, Direction.Axis.Z);

    private final BlockState petal = ModBlocks.YELLOW_PETAL_BLOCK.get().defaultBlockState();
    private final BlockState fluff = ModBlocks.DANDELION_FLUFF.get().defaultBlockState();
    private final BlockState seed = ModBlocks.GIANT_SEED.get().defaultBlockState();

    //see markAndNotifyBlock() in level
    private final int FLAGS = 1 | 2;

    public void generate(ServerLevel pLevel, BlockPos pPos) {
        boolean isMature = pLevel.random.nextInt(2) == 0;
        int xPivot = pPos.getX();
        int zPivot = pPos.getZ();

        int height = pLevel.random.nextInt(4) + (isMature ? 8 : 4);

        for(int y = 0; y < height; y++) {
            BlockPos posYPos = new BlockPos(
                    xPivot,
                    pPos.getY() + y,
                    zPivot
            );

            //pLevel.setBlock(posYPos, stem, 3);
            setBlockConditional(pLevel, posYPos, stem);
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

                //pLevel.setBlock(iPos, leafStates[p], 3);
                setBlockConditional(pLevel, iPos, leafStates[p]);

                if(pLevel.random.nextInt(2) == 0) {
                    vertOffs++;
                } else {
                    horzOffs++;
                }
            }
        }


        if(isMature) {
            generateMature(pLevel, pPos, height);
        } else {
            generateJouvenile(pLevel, pPos, height);
        }

    }

    private void generateJouvenile(ServerLevel pLevel, BlockPos pPos, int height) {
        int xPivot = pPos.getX();
        int zPivot = pPos.getZ();
        double dRadius = 3 + pLevel.random.nextDouble() * 3;
        int iRadius = (int) dRadius;
        int iCornerRadius = (int) Math.round(dRadius / Math.sqrt(2));

        pLevel.setBlock(new BlockPos(xPivot + 1, pPos.getY() + height, zPivot), leafX, 3);
        pLevel.setBlock(new BlockPos(xPivot, pPos.getY() + height, zPivot + 1), leafZ, 3);
        pLevel.setBlock(new BlockPos(xPivot - 1, pPos.getY() + height, zPivot), leafX, 3);
        pLevel.setBlock(new BlockPos(xPivot, pPos.getY() + height, zPivot - 1), leafZ, 3);
        pLevel.setBlock(new BlockPos(xPivot, pPos.getY() + height, zPivot), ModBlocks.ORANGE_PETAL_BLOCK.get().defaultBlockState(), 3);

        height++;
        for (int i = 1; i < iRadius; i++) {
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
        for (int i = 1; i < iCornerRadius; i++) {
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

            //pLevel.setBlock(pos1, petal, 3);
            //pLevel.setBlock(pos2, petal, 3);
            //pLevel.setBlock(pos3, petal, 3);
            //pLevel.setBlock(pos4, petal, 3);
            setBlockConditional(pLevel, pos1, petal);
            setBlockConditional(pLevel, pos2, petal);
            setBlockConditional(pLevel, pos3, petal);
            setBlockConditional(pLevel, pos4, petal);
        }

    }

    private void generateMature(ServerLevel pLevel, BlockPos pPos, int stemHeight) {
        int xPivot = pPos.getX();
        int zPivot = pPos.getZ();
        double dRadius = 3 + pLevel.random.nextDouble() * 3;
        int iRadius = (int)dRadius;

        int height = iRadius * 2 + 1;
        int width = iRadius * 2 + 1;
        double[] distGrid = new double[width * width * height]; //ordered like (z + x * width + y * width * width)

        for(int z = - iRadius; z <= iRadius; z++) {
            for(int y = - iRadius; y <= iRadius; y++) {
                for(int x = -iRadius; x <= iRadius; x++) {
                    distGrid[(z + iRadius) + (x + iRadius) * width + (y + iRadius) * width * width] =
                            Math.sqrt(x * x + y * y + z * z);
                }
            }
        }

        for(int z = 0; z < width; z++) {
            for(int y = 1; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    boolean posX = x == width - 1  ? false : distGrid[z + (x + 1) * width + y * width * width] < dRadius;
                    boolean negX = x == 0          ? false : distGrid[z + (x - 1) * width + y * width * width] < dRadius;
                    boolean posY = y == height - 1 ? false : distGrid[z + x * width + (y + 1) * width * width] < dRadius;
                    boolean negY = y == 0          ? false : distGrid[z + x * width + (y - 1) * width * width] < dRadius;
                    boolean posZ = z == width - 1  ? false : distGrid[(z + 1) + x * width + y * width * width] < dRadius;
                    boolean negZ = z == 0          ? false : distGrid[(z - 1) + x * width + y * width * width] < dRadius;
                    boolean mid = distGrid[z + x * width + y * width * width] < dRadius;
                    if(mid && !(posX && negX && posY && negY && posZ && negZ)) {
                        BlockPos iPos = new BlockPos(
                                xPivot + x - iRadius,
                                pPos.getY() + y - iRadius + stemHeight,
                                zPivot + z - iRadius
                        );
                        setBlockConditional(pLevel, iPos, fluff);
                    }
                }
            }
        }


        for (int i = 1; i < iRadius; i++) {
            BlockPos posXPos = new BlockPos(
                    xPivot + i,
                    pPos.getY() + stemHeight,
                    zPivot
            );
            BlockPos negXPos = new BlockPos(
                    xPivot - i,
                    pPos.getY() + stemHeight,
                    zPivot
            );
            BlockPos posZPos = new BlockPos(
                    xPivot,
                    pPos.getY() + stemHeight,
                    zPivot + i
            );
            BlockPos negZPos = new BlockPos(
                    xPivot,
                    pPos.getY() + stemHeight,
                    zPivot - i
            );
            BlockPos posYPos = new BlockPos(
                    xPivot,
                    pPos.getY() + stemHeight + i,
                    zPivot
            );
            if(i == 1) {
                setBlockConditional(pLevel, posXPos, seed);
                setBlockConditional(pLevel, negXPos, seed);
                setBlockConditional(pLevel, posZPos, seed);
                setBlockConditional(pLevel, negZPos, seed);
                setBlockConditional(pLevel, posYPos, seed);
            } else {
                setBlockConditional(pLevel, posXPos, stemX);
                setBlockConditional(pLevel, negXPos, stemX);
                setBlockConditional(pLevel, posZPos, stemZ);
                setBlockConditional(pLevel, negZPos, stemZ);
                setBlockConditional(pLevel, posYPos, stem);
            }

        }

    }

    private void setBlockConditional(ServerLevel pLevel, BlockPos pPos, BlockState block) {
        Block existingBlock = pLevel.getBlockState(pPos).getBlock();

        if(existingBlock.defaultBlockState().is(BlockTags.REPLACEABLE_BY_TREES) || existingBlock == ModBlocks.MUTANT_DANDELION.get() || existingBlock == Blocks.AIR)
            pLevel.setBlock(pPos, block, FLAGS);
    }
}
