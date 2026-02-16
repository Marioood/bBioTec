package net.marioood.bbiotec.saplinggen;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ModTreeGenerator {
    public abstract void generate(ServerLevel pLevel, BlockPos pPos);
}
