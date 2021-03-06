package mariculture.fishery.fish;

import java.util.Arrays;
import java.util.List;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.EnumFishWorkEthic;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FishNether extends FishSpecies {
	public FishNether(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.NETHER;
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
		return false;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_NETHER), 5D);
	}

	@Override
	public int getTankLevel() {
		return 3;
	}
	
	@Override
	public boolean caughtAsRaw() {
		return false;
	}
	
	@Override
	public List<EnumBiomeType> getCatchableBiomes() {
		return Arrays.asList(new EnumBiomeType[] { EnumBiomeType.HELL });
	}

	@Override
	public int getCatchChance() {
		return 70;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.155;
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getBaseProductivity() {
		return EnumFishWorkEthic.HARDWORKER.getMultiplier();
	}
	
	@Override
	public int getFishMealSize() {
		return 1;
	}
}
