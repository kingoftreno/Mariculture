package mariculture.core.helpers.cofh;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.energy.IEnergyHandler;

/**
 * This class contains helper functions related to Redstone Flux, the basis of the CoFH Energy System.
 * 
 * @author King Lemming
 * 
 */
public class EnergyHelper {

	public final int RF_PER_MJ = 10; // Official Ratio of RF to MJ (BuildCraft)
	public final int RF_PER_EU = 4; // Official Ratio of RF to EU (IndustrialCraft)

	private EnergyHelper() {

	}

	/* IEnergyContainer Interaction */
	public static int extractEnergyFromHeldContainer(EntityPlayer player, int maxExtract, boolean simulate) {

		ItemStack container = player.getCurrentEquippedItem();

		return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).extractEnergy(container, maxExtract, simulate) : 0;
	}

	public static int insertEnergyIntoHeldContainer(EntityPlayer player, int maxReceive, boolean simulate) {

		ItemStack container = player.getCurrentEquippedItem();

		return isEnergyContainerItem(container) ? ((IEnergyContainerItem) container.getItem()).receiveEnergy(container, maxReceive, simulate) : 0;
	}

	public static boolean isPlayerHoldingEnergyContainerItem(EntityPlayer player) {

		return isEnergyContainerItem(player.getCurrentEquippedItem());
	}

	public static boolean isEnergyContainerItem(ItemStack container) {

		return container != null && container.getItem() instanceof IEnergyContainerItem;
	}

	public static ItemStack setDefaultEnergyTag(ItemStack container, int energy) {

		container.setTagCompound(new NBTTagCompound());
		container.stackTagCompound.setInteger("Energy", energy);

		return container;
	}

	/* IEnergyHandler Interaction */
	public static int extractEnergyFromAdjacentEnergyHandler(TileEntity tile, int side, int energy, boolean simulate) {

		TileEntity handler = BlockHelper.getAdjacentTileEntity(tile, side);

		return handler instanceof IEnergyHandler ? ((IEnergyHandler) handler).extractEnergy(ForgeDirection.VALID_DIRECTIONS[side ^ 1], energy, simulate) : 0;
	}

	public static int insertEnergyIntoAdjacentEnergyHandler(TileEntity tile, int side, int energy, boolean simulate) {

		TileEntity handler = BlockHelper.getAdjacentTileEntity(tile, side);

		return handler instanceof IEnergyHandler ? ((IEnergyHandler) handler).receiveEnergy(ForgeDirection.VALID_DIRECTIONS[side ^ 1], energy, simulate) : 0;
	}

	public static boolean isAdjacentEnergyHandlerFromSide(TileEntity tile, int side) {

		TileEntity handler = BlockHelper.getAdjacentTileEntity(tile, side);

		return isEnergyHandlerFromSide(handler, ForgeDirection.VALID_DIRECTIONS[side ^ 1]);
	}

	public static boolean isEnergyHandlerFromSide(TileEntity tile, ForgeDirection from) {

		return tile instanceof IEnergyHandler ? ((IEnergyHandler) tile).canInterface(from) : false;
	}

}
