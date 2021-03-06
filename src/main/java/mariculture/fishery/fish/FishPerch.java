package mariculture.fishery.fish;

import mariculture.api.fishery.EnumRodQuality;
import mariculture.api.fishery.ILootHandler.LootQuality;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FishPerch extends FishSpecies {
	public FishPerch(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.OCEAN;
	}

	@Override
	public int getLifeSpan() {
		return 25;
	}

	@Override
	public int getFertility() {
		return 83;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 8D);
	}
	
	@Override
	public int getCatchChance() {
		return 10;
	}
	
	@Override
	public LootQuality getLootQuality() {
		return LootQuality.GOOD;
	}
	
	@Override
	public double getFishOilVolume() {
		return 1.000;
	}

	@Override
	public ItemStack getLiquifiedProduct() {
		return new ItemStack(Items.leather);
	}

	@Override
	public int getLiquifiedProductChance() {
		return 5;
	}

	@Override
	public int[] getChestGenChance() {
		return new int[] { 1, 1, 3 };
	}
	
	@Override
	public int getFishMealSize() {
		return 3;
	}
}
