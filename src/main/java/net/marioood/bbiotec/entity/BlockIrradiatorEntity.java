package net.marioood.bbiotec.entity;

import net.marioood.bbiotec.ModBlocks;
import net.marioood.bbiotec.ModItems;
import net.marioood.bbiotec.datagen.IrradiatorRecipes;
import net.marioood.bbiotec.screen.IrradiatorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class BlockIrradiatorEntity extends BlockEntity implements MenuProvider {

    public final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public final int SLOT_INPUT0 = 0;
    public final int SLOT_INPUT1 = 1;
    public final int SLOT_INPUT2 = 2;
    public final int SLOT_INPUT3 = 3;

    public final int SLOT_INPUT_URANIUM = 4;
    public final int SLOT_OUTPUT = 5;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final SimpleContainerData data;
    private int progress = 0;
    private int maxProgress = 120;

    private ItemStack output;

    public BlockIrradiatorEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.IRRADIATOR.get(), pPos, pBlockState);
        data = new SimpleContainerData(2) {

            @Override
            public int get(int pIndex) {
                return switch(pIndex) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch(pIndex) {
                    case 0:
                        progress = pValue;
                        break;
                    case 1:
                        maxProgress = pValue;
                        break;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.bbiotec.irradiator");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new IrradiatorMenu(pContainerId, pPlayerInventory, this, data);
    }

    public void tick(Level level, BlockPos pos, BlockState blockState) {

        if(hasRecipe()) {
            progress++;
            setChanged(level, pos, blockState);

            if(progress >= maxProgress) {
                craftItem();
                progress = 0;
            }

            if(level.random.nextInt(60) > 0)
                return;

            boolean neighborsWater = false;

            for(int x = -1; x <= 1; x++) {
                for(int z = -1; z <= 1; z++) {
                    if(x == 0 && z == 0) continue;

                    neighborsWater = neighborsWater || level.isWaterAt(new BlockPos(pos.getX() + x, pos.getY(), pos.getZ() + z));
                }
            }

            if(!neighborsWater) {
                level.explode(null, pos.getX(), pos.getY(), pos.getZ(), 6, Level.ExplosionInteraction.BLOCK);
            }
        } else {
            if(progress > 0) {
                progress -= 2;
            }
        }
    }

    private void craftItem() {
        itemHandler.extractItem(SLOT_INPUT0, 1, false);
        itemHandler.extractItem(SLOT_INPUT1, 1, false);
        itemHandler.extractItem(SLOT_INPUT2, 1, false);
        itemHandler.extractItem(SLOT_INPUT3, 1, false);
        itemHandler.extractItem(SLOT_INPUT_URANIUM, 1, false);
        itemHandler.setStackInSlot(SLOT_OUTPUT, new ItemStack(output.getItem(), itemHandler.getStackInSlot(SLOT_OUTPUT).getCount() + output.getCount()));
    }

    private boolean hasRecipe() {
        Item uranium = ModItems.URANIUM_INGOT.get();

        if(!itemHandler.getStackInSlot(SLOT_INPUT_URANIUM).is(uranium))
            return false;

        //1. tally up ingredients
        //[{protein: 2, oak_sapling: 1, lipids: 1}, {poppy: 1, rose_bush: 1}...]
        //[itemstack(tendrildendron, 1), itemstack(short_rose, 4)..]
        //2. compare ingredient counts
        //3. use index in a separate array of outputs

        HashMap<Item, Integer> inputs = new HashMap<>();

        for(int i = SLOT_INPUT0; i <= SLOT_INPUT3; i++) {
            ItemStack curStack = itemHandler.getStackInSlot(i);
            Item curItem = curStack.getItem();
            if(curItem != Items.AIR) {
                Integer preexistingSize = inputs.get(curItem);
                if(preexistingSize == null) {
                    inputs.put(curStack.getItem(), 1);
                } else {
                    inputs.put(curStack.getItem(), preexistingSize + 1);
                }
            }
        }

        output = IrradiatorRecipes.getOutput(inputs);

        if(output == null) return false;

        return camInsertItemIntoOutput(output) &&
                canInsertAmountIntoOutputSlot(output.getCount());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(SLOT_OUTPUT).isEmpty() ? 64 : itemHandler.getStackInSlot(SLOT_OUTPUT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(SLOT_OUTPUT).getCount();

        return maxCount >= currentCount + count;
    }

    private boolean camInsertItemIntoOutput(ItemStack output) {
        return itemHandler.getStackInSlot(SLOT_OUTPUT).isEmpty() || itemHandler.getStackInSlot(SLOT_OUTPUT).getItem() == output.getItem();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.saveAdditional(pTag, pRegistries);
        pTag.put("Inventory", itemHandler.serializeNBT(pRegistries));
        pTag.putInt("Progress", progress);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);
        itemHandler.deserializeNBT(pRegistries, pTag.getCompound("Inventory"));
        progress = pTag.getInt("Progress");
    }


    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
