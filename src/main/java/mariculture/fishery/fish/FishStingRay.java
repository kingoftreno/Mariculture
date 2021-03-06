package mariculture.fishery.fish;

import java.util.Random;

import mariculture.api.fishery.fish.EnumFishGroup;
import mariculture.api.fishery.fish.FishSpecies;
import mariculture.core.Core;
import mariculture.core.lib.MaterialsMeta;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class FishStingRay extends FishSpecies {
	public FishStingRay(int id) {
		super(id);
	}

	@Override
	public EnumFishGroup getGroup() {
		return EnumFishGroup.FLATFISH;
	}

	@Override
	public int getLifeSpan() {
		return 37;
	}

	@Override
	public int getFertility() {
		return 93;
	}

	@Override
	public boolean isDominant() {
		return true;
	}
	
	@Override
	public void addFishProducts() {
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_WATER), 2D);
		addProduct(new ItemStack(Core.materials, 1, MaterialsMeta.DROP_POISON), 4D);
		addProduct(new ItemStack(Items.spider_eye), 3D);
	}
	
	@Override
	public boolean caughtAsRaw() {
		return false;
	}

	@Override
	public int getCatchChance() {
		return 30;
	}
	
	@Override
	public double getFishOilVolume() {
		return 0.170;
	}

	@Override
	public void onConsumed(World world, EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.poison.id, 600, 0));
		player.getFoodStats().addStats(2, 2F);
		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
	}

	@Override
	public void affectLiving(EntityLivingBase living) {
		if (living instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) living;
			Random rand = new Random();
			int difficulty = player.worldObj.difficultySetting.getDifficultyId();
			if (difficulty > 0) {
				int chance = 40 - (difficulty * 10);
				if (rand.nextInt(chance) == 0) {
					player.addPotionEffect(new PotionEffect(Potion.poison.id, difficulty * 100, 1, true));
				}
			}
		}
	}

	@Override
	public int[] getChestGenChance() {
		return null;
	}
	
	@Override
	public int getFishMealSize() {
		return 2;
	}
}
