package mariculture.fishery;

import java.util.Random;

import mariculture.api.core.EnumBiomeType;
import mariculture.api.core.IUpgradable;
import mariculture.api.core.MaricultureHandlers;
import mariculture.api.fishery.Fishing;
import mariculture.api.fishery.IFishHelper;
import mariculture.api.fishery.fish.EnumSalinityType;
import mariculture.api.fishery.fish.FishDNA;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.fishery.blocks.TileFeeder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class FishHelper implements IFishHelper {
	public static int MALE = 0;
	public static int FEMALE = 1;

	@Override
	public ItemStack makePureFish(FishSpecies species) {
		ItemStack fishStack = new ItemStack(Fishery.fishy);
		if (!fishStack.hasTagCompound()) {
			fishStack.setTagCompound(new NBTTagCompound());
		}

		for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
			FishDNA.DNAParts.get(i).addDNA(fishStack, FishDNA.DNAParts.get(i).getDNAFromSpecies(species));
			FishDNA.DNAParts.get(i).addLowerDNA(fishStack, FishDNA.DNAParts.get(i).getDNAFromSpecies(species));
		}

		return fishStack;
	}
	
	@Override
	public ItemStack makePureFish(FishSpecies species, int gender) {
		return Fishery.gender.addDNA(makePureFish(species), gender);
	}

	public static int[] swapDominance(int dominance1, int dominance2, int option1, int option2, Random rand) {
		int[] array = new int[2];

		if (dominance1 == dominance2) {
			if (rand.nextInt(2) == 0) {
				array[0] = option1;
				array[1] = option2;
			} else {
				array[0] = option2;
				array[1] = option1;
			}
		} else {
			if (dominance1 < dominance2) {
				array[0] = option1;
				array[1] = option2;
			} else {
				array[0] = option2;
				array[1] = option1;
			}
		}

		return array;
	}

	@Override
	public ItemStack makeBredFish(ItemStack egg, Random rand) {
		ItemStack fish = new ItemStack(Fishery.fishy);
		for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
			if (!egg.stackTagCompound.hasKey(FishDNA.DNAParts.get(i).getEggString())) {
				return null;
			}
		}

		for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
			int[] DNAlist = FishDNA.DNAParts.get(i).getDNAList(egg);

			int parent1DNA = DNAlist[rand.nextInt(2)];
			int parent2DNA = DNAlist[rand.nextInt(2) + 2];

			int[] babyDNA = FishDNA.DNAParts.get(i).attemptMutation(parent1DNA, parent2DNA);

			FishDNA.DNAParts.get(i).addDNA(fish, babyDNA[0]);
			FishDNA.DNAParts.get(i).addLowerDNA(fish, babyDNA[1]);
		}

		/* Mutate the fish species */
		int species1 = Fishery.species.getDNA(fish);
		int species2 = Fishery.species.getLowerDNA(fish);

		int chance = Fishing.mutation.getMutationChance(getSpecies(species1), getSpecies(species2));
		if (chance > 0 && species1 != species2) {
			FishSpecies newSpecies = Fishing.mutation.getMutation(getSpecies(species1), getSpecies(species2));
			if (newSpecies != null) {
				/* Attempt to mutate the fish * */
				if (rand.nextInt(1000) < chance) {
					for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
						FishDNA.DNAParts.get(i).addDNA(fish, FishDNA.DNAParts.get(i).getDNAFromSpecies(newSpecies));
					}
				}

				/* Attempt to mutate the fish again * */
				if (rand.nextInt(1000) < chance) {
					for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
						FishDNA.DNAParts.get(i).addLowerDNA(fish, FishDNA.DNAParts.get(i).getDNAFromSpecies(newSpecies));
					}
				}
			}
		}

		/* Reorder the fish's DNA to be sorted by dominance */
		for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
			int dna1 = FishDNA.DNAParts.get(i).getDNA(fish);
			int dna2 = FishDNA.DNAParts.get(i).getLowerDNA(fish);

			int[] dominance = FishDNA.DNAParts.get(i).getDominant(dna1, dna2, rand);

			FishDNA.DNAParts.get(i).addDNA(fish, dominance[0]);
			FishDNA.DNAParts.get(i).addLowerDNA(fish, dominance[1]);
		}

		return fish;
	}
	
	@Override
	public boolean canLive(World world, int x, int y, int z, EnumBiomeType[] biomeTypes, EnumSalinityType[] salinity) {
		EnumBiomeType theBiome = MaricultureHandlers.biomeType.getBiomeType(world.getBiomeGenForCoords(x, z));
		EnumSalinityType saltType = theBiome.getSalinity();
		
		int min = biomeTypes[0].minTemp();
		int max = biomeTypes[0].maxTemp();
		int temp = theBiome.baseTemp();
		
		//Change the biome type if it's upgradable
		if(world.getTileEntity(x, y, z) instanceof IUpgradable) {
			IUpgradable tile = (IUpgradable) world.getTileEntity(x, y, z);
			if(MaricultureHandlers.upgrades.hasUpgrade("salinator", tile))
				saltType = EnumSalinityType.SALT;
			if(MaricultureHandlers.upgrades.hasUpgrade("filter", tile))
				saltType = EnumSalinityType.FRESH;
			if(MaricultureHandlers.upgrades.hasUpgrade("ethereal", tile))
				saltType = EnumSalinityType.MAGIC;
			temp += MaricultureHandlers.upgrades.getData("temp", tile);
		}
		
		//Check the salt type of this location
		boolean saltMatches = false;
		for(EnumSalinityType salt: salinity) {
			if(salt.equals(saltType))
				saltMatches = true;
		}
		
		if(!saltMatches) return false;
		
		//Check the water temperatures
		for(EnumBiomeType type: biomeTypes) {
			if(type.minTemp() < min)
				min = type.minTemp();
			if(type.maxTemp() > max)
				max = type.maxTemp();
		}
		
		if(temp >= min && temp <= max) {
			return true;
		}
		
		return false;
	}

	@Override
	public boolean biomeMatches(BiomeGenBase biome, EnumBiomeType[] biomeTypes) {
		EnumBiomeType type = MaricultureHandlers.biomeType.getBiomeType(biome);
		
		for(int i = 0; i < biomeTypes.length; i++) {
			if(biomeTypes[i] != null) {
				if (biomeTypes[i] == type) {
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public boolean isPure(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.getInteger("SpeciesID") == stack.stackTagCompound.getInteger("lowerSpeciesID")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isMale(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (!stack.stackTagCompound.hasKey("Gender")) {
				return false;
			}

			if (stack.stackTagCompound.getInteger("Gender") == MALE) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isFemale(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (!stack.stackTagCompound.hasKey("Gender")) {
				return false;
			}

			if (Fishery.gender.getDNA(stack) == FEMALE) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEgg(ItemStack stack) {
		if (stack.hasTagCompound()) {
			if (stack.stackTagCompound.hasKey("isEgg")) {
				return true;
			}
		}

		return false;
	}

	@Override
	public ItemStack generateEgg(ItemStack fish1, ItemStack fish2) {
		ItemStack egg = new ItemStack(Fishery.fishy);
		egg.setTagCompound(new NBTTagCompound());

		for (int i = 0; i < FishDNA.DNAParts.size(); i++) {
			int[] DNAlist = new int[4];
			DNAlist[0] = FishDNA.DNAParts.get(i).getDNA(fish1);
			DNAlist[1] = FishDNA.DNAParts.get(i).getLowerDNA(fish1);
			DNAlist[2] = FishDNA.DNAParts.get(i).getDNA(fish2);
			DNAlist[3] = FishDNA.DNAParts.get(i).getLowerDNA(fish2);
			FishDNA.DNAParts.get(i).addDNAList(egg, DNAlist);
		}

		egg.stackTagCompound.setInteger("currentFertility", -1);
		egg.stackTagCompound.setBoolean("isEgg", true);

		return egg;
	}

	@Override
	public FishSpecies getSpecies(String species) {
		for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
			FishSpecies fish = FishSpecies.speciesList.get(i);

			if (fish.getSpecies().equals(species)) {
				return fish;
			}
		}

		return Fishery.cod;
	}
	
	@Override
	public FishSpecies getSpecies(int id) {
		if (id < FishSpecies.speciesList.size()) {
			for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
				FishSpecies fish = FishSpecies.speciesList.get(i);
				if(fish != null) {
					if(fish.fishID == id) {
						return fish;
					}
				}
			}
		}
		
		return Fishery.cod;
	}

	@Override
	public int getSpeciesID(String species) {
		for (int i = 0; i < FishSpecies.speciesList.size(); i++) {
			FishSpecies fish = FishSpecies.speciesList.get(i);

			if (fish.getSpecies().equals(species)) {
				return FishSpecies.speciesList.get(i).fishID;
			}
		}

		return 0;
	}

	@Override
	public ItemStack makeBredFish(ItemStack input, ItemStack egg, Random rand) {
		return makeBredFish(egg, rand);
	}
}
