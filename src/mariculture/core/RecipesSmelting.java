package mariculture.core;

import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
import mariculture.api.core.RecipeSmelter.SmelterOutput;
import mariculture.core.helpers.RecipeHelper;
import mariculture.core.lib.CraftingMeta;
import mariculture.core.lib.GlassMeta;
import mariculture.core.lib.MaterialsMeta;
import mariculture.core.lib.MetalRates;
import mariculture.core.lib.OresMeta;
import mariculture.core.util.FluidDictionary;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class RecipesSmelting {
	public static int iron = 1538;
	public static int gold = 1064;
	public static int tin = 232;
	public static int copper = 1085;
	public static int silver = 962;
	public static int lead = 328;
	public static int magnesium = 650;
	public static int nickel = 1455;
	public static int bronze = 950;
	public static int steel = 1370;
	public static int aluminum = 660;
	public static int titanium = 1662;
	
	public static void addRecipe(String fluid, int[] volume, Object[] items, int temperature, ItemStack output, int chance) {
		String origFluid = fluid;
		for(int i = 0; i < items.length; i++) {
			if(items[i] != null && volume[i] > 0) {
				Object item = items[i];
				ItemStack stack = null;
				if(item instanceof String) {
					if(OreDictionary.getOres((String)item).size() > 0) {
						stack = OreDictionary.getOres((String) item).get(0);
					}
				} else if(item instanceof ItemStack) {
					stack = (ItemStack) item;
				} else if (item instanceof Item) {
					stack = new ItemStack((Item)item);
				} else if(item instanceof Block) {
					stack = new ItemStack((Block)item);
				}
				
				if(stack != null && FluidRegistry.getFluid(fluid) != null) {
					if(i == 0 || stack.getItem() instanceof ItemTool || stack.getItem() instanceof ItemHoe) {
	 					MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack, temperature, 
								new SmelterOutput(FluidRegistry.getFluidStack(fluid, volume[i]), output, chance)));
					} else {
						MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(stack, temperature, 
								new SmelterOutput(FluidRegistry.getFluidStack(fluid, volume[i]))));
					}
				}
			}
		}
	}

	public static void add() {
		 addFuels();
		 addMetalRecipes();
	}

	private static void addFuels() {
		MaricultureHandlers.smelter.addFuel(new ItemStack(Block.coalBlock, 1, 0), 432, 2000);
		MaricultureHandlers.smelter.addFuel(new ItemStack(Item.coal, 1, 0), 48, 2000);
		MaricultureHandlers.smelter.addFuel(new ItemStack(Item.coal, 1, 1), 32, 1500);
		MaricultureHandlers.smelter.addFuel("logWood", 8, 750);
		MaricultureHandlers.smelter.addFuel("plankWood", 4, 300);
	}
	
	public static void addFullSet(String fluid, Object[] items, int temp) {
		addRecipe(fluid, MetalRates.MATERIALS, new Object[] { 
				items[0], items[1], items[2], items[3], items[4] }, temp, new ItemStack(Block.stone), 2);
		
		addRecipe(fluid, MetalRates.TOOLS, new Object[] { 
				items[5], items[6], items[7], items[8], items[9] }, temp, new ItemStack(Item.stick), 1);
		
		addRecipe(fluid, MetalRates.ARMOR, new Object[] { 
				items[10], items[11], items[12], items[13] }, temp, null, 0);
	}
	
	public static void addMetal(String fluid, String metal, int temp) {
		addRecipe(FluidDictionary.tin, MetalRates.MATERIALS, new Object[] { 
				"ore" + metal, "nugget" + metal, "ingot" + metal, "block" + metal, "dust" + metal }, temp, new ItemStack(Block.stone), 2);
		
		if(OreDictionary.getOres("ingot" + metal).size() > 0) {
			RecipeHelper.addIngotCasting(fluid, metal);
		}
	}

	
	public static void addMetalRecipes() {
		addFullSet(FluidDictionary.iron, new Object[] {
				"oreIron", "nuggetIron", "ingotIron", "blockIron", "dustIron",
				Item.pickaxeIron, Item.shovelIron, Item.axeIron, Item.swordIron, Item.hoeIron,
				Item.helmetIron, Item.plateIron, Item.legsIron, Item.bootsIron}, iron);
		RecipeHelper.addIngotCasting(FluidDictionary.iron, "Iron");
		
		addFullSet(FluidDictionary.gold, new Object[] {
				"oreGold", "nugetGold", "ingotGold", "blockGold", "dustGold",
				Item.pickaxeGold, Item.shovelGold, Item.axeGold, Item.swordGold, Item.hoeGold,
				Item.helmetGold, Item.plateGold, Item.legsGold, Item.bootsGold}, gold);
		RecipeHelper.addIngotCasting(FluidDictionary.gold, "Gold");
		
		addMetal(FluidDictionary.tin, "Tin", tin);
		addMetal(FluidDictionary.copper, "Copper", copper);
		addMetal(FluidDictionary.silver, "Silver", silver);
		addMetal(FluidDictionary.lead, "Lead", lead);
		addMetal(FluidDictionary.magnesium, "Magnesium", magnesium);
		addMetal(FluidDictionary.nickel, "Nickel", nickel);
		addMetal(FluidDictionary.bronze, "Bronze", bronze);
		addMetal(FluidDictionary.steel, "Steel", steel);
		
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.craftingItem, 1, CraftingMeta.ALUMINUM_SHEET), aluminum, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.aluminum, MetalRates.INGOT * 3), null, 0)));	
		addRecipe(FluidDictionary.aluminum, MetalRates.MATERIALS, new Object[] { 
				"oreAluminum", "nuggetAluminum", "ingotAluminum", 
					"blockAluminum", "dustAluminum" }, aluminum, new ItemStack(Item.clay), 5);
		RecipeHelper.addIngotCasting(FluidDictionary.aluminum, "Aluminum");
		
		addRecipe(FluidDictionary.titanium, MetalRates.MATERIALS, new Object[] { 
				"oreRutile", "nuggetTitanium", "ingotTitanium", "blockTitanium", "dustTitanium" }, 
						titanium, new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 2);
		RecipeHelper.addIngotCasting(FluidDictionary.titanium, "Titanium");
		
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.oreBlocks, 1, OresMeta.RUTILE), titanium,
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.rutile, MetalRates.ORE), 
						new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 2)));
		
		FluidStack moltenRutile = FluidRegistry.getFluidStack(FluidDictionary.rutile, MetalRates.INGOT);
		FluidStack moltenMagnesium = FluidRegistry.getFluidStack(FluidDictionary.magnesium, MetalRates.INGOT);
		FluidStack moltenTitanium = FluidRegistry.getFluidStack(FluidDictionary.titanium, MetalRates.INGOT);
		//Making Titanium from Molten Rutile and Magnesium in a VAT takes 3 seconds
		RecipeHelper.addFluidAlloy(moltenRutile, moltenMagnesium, moltenTitanium, 3);
		
		//Gold Back
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.pressurePlateGold), gold, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.gold, MetalRates.INGOT * 2), null, 0)));
		
		//Iron Back
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Item.bucketEmpty), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 3), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Item.doorIron), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 6), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.fenceIron), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, (int) (MetalRates.INGOT * 0.25)), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Item.shears), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 2), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.craftingItem, 1, CraftingMeta.WHEEL), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 4), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.anvil), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 32), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.pressurePlateIron), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 2), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.hopperBlock), iron, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.iron, MetalRates.INGOT * 5), new ItemStack(Block.planks), 1)));
		
		//Glass
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.sand), 1000, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.glass, FluidContainerRegistry.BUCKET_VOLUME), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.glass), 900, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.glass, FluidContainerRegistry.BUCKET_VOLUME), null, 0)));
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.thinGlass), 500, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidDictionary.glass, 375), null, 0)));
		
		//Ice > Water
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Block.ice), 1, 
				new SmelterOutput(FluidRegistry.getFluidStack(FluidRegistry.WATER.getName(), FluidContainerRegistry.BUCKET_VOLUME), null, 0)));
		
		//Glass > Plastic
		RecipeHelper.addVatItemRecipe(new ItemStack(Core.craftingItem, 1, CraftingMeta.PLASTIC), 
				FluidDictionary.glass, 30000, new ItemStack(Core.glassBlocks, 8, GlassMeta.PLASTIC), 5);
		
		//Water + Lava = Obsidian
		RecipeHelper.addFluidAlloyResultItem(FluidRegistry.getFluidStack("water", 1000), 
				FluidRegistry.getFluidStack("lava", 1000), new ItemStack(Block.obsidian), 2);
		
		//24 Parts Quicklime + 16 Parts Water = Unknown Metal Dust + 10 Parts Water (Takes 10 seconds)
		RecipeHelper.addFluidAlloyResultItemNFluid(FluidRegistry.getFluidStack("water", 16000), 
				FluidRegistry.getFluidStack(FluidDictionary.quicklime, 24000),
				FluidRegistry.getFluidStack("water", 10000), new ItemStack(Core.materials, 1, MaterialsMeta.DUST_UNKNOWN), 10);
	}
}
