package mariculture.core.handlers;

import mariculture.Mariculture;
import mariculture.core.helpers.EnchantHelper;
import mariculture.core.lib.Modules;
import mariculture.core.network.PacketSyncMirror;
import mariculture.magic.Magic;
import mariculture.magic.MirrorData;
import mariculture.magic.ResurrectionTracker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;

public class ServerFMLEvents {
	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent event) {
		EntityPlayer player = event.player;
		if(Modules.magic.isActive() && player instanceof EntityPlayerMP) {
			Mariculture.packets.sendTo(new PacketSyncMirror(MirrorData.getInventoryForPlayer(player)), (EntityPlayerMP) player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if(Modules.magic.isActive() && EnchantHelper.exists(Magic.resurrection)) {
			ResurrectionTracker.onPlayerRespawn(event.player);
		}
	}
}
