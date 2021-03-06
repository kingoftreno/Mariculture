package mariculture.magic.jewelry;


public class ItemNecklace extends ItemJewelry {
	@Override
	public JewelryType getType() {
		return JewelryType.NECKLACE;
	}

	@Override
	public int getMaxDurability() {
		return 100;
	}

	@Override
	public int getMaxLevel() {
		return 200;
	}

	@Override
	public boolean renderBinding() {
		return false;
	}
}
