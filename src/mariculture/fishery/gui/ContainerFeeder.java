package mariculture.fishery.gui;

import mariculture.api.core.IItemUpgrade;
import mariculture.core.gui.ContainerMachine;
import mariculture.core.gui.SlotFluidContainer;
import mariculture.core.gui.SlotOutput;
import mariculture.core.gui.SlotUpgrade;
import mariculture.core.helpers.FluidHelper;
import mariculture.fishery.FishFoodHandler;
import mariculture.fishery.FishHelper;
import mariculture.fishery.Fishery;
import mariculture.fishery.blocks.TileFeeder;
import mariculture.fishery.items.ItemFishy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerFeeder extends ContainerMachine {

	public ContainerFeeder(TileFeeder tile, InventoryPlayer playerInventory) {
		super(tile);

		addUpgradeSlots(tile);
		
		//Fluids
		this.addSlotToContainer(new SlotFluidContainer(tile, 3, 10, 25));
		this.addSlotToContainer(new SlotOutput(tile, 4, 10, 56));
		
		//Fish
		this.addSlotToContainer(new SlotFather(tile, 5, 76, 25));
		this.addSlotToContainer(new SlotMother(tile, 6, 76, 53));
		
		//Output
		this.addSlotToContainer(new SlotOutput(tile, 7, 132, 22));
		this.addSlotToContainer(new SlotOutput(tile, 8, 132, 40));
		this.addSlotToContainer(new SlotOutput(tile, 9, 132, 58));
		this.addSlotToContainer(new SlotOutput(tile, 10, 150, 22));
		this.addSlotToContainer(new SlotOutput(tile, 11, 150, 40));
		this.addSlotToContainer(new SlotOutput(tile, 12, 150, 58));

		bindPlayerInventory(playerInventory, 10);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotID) {
		int size = getSizeInventory();
		int low = size + 27;
		int high = low + 9;
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotID);

		if (slot != null && slot.getHasStack()) {
			ItemStack stack = slot.getStack();
			itemstack = stack.copy();

			if (slotID < size) {
				if (!this.mergeItemStack(stack, size, high, true)) {
					return null;
				}

				slot.onSlotChange(stack, itemstack);
			} else if (slotID >= size) {
				if (stack.getItem() instanceof ItemFishy && Fishery.gender.getDNA(stack) == FishHelper.MALE) {
					if (!this.mergeItemStack(stack, 5, 6, false)) { // Slot 5-5
						return null;
					}
				} else if (stack.getItem() instanceof ItemFishy && Fishery.gender.getDNA(stack) == FishHelper.FEMALE) {
					if (!this.mergeItemStack(stack, 6, 7, false)) { // Slot 6-6
						return null;
					}
				} else if (stack.getItem() instanceof IItemUpgrade) {
					if (!this.mergeItemStack(stack, 0, 3, false)) { // Slot 0-2
						return null;
					}
				} else if (FluidHelper.isFluidOrEmpty(stack) || FishFoodHandler.isFishFood(stack)) {
					if (!this.mergeItemStack(stack, 3, 4, false)) { // Slot 3-3
						return null;
					}
				} else if (slotID >= size && slotID < low) {
					if (!this.mergeItemStack(stack, low, high, false)) {
						return null;
					}
				} else if (slotID >= low && slotID < high && !this.mergeItemStack(stack, high, low, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(stack, size, high, false)) {
				return null;
			}

			if (stack.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (stack.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(player, stack);
		}

		return itemstack;
	}
}