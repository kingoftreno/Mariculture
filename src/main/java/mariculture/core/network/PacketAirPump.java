package mariculture.core.network;

import mariculture.core.blocks.TileAirPump;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class PacketAirPump extends PacketCoords {
	public PacketAirPump() {}
	public PacketAirPump(int x, int y, int z) {
		super(x, y, z);
	}
	
	@Override
	public void handle(Side side, EntityPlayer player) {
		((TileAirPump) player.worldObj.getTileEntity(x, y, z)).animate = true;
	}
}
