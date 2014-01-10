package mariculture.api.core;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public interface ISmelterHandler {	
	/** Add a Melting Recipe
	 * Take note that if you set the liquid output to the same as the ore rate, 
	 * 	then your item will be affected by the purity upgrade */
	public void addRecipe(RecipeSmelter recipe);
	public RecipeSmelter getResult(ItemStack input, ItemStack input2, int temp);
	
	public void addLiquidFuel(FluidStack fluid, FuelInfo info);
	public void addSolidFuel(ItemStack stack, FuelInfo info);
	public FuelInfo getFuelInfo(Object obj);
	/** Get the melting point of an item */
	public int getMeltingPoint(ItemStack stack);
}
