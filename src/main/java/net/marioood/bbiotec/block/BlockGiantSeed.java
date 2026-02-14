package net.marioood.bbiotec.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class BlockGiantSeed extends Block {
    private static final int POPPY = 0;
    private static final int DANDELION = 1;
    private static final int CORNFLOWER = 2;
    private static final int ALLIUM = 3;
    private static final int OXEYE_DAISY = 4;
    private static final int AZURE_BLUET = 5;
    private static final int BLUE_ORCHID = 6;
    private static final int TYPE_COUNT = 7;
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, TYPE_COUNT - 1);

    public BlockGiantSeed(Properties p) {
        super(p);
        registerDefaultState(defaultBlockState().setValue(TYPE, POPPY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(TYPE);
    }
}
