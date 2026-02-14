package net.marioood.bbiotec.screen;

import net.marioood.bbiotec.ModItems;
import net.marioood.bbiotec.entity.BlockEvisceratorEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.CrafterSlot;
import net.minecraft.world.inventory.NonInteractiveResultSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class EvisceratorMenu extends AbstractContainerMenu {
    public final BlockEvisceratorEntity daddy;
    private final Level level;

    public EvisceratorMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()));
    }

    public EvisceratorMenu(int pContainerId, Inventory inv, BlockEntity blockEntity) {
        super(ModMenuTypes.EVISCERATOR.get(), pContainerId);
        this.daddy = (BlockEvisceratorEntity)blockEntity;
        this.level = inv.player.level();

        addPlayerSlots(inv);
        addSlot(new SlotItemHandler(daddy.inventory, 0, 26, 35));

        addSlot(new SlotItemHandler(daddy.inventory, 1, 118, 26) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
        addSlot(new SlotItemHandler(daddy.inventory, 2, 136, 26) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
        addSlot(new SlotItemHandler(daddy.inventory, 3, 118, 44) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
        addSlot(new SlotItemHandler(daddy.inventory, 4, 136, 44) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return false;
            }
        });
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    /*@Override
    public void slotsChanged(Container pContainer) {
        if(pContainer.getItem(0) == null) return;

        if(pContainer.getItem(0).is(Items.APPLE)) {
            daddy.inventory.setStackInSlot(1, new ItemStack(ModItems.CELLULOSE.get()));
            daddy.inventory.setStackInSlot(1, new ItemStack(ModItems.GLUCOSE_SYRUP.get()));
        }
        super.slotsChanged(pContainer);
    }*/

    @Override
    public boolean stillValid(Player pPlayer) {
        return true;
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
