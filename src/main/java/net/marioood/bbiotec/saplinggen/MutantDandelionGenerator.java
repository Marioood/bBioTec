package net.marioood.bbiotec.saplinggen;

import net.marioood.bbiotec.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class MutantDandelionGenerator extends ModTreeGenerator {
    public void generate(ServerLevel pLevel, BlockPos pPos, BlockState pState) {
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
