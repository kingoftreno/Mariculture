package mariculture.core.helpers.cofh;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Contains various helper functions to assist with {@link Fluid} and Fluid-related manipulation and interaction.
 * 
 * @author King Lemming
 * 
 */
public class FluidHelper {

	public static final FluidStack WATER = new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
	public static final FluidStack LAVA = new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);

	private FluidHelper() {

	}

	/* IFluidContainer Interaction */
	public static FluidStack extractFluidFromHeldContainer(EntityPlayer player, int maxDrain, boolean doDrain) {

		ItemStack container = player.getCurrentEquippedItem();

		return isFluidContainerItem(container) ? ((IFluidContainerItem) container.getItem()).drain(container, maxDrain, doDrain) : null;
	}

	public static int insertFluidIntoHeldContainer(EntityPlayer player, FluidStack resource, boolean doFill) {

		ItemStack container = player.getCurrentEquippedItem();

		return isFluidContainerItem(container) ? ((IFluidContainerItem) container.getItem()).fill(container, resource, doFill) : 0;
	}

	public static boolean isPlayerHoldingFluidContainerItem(EntityPlayer player) {

		return isFluidContainerItem(player.getCurrentEquippedItem());
	}

	public static boolean isFluidContainerItem(ItemStack container) {

		return container != null && container.getItem() instanceof IFluidContainerItem;
	}

	public static FluidStack getFluidStackFromContainerItem(ItemStack container) {

		return ((IFluidContainerItem) container.getItem()).getFluid(container);
	}

	public static ItemStack setDefaultFluidTag(ItemStack container, FluidStack resource) {

		container.setTagCompound(new NBTTagCompound());
		NBTTagCompound fluidTag = resource.writeToNBT(new NBTTagCompound());
		container.stackTagCompound.setTag("Fluid", fluidTag);

		return container;
	}

	/* IFluidHandler Interaction */
	public static FluidStack extractFluidFromAdjacentFluidHandler(TileEntity tile, int side, int maxDrain, boolean doDrain) {

		TileEntity handler = BlockHelper.getAdjacentTileEntity(tile, side);

		return handler instanceof IFluidHandler ? ((IFluidHandler) handler).drain(ForgeDirection.VALID_DIRECTIONS[side ^ 1], maxDrain, doDrain) : null;
	}

	public static int insertFluidIntoAdjacentFluidHandler(TileEntity tile, int side, FluidStack fluid, boolean doFill) {

		TileEntity handler = BlockHelper.getAdjacentTileEntity(tile, side);

		return handler instanceof IFluidHandler ? ((IFluidHandler) handler).fill(ForgeDirection.VALID_DIRECTIONS[side ^ 1], fluid, doFill) : 0;
	}

	public static boolean isAdjacentFluidHandler(TileEntity tile, int side) {

		return BlockHelper.getAdjacentTileEntity(tile, side) instanceof IFluidHandler;
	}

	public static boolean isFluidHandler(TileEntity tile) {

		return tile instanceof IFluidHandler;
	}

	/* Fluid Container Registry Interaction */
	public static boolean fillContainerFromHandler(World world, IFluidHandler handler, EntityPlayer player, FluidStack tankFluid) {

		ItemStack container = player.getCurrentEquippedItem();

		if (FluidContainerRegistry.isEmptyContainer(container)) {
			ItemStack returnStack = FluidContainerRegistry.fillFluidContainer(tankFluid, container);
			FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(returnStack);

			if (fluid == null || returnStack == null) {
				return false;
			}
			if (!player.capabilities.isCreativeMode) {
				if (container.stackSize == 1) {
					container = container.copy();
					player.inventory.setInventorySlotContents(player.inventory.currentItem, returnStack);
				} else if (!player.inventory.addItemStackToInventory(returnStack)) {
					return false;
				}
				handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
				container.stackSize--;

				if (container.stackSize <= 0) {
					container = null;
				}
			} else {
				handler.drain(ForgeDirection.UNKNOWN, fluid.amount, true);
			}
			return true;
		}
		return false;
	}

	/* HELPERS */
	public static boolean isValidFluidStack(FluidStack fluid) {

		return fluid == null ? false : fluid.fluidID == 0 ? false : FluidRegistry.getFluidName(fluid) != null;
	}

	public static int getFluidLuminosity(FluidStack fluid) {

		return fluid == null ? 0 : getFluidLuminosity(fluid.getFluid());
	}

	public static int getFluidLuminosity(Fluid fluid) {

		return fluid == null ? 0 : fluid.getLuminosity();
	}

	public static FluidStack getFluidFromWorld(World worldObj, int x, int y, int z) {
		Block block = worldObj.getBlock(x, y, z);
		int bMeta = worldObj.getBlockMetadata(x, y, z);

		if (block == Blocks.water) {
			if (bMeta == 0) {
				return WATER.copy();
			} else {
				return null;
			}
		} else if (block == Blocks.lava) {
			if (bMeta == 0) {
				return LAVA.copy();
			} else {
				return null;
			}
		} else if (block instanceof IFluidBlock) {
			IFluidBlock fluid = (IFluidBlock) block;
			return fluid.drain(worldObj, x, y, z, true);
		}
		return null;
	}

	public static FluidStack getFluidForFilledItem(ItemStack container) {

		if (container != null && container.getItem() instanceof IFluidContainerItem) {
			return ((IFluidContainerItem) container.getItem()).getFluid(container);
		}
		return FluidContainerRegistry.getFluidForFilledItem(container);
	}

	public static boolean isFluidEqualOrNull(FluidStack resourceA, FluidStack resourceB) {

		return resourceA == null || resourceB == null || resourceA.isFluidEqual(resourceB);
	}

	public static boolean isFluidEqualOrNull(Fluid fluidA, FluidStack resourceB) {

		return fluidA == null || resourceB == null || fluidA == resourceB.getFluid();
	}

	public static boolean isFluidEqualOrNull(Fluid fluidA, Fluid fluidB) {

		return fluidA == null || fluidB == null || fluidA == fluidB;
	}

	public static boolean isFluidEqual(FluidStack resourceA, FluidStack resourceB) {

		return resourceA != null && resourceA.isFluidEqual(resourceB);
	}

	public static boolean isFluidEqual(Fluid fluidA, FluidStack resourceB) {

		return fluidA != null && resourceB != null && fluidA == resourceB.getFluid();
	}

	public static boolean isFluidEqual(Fluid fluidA, Fluid fluidB) {

		return fluidA != null && fluidB != null && fluidA.equals(fluidB);
	}

}
