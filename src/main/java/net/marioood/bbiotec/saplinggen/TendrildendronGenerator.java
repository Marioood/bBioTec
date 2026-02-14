package net.marioood.bbiotec.saplinggen;

import net.marioood.bbiotec.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class TendrildendronGenerator extends ModTreeGenerator {
    public void generate(ServerLevel pLevel, BlockPos pPos, BlockState pState) {
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
