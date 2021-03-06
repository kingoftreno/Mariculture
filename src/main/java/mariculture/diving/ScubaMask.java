package mariculture.diving;

import mariculture.core.helpers.PlayerHelper;
import mariculture.core.lib.ArmorSlot;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ScubaMask {
	private static int tick = 0;
	private static final float LEVEL = 10F;

	public static void damage(EntityPlayer player) {
		if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.scubaMask)) {
			ItemStack mask = PlayerHelper.getArmor(player, ArmorSlot.HAT, Diving.scubaMask);
			if(mask != null) {
				if(mask.hasTagCompound()) {
					if (mask.stackTagCompound.getBoolean("ScubaMaskOnOutOfWater") == true) {
						if (!player.isInsideOfMaterial(Material.water)) {
							tick++;
							if(tick >= 60) {
								tick = 0;
								mask.damageItem(1, player);
							}
			
							if (mask.stackSize <= 0) {
								player.inventory.armorInventory[ArmorSlot.HAT] = null;
							}
						}
					}
				}
			}
		}
	}
	
	public static boolean init(EntityPlayer player) {
		if(player.worldObj.isRemote) {
			if (PlayerHelper.hasArmor(player, ArmorSlot.HAT, Diving.scubaMask)) {
				if (player.isInsideOfMaterial(Material.water)) {
					activate(player);
				}  else {
					ItemStack mask = PlayerHelper.getArmor(player, ArmorSlot.HAT, Diving.scubaMask);
					if(mask != null) {
						if (mask.hasTagCompound() && mask.stackTagCompound.getBoolean("ScubaMaskOnOutOfWater") == true) {
							activate(player);
						} else {
							deactivate(player);
						}
						
						return true;
					}
					
					deactivate(player);
				}
			} else {
				deactivate(player);
			}
		}
		
		return false;
	}
	
	private static void activate(EntityPlayer player) {
		if(Minecraft.getMinecraft().thePlayer == player) {
			float gamma = Minecraft.getMinecraft().gameSettings.gammaSetting;
			if(gamma <= 1) {					
				NBTTagCompound playerData = player.getEntityData();
				ItemStack mask = PlayerHelper.getArmor(player, ArmorSlot.HAT, Diving.scubaMask);
				if(mask != null) {
					if(!mask.hasTagCompound()) {
						mask.setTagCompound(new NBTTagCompound());
					}
										
					mask.stackTagCompound.setFloat("gamma", gamma);
						
				}
			}
				
			Minecraft.getMinecraft().gameSettings.gammaSetting=LEVEL;
		}
	}
	
	private static void deactivate(EntityPlayer player) {
		if(Minecraft.getMinecraft().thePlayer == player) {
			if(Minecraft.getMinecraft().gameSettings.gammaSetting > 1F) {
				float gamma = 0.75F;
				ItemStack mask = PlayerHelper.getArmor(player, ArmorSlot.HAT, Diving.scubaMask);
				if(mask != null) {
					if(mask.hasTagCompound()) {
						gamma = mask.stackTagCompound.getFloat("gamma");
						
					}
				}

				Minecraft.getMinecraft().gameSettings.gammaSetting=LEVEL;
			}
		}
	}
}
