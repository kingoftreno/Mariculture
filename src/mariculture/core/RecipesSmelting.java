package mariculture.core;

import mariculture.api.core.FuelInfo;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.core.RecipeSmelter;
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
	 					RecipeHelper.addMelting(stack, temperature, FluidRegistry.getFluidStack(fluid, volume[i]), output, chance);
					} else {
						RecipeHelper.addMelting(stack, temperature, FluidRegistry.getFluidStack(fluid, volume[i]));
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
		MaricultureHandlers.smelter.addSolidFuel(new ItemStack(Block.coalBlock, 1, 0), new FuelInfo(2000, 432, 10800));
		MaricultureHandlers.smelter.addSolidFuel(new ItemStack(Item.coal, 1, 0), new FuelInfo(2000, 48, 1200));
		MaricultureHandlers.smelter.addSolidFuel(new ItemStack(Item.coal, 1, 1), new FuelInfo(1500, 32, 800));
		MaricultureHandlers.smelter.addSolidFuel(new ItemStack(Block.wood), new FuelInfo(750, 8, 80));
		MaricultureHandlers.smelter.addSolidFuel(new ItemStack(Block.planks), new FuelInfo(300, 4, 20));
		MaricultureHandlers.smelter.addLiquidFuel(FluidRegistry.getFluidStack(FluidDictionary.natural_gas, 1), new FuelInfo(2000, 16, 1200));
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
		
		addRecipe(FluidDictionary.aluminum, MetalRates.MATERIALS, new Object[] { 
				"oreAluminum", "nuggetAluminum", "ingotAluminum", 
					"blockAluminum", "dustAluminum" }, aluminum, new ItemStack(Item.clay), 5);
		RecipeHelper.addIngotCasting(FluidDictionary.aluminum, "Aluminum");
		
		addRecipe(FluidDictionary.titanium, MetalRates.MATERIALS, new Object[] { 
				"oreRutile", "nuggetTitanium", "ingotTitanium", "blockTitanium", "dustTitanium" }, 
						titanium, new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 2);
		RecipeHelper.addIngotCasting(FluidDictionary.titanium, "Titanium");

		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.RUTILE), titanium, 
				FluidRegistry.getFluidStack(FluidDictionary.rutile, MetalRates.ORE), new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 2);
		
		FluidStack moltenRutile = FluidRegistry.getFluidStack(FluidDictionary.rutile, MetalRates.INGOT);
		FluidStack moltenMagnesium = FluidRegistry.getFluidStack(FluidDictionary.magnesium, MetalRates.INGOT);
		FluidStack moltenTitanium = FluidRegistry.getFluidStack(FluidDictionary.titanium, MetalRates.INGOT);
		//Making Titanium from Molten Rutile and Magnesium in a VAT takes 3 seconds
		RecipeHelper.addFluidAlloy(moltenRutile, moltenMagnesium, moltenTitanium, 3);
		
		//Gold Back
		RecipeHelper.addMelting(new ItemStack(Block.pressurePlateGold), gold, FluidDictionary.gold, MetalRates.INGOT * 2);
		//Iron Back
		RecipeHelper.addMelting(new ItemStack(Item.bucketEmpty), iron, FluidDictionary.iron, MetalRates.INGOT * 3);
		RecipeHelper.addMelting(new ItemStack(Item.doorIron), iron, FluidDictionary.iron, MetalRates.INGOT * 6);
		RecipeHelper.addMelting(new ItemStack(Block.fenceIron), iron, FluidDictionary.iron, (int) (MetalRates.INGOT * 0.25));
		RecipeHelper.addMelting(new ItemStack(Item.shears), iron, FluidDictionary.iron, MetalRates.INGOT * 2);
		RecipeHelper.addMelting(new ItemStack(Block.anvil), iron, FluidDictionary.iron, MetalRates.INGOT * 32);
		RecipeHelper.addMelting(new ItemStack(Block.pressurePlateIron), iron, FluidDictionary.iron, MetalRates.INGOT * 2);
		
		//Glass
		RecipeHelper.addMelting(new ItemStack(Block.sand), 1000, FluidDictionary.glass, 1000);
		RecipeHelper.addMelting(new ItemStack(Block.glass), 900, FluidDictionary.glass, 1000);
		RecipeHelper.addMelting(new ItemStack(Block.thinGlass), 500, FluidDictionary.glass, 375);
		
		//Ice > Water
		RecipeHelper.addMelting(new ItemStack(Block.ice), 1, "water", 1000);
		
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
		
		//Random Metal Dust!!!
		MaricultureHandlers.smelter.addRecipe(new RecipeSmelter(new ItemStack(Core.materials, 1, MaterialsMeta.DUST_UNKNOWN),
				650, new FluidStack[] { get(FluidDictionary.magnesium), get(FluidDictionary.lead), get(FluidDictionary.copper),
			get(FluidDictionary.nickel), get(FluidDictionary.aluminum), get(FluidDictionary.iron), get(FluidDictionary.silver)},
			new int[] { 2, 7, 6, 10, 15, 20, 25 }, new ItemStack(Core.materials, 1, MaterialsMeta.DUST_SALT), 1));
		
		//Limestone > Quicklime
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE), 825, get(FluidDictionary.quicklime, 1000));
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_BRICK), 825, get(FluidDictionary.quicklime, 1000));
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_SMOOTH), 825, get(FluidDictionary.quicklime, 1100));
		RecipeHelper.addMelting(new ItemStack(Core.oreBlocks, 1, OresMeta.LIMESTONE_CHISELED), 825, get(FluidDictionary.quicklime, 1100));
		
		//Carbon > Molten Carbon
		RecipeHelper.addMelting(new ItemStack(Item.coal, 1, 0), 2000, get(FluidDictionary.coal, 100));
		RecipeHelper.addMelting(new ItemStack(Item.coal, 1, 1), 2000, get(FluidDictionary.coal, 50));
		RecipeHelper.addMelting(new ItemStack(Block.wood), 2000, get(FluidDictionary.coal, 50));
	}
	
	public static FluidStack get(String name, int vol) {
		return FluidRegistry.getFluidStack(name, vol);
	}
	
	public static FluidStack get(String name) {
		return FluidRegistry.getFluidStack(name, MetalRates.INGOT);
	}
}
