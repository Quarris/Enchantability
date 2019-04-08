package quarris.enchantability.mod.config;

import net.minecraftforge.common.config.Config;
import quarris.enchantability.mod.Enchantability;

@Config(modid = Enchantability.MODID, category = "enchants")
public class ConfigEnchants {

    @Config.Comment("Registers the specified enchants. Set to false to disable them.")
    public static InitEnchants initEnchants = new InitEnchants();

    public static ModifyEnchants modifyEnchants = new ModifyEnchants();
    
    
    public static class InitEnchants {
        public boolean blastProtection = true;
        public boolean efficiency = true;
        public boolean fireAspect = true;
        public boolean infinity = true;
        public boolean knockback = true;
        public boolean projectileProtection = true;
        public boolean punch = true;
        public boolean silktouch = true;
        public boolean featherFalling = true;
        public boolean vanishing = true;
        public boolean frostWalker = true;
        public boolean smite = true;
        //public boolean binding = true;
        public boolean looting = true;
        public boolean fortune = true;
        public boolean mending = true;
    }

    public static class ModifyEnchants {
        @Config.Comment({
                "Efficiency: Adds a list of ItemStacks or OreDict entries to the Efficiency Enchant Effect.",
                "Ore Dict Format: \"oreName\" eg. \"plankWood\" for Wooden Planks",
                "Item Stack Format: \"modid:itemname#meta\" eg. \"minecraft:arrow\" for Arrows or \"minecraft:dye#15\" for Bone Meal",
                "Note: For meta you can leave blank to allow all values of meta, for example \"minecraft:concrete_powder\" will allow all colours"})
        public String[] itemsForEfficiency = new String[] {
                "plankWood", "stickWood", "fenceWood",
                "dye", "paper", "torch",
                "sandstone",
                "minecraft:arrow", "minecraft:concrete_powder", "minecraft:ladder",
                "minecraft:nether_brick_fence", "minecraft:cobblestone_wall",
                "minecraft:sign", "minecraft:item_frame", "minecraft:flower_pot",
                "minecraft:redstone_torch", "minecraft:wooden_button", "minecraft:wooden_pressure_plate",
                "minecraft:stone_button", "minecraft:stone_pressure_plate",
                "minecraft:rail", "minecraft:melon_seeds", "minecraft:pumpkin_seeds", "minecraft:sugar",
                "minecraft:fire_charge", "minecraft:firework_charge", "minecraft:leather",
                "minecraft:bread", "minecraft:lead", "minecraft:blaze_powder"
        };

        @Config.Comment({"Infinity: Do offset position from world spawn when travelling from End to Overworld"})
        public boolean offsetEndPositionFromWorldSpawn = true;

        @Config.RangeDouble(min = 0.5, max = 33.5)
        @Config.Comment({"Knockback: Adds a modifier to the reach distance, in terms of blocks"})
        public double knockbackModifier = 0.5;

        @Config.RangeDouble(min = 5.0)
        @Config.Comment({"Smite: Modifies the chance of causing lightning. The higher this value, the lower the chance.",
                         "At 5.0, the chance with diamond sword is 100%"})
        public float smiteChance = 50.0f;

        @Config.Comment("Smite: Should Smite effect be bound by sword tiers.")
        public boolean useSwordTiers = true;

        @Config.RangeDouble(min = 0)
        @Config.Comment({"Fortune: An experience multiplier to be applied for getting xp from smelting.",
                         "{xp = tier * multiplier}, note that the xp does not have a 100% of dropping."})
        public float fortuneMultiplier = 1.0f;

    }
}
