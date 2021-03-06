package mariculture.core.lib.config;

public class Comment {

	public static final String HARDCORE = "This causes your air to drop quicker, the higher the faster it will drop, set to 0 to turn off";
	public static final String REFRESH = "This is how many ticks before updates are sent to the client";
	public static final String PEARL_CHANCE = "This is the chance, that after the tick count is finished, oysters will generate a pearl." + 
														" It's a X in this many chance, so lower = better chance";
	public static final String BAIT = "Chance of catching something in an autofisher with this bait - Lower = Better chance";
	public static final String METAL = "This is how many mB a Nugget is worth, the default value is the same as Tinker's Construct";
	public static final String FLUDD = "Whether a server will tell the client to display the fludd animations";
	public static final String CORAL_SPREAD = "This is how fast coral will spread, lower = faster";
	public static final String KELP_SPREAD = "This is how fast Kelp will spread moss to neaby blocks, lower = faster";
	public static final String KELP_GROWTH = "This is the speed that kelp will grow, lower = faster";
	public static final String ENDERDRAGON = "This is whether players can Spawn the Ender Dragon with the Dragon Spawn Egg";
	public static final String RIVER = "This is a list of biome ids, that Mariculture considers to be 'river' biomes (affects where limestone/rutile can spawn)";
	public static final String OCEAN = "This is a list of biome ids, that Mariculture considers to be 'ocean' biomes; (affects where natural gas/coral/kelp can spawn) Take note that Mariculture automatically asumes that BiomesOPlenty ocean types are indeed oceans";
	public static final String ORE = "This section let's you adjust where your ores will spawn and how much. Please note when it comes to 'chance', that LOWER = more common, as it's a x in this many chance.";
	public static final String PUMP_MANUAL = "Whether or not the Air Pump can be activated by right clicking on it";
	public static final String PUMP_REDSTONE = "Whether or not giving the Air Pump a Redstone Signal will cause it to provide air";
	public static final String PUMP_RF = "Whether an Air Pump can be powered by Redstone Flux";
	public static final String WHITELIST = "Place Ore Dictionary Prefixes here, that are acceptable to be converted";
	public static final String TANK_UPDATE = "This is how many seconds must pass before the feeder will 'tick' to check for the tank size, If set to 0, the size will only be set when you open the interface";
	public static final String FISH_FOOD_TICK = "This is how many minecraft ticks, before attempting to pick up fish food, set to 0 to disable";
	public static final String EFFECT_TICK = "This is how many ticks for an effect to occur in a fish tank, such as poison or regen";
	public static final String DEATH_TICK = "This is the number of ticks, before 'damaging' a fish that can't stay alive in the current feeder, set to 0 to disable";
	public static final String DRAGON_EGG_BASE = "This is a 1 in this many chance for the chance to get a Spawn Ender Dragon from a Dragon Egg";
	public static final String DRAGON_EGG_ETHEREAL = "Same as the normal chance but this is the chance when you have an ethereal upgrade in the incubator";
	public static final String BIOMESOP_CORAL = "If enabled this will force Coral to only generate in Coral Reef Biomes if they exist in a world, If disabled, will generate in all ocean biomes";
	public static final String BIOMESOP_KELP = "If enabled this will force Kelp Forests to only generate in Kelp Forest Biomes if they exist in a world, If disabled, will generate in all ocean biomes";
	public static final String RETRO_KEY = "This key is what stops, retro gen from generating in chunks over and over, if you want to retro gen again, change the key to any other integer";
	public static final String RETRO = "Retro-Generation allows you to generate ores/other features in your existing worlds, without you having to go off and explore new areas. If you set all to true, it will override the individual settings for retro-gen";
	public static final String JEWELRY_TICK_RATE = "This is how many ticks before the server will send an update to a player about what level enchant they have in their mirror inventory";
	public static final String CAN_WORK_TICK = "This is how many ticks before blocks check, whether they can perform their operations. This is what causes the delay for machines to start working";
	public static final String BLACKLIST = "Place Ore Dictionary names, to blacklist here (this only works if use white list is set to false)";
	
	public static final String KELP_FOREST_CHEST_CHANCE = "The higher the number the less common a chest will be, this roughly works out to this 1 chest per this many blocks of a forest.";
	public static final String KELP_FOREST_START = "The higher the number, the rarer kelp forests will be, this number defines the chance of them spawning";
	public static final String KELP_FOREST_END = "The higher the number, the larger kelp forests will be, this number defines the chance of them no longer spawning";
}
