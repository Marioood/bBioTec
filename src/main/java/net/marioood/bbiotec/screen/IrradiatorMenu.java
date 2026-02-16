package net.marioood.bbiotec.screen;

import net.marioood.bbiotec.ModBlocks;
import net.marioood.bbiotec.ModItems;
import net.marioood.bbiotec.entity.BlockEvisceratorEntity;
import net.marioood.bbiotec.entity.BlockIrradiatorEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IrradiatorMenu extends AbstractContainerMenu {
    public final BlockIrradiatorEntity daddy;
    private final Level level;
    private final ContainerData data;

    public IrradiatorMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(2));
    }

    public IrradiatorMenu(int pContainerId, Inventory inv, BlockEntity blockEntity, SimpleContainerData data) {
        super(ModMenuTypes.IRRADIATOR.get(), pContainerId);
        this.daddy = (BlockIrradiatorEntity)blockEntity;
        this.level = inv.player.level();
        this.data = data;

        addPlayerSlots(inv);
        addSlot(new SlotItemHandler(daddy.itemHandler, daddy.SLOT_INPUT0, 26, 15));
        addSlot(new SlotItemHandler(daddy.itemHandler, daddy.SLOT_INPUT1, 70, 15));
        addSlot(new SlotItemHandler(daddy.itemHandler, daddy.SLOT_INPUT2, 26, 55));
        addSlot(new SlotItemHandler(daddy.itemHandler, daddy.SLOT_INPUT3, 70, 55));
        addSlot(new SlotItemHandler(daddy.itemHandler, daddy.SLOT_INPUT_URANIUM, 48, 35));
        addSlot(new SlotItemHandler(daddy.itemHandler, daddy.SLOT_OUTPUT, 124, 35) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });

        addDataSlots(data);
    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }

    public int getScaledArrowProgress() {
        int progress = data.get(0);
        int maxProgress = data.get(1);
        int arrowWidth = 24;

        return progress > 0 ? progress * arrowWidth / maxProgress : 0;
    }
    public double getFxProgress(float partialTick) {
        return (data.get(0) + partialTick) / (double)data.get(1);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, daddy.getBlockPos()), pPlayer, ModBlocks.IRRADIATOR.get());
    }

    private void addPlayerSlots(Inventory pPlayerInventory) {
        for (int l = 0; l < 3; l++) {
            for (int j1 = 0; j1 < 9; j1++) {
                this.addSlot(new Slot(pPlayerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for (int i1 = 0; i1 < 9; i1++) {
            this.addSlot(new Slot(pPlayerInventory, i1, 8 + i1 * 18, 142));
        }
    }
}
