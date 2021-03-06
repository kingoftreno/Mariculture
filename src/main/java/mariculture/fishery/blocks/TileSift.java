package mariculture.fishery.blocks;

import mariculture.core.blocks.base.TileStorage;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

public class TileSift extends TileStorage implements ISidedInventory {

	public TileSift() {
		inventory = new ItemStack[10];
	}
	
	public int getSuitableSlot(ItemStack item) {
		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] == null) {
				return i;
			}

			if ((inventory[i].getItemDamage() == item.getItemDamage() && inventory[i].getItem() == item.getItem() 
					&& (inventory[i].stackSize + item.stackSize) <= inventory[i].getMaxStackSize())) {
				return i;
			}
		}

		return 10;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, int side) {
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack stack, int side) {
		return this.blockMetadata > 1;
	}
}
