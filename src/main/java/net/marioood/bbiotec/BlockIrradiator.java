package net.marioood.bbiotec;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class BlockIrradiator extends Block {
    public BlockIrradiator(Properties props) {
        super(props);
    }

   /* @Override
    public void onNeighborChange(BlockState state, LevelReader level, BlockPos pos, BlockPos neighbor) {
        boolean neighborsWater = true;
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                if(x == 0 && z == 0) continue;

                neighborsWater &&= level.isWaterAt(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z));
            }
        }

        if(!neighborsWater) {

        }

        super.onNeighborChange(state, level, pos, neighbor);
    }*/

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        boolean neighborsWater = true;
        for(int x = -1; x <= 1; x++) {
            for(int z = -1; z <= 1; z++) {
                if(x == 0 && z == 0) continue;

                neighborsWater = neighborsWater && pLevel.isWaterAt(new BlockPos(pPos.getX() + x, pPos.getY(), pPos.getZ() + z));
            }
        }

        if(!neighborsWater) {
            pLevel.removeBlock(pPos, false);
        }
    }
}
