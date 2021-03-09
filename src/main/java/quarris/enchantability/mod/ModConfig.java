package quarris.enchantability.mod;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.minecraftforge.common.ForgeConfigSpec.*;

public class ModConfig {

    // Misc Configs
    public IntValue buttonXOffset;
    public IntValue buttonYOffset;

    // Enabled Features
    public BooleanValue enableAdrenaline;
    public BooleanValue enableAirWalker;
    public BooleanValue enableBlastResist;
    public BooleanValue enableDeflection;
    public BooleanValue enableDexterity;
    public BooleanValue enableFarReach;
    public BooleanValue enableFastBreak;
    public BooleanValue enableFirePraise;
    public BooleanValue enableGluttony;
    public BooleanValue enableGravity;
    public BooleanValue enableHeat;
    public BooleanValue enableLavaSwim;
    public BooleanValue enableLoyalty;
    public BooleanValue enableLure;
    public BooleanValue enableMetalFist;
    public BooleanValue enableSmite;
    public BooleanValue enableStrike;
    public BooleanValue enableSwiftCharge;
    public BooleanValue enableVoid;

    // Adrenaline
    public IntValue adrenalineCooldown;
    public IntValue adrenalineDamageTakenTime;
    public IntValue adrenalineDamageDealtTime;
    public DoubleValue adrenalineDamageTakenThreshold;
    public DoubleValue adrenalineDamageDealtThreshold;
    public IntValue adrenalineDuration1;
    public IntValue adrenalineDuration2;
    public IntValue adrenalineDuration3;

    // Dexterity
    public ConfigValue<List<String>> dexterityTags;
    public ConfigValue<List<String>> dexterityItems;

    // Gluttony
    public BooleanValue enableCookie;
    public BooleanValue enableRabbitStew;

    // Heat
    public IntValue additionalTickSpeed;
    public IntValue heatRange;
    public BooleanValue treatBlacklistAsWhitelist;
    public ConfigValue<List<String>> tileBlacklist;

    // Loyalty
    public ConfigValue<List<Integer>> loyaltyReviveTimes;

    // Metal Fist
    public DoubleValue speedMultiplier;


    private static ModConfig instance;

    public static ModConfig get() {
        return instance;
    }

    public static void init(Builder builder) {
        instance = new ModConfig(builder);
    }

    public ModConfig(Builder builder) {
        builder.comment("Misc Configs").push("misc");
        buttonXOffset = builder
                .comment("The horizontal offset for the button")
                .defineInRange("buttonXOffset", -18, -1000, 1000);
        buttonYOffset = builder
                .comment("The vertical offset for the button")
                .defineInRange("buttonYOffset", 143, -1000, 1000);
        builder.pop();

        builder.comment("Enabled Features").push("features");
        enableAdrenaline = builder.define("enableAdrenaline", true);
        enableAirWalker = builder.define("enableAirWalker", true);
        enableBlastResist = builder.define("enableBlastResist", true);
        enableDeflection = builder.define("enableDeflection", false);
        enableDexterity = builder.define("enableDexterity", true);
        enableFarReach = builder.define("enableFarReach", true);
        enableFastBreak = builder.define("enableFastBreak", true);
        enableFirePraise = builder.define("enableFirePraise", true);
        enableGluttony = builder.define("enableGluttony", true);
        enableGravity = builder.define("enableGravity", true);
        enableHeat = builder.define("enableHeat", true);
        enableLavaSwim = builder.define("enableLavaSwim", true);
        enableLoyalty = builder.define("enableLoyalty", true);
        enableLure = builder.define("enableLure", true);
        enableMetalFist = builder.define("enableMetalFist", true);
        enableSmite = builder.define("enableSmite", true);
        enableStrike = builder.define("enableStrike", true);
        enableSwiftCharge = builder.define("enableSwiftCharge", true);
        enableVoid = builder.define("enableVoid", true);
        builder.pop();

        builder.comment("Enchant Configs").push("enchants");

        builder.comment("Adrenaline").push("adrenaline");
        adrenalineCooldown = builder
                .comment("The cooldown (in ticks) after the adrenaline has worn off")
                .defineInRange("adrenalineCooldown", 600, 0, 72000);

        adrenalineDamageTakenTime = builder
                .comment("The time (in ticks) to track the damage dealt for the corresponding threshold for activation")
                .defineInRange("adrenalineDamageTakenTime", 80, 10, Integer.MAX_VALUE);

        adrenalineDamageDealtTime = builder
                .comment("The time (in ticks) to track the damage taken for the corresponding threshold for activation")
                .defineInRange("adrenalineDamageDealtTime", 40, 10, Integer.MAX_VALUE);

        adrenalineDamageTakenThreshold = builder
                .comment("The damage needed to be taken within the tracked time to activate")
                .defineInRange("adrenalineDamageTakenThreshold", 12d, 1d, 100d);

        adrenalineDamageDealtThreshold = builder
                .comment("The damage needed to be dealt within the tracked time to activate")
                .defineInRange("adrenalineDamageDealtThreshold", 50d, 1d, 2000d);

        builder.comment("Duration (in ticks) for the effects of Adrenaline per level of enchant");
        adrenalineDuration1 = builder.defineInRange("adrenalineDuration1", 100, 1, 12000);
        adrenalineDuration2 = builder.defineInRange("adrenalineDuration2", 300, 1, 12000);
        adrenalineDuration3 = builder.defineInRange("adrenalineDuration3", 800, 1, 12000);
        builder.pop();

        builder.comment("Dexterity").push("dexterity");
        dexterityTags = builder.define("dexterityTags", defaultTags());
        dexterityItems = builder.define("dexterityItems", defaultItems());
        builder.pop();

        builder.comment("Gluttony").push("gluttony");
        enableRabbitStew = builder.define("enableRabbitStew", true);
        enableCookie = builder.define("enableCookie", true);
        builder.pop();

        builder.comment("Heat").push("heat");
        additionalTickSpeed = builder.defineInRange("additionalTickSpeed", 3, 1, 64);
        heatRange = builder.defineInRange("heatRange", 32, 1, 256);
        treatBlacklistAsWhitelist = builder.define("treatBlacklistAsWhitelist", false);
        tileBlacklist = builder.define("tileBlacklist", defaultTileBlacklist());
        builder.pop();

        builder.comment("Loyalty").push("loyalty");
        loyaltyReviveTimes = builder
                .comment("Time (in ticks) until the pet revives", "Times for level 1/2/3 is represented as [1, 2, 3]")
                .define("reviveTimes", defaultLoyaltyReviveTimes());
        builder.pop();

        builder.comment("Metal Fist").push("metal_fist");
        speedMultiplier = builder
                .comment("The multiplier for the break speed when wearing Sharpness 5+ book",
                        "Set to '1.0' to disable increased speed on Sharpness 5")
                .defineInRange("speedMultiplier", 2D, 1D, 10D);
        builder.pop();

        builder.pop();
    }

    public void reloadTags() {
        for (String itemName : this.dexterityItems.get()) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
            if (item != null) {
                EnchantabilityApi.addToDexterityList(item);
            } else {
                ModRef.LOGGER.warn("Registered item for dexterity doesn't exist, " + itemName);
            }
        }

        for (String tagName : this.dexterityTags.get()) {
            ITag<Item> tag = ItemTags.getCollection().get(new ResourceLocation(tagName));

            if (tag != null) {
                EnchantabilityApi.addToDexterityList(tag);
            } else {
                ModRef.LOGGER.warn("Registered tag for dexterity doesn't exist, " + tagName);
            }
        }
    }

    private static List<Integer> defaultLoyaltyReviveTimes() {
        List<Integer> defaults = new ArrayList<>(3);
        defaults.add(60 * 20);
        defaults.add(45 * 20);
        defaults.add(30 * 20);
        return defaults;
    }

    private static List<String> defaultTags() {
        ITag.INamedTag<Item>[] tags = new ITag.INamedTag[]{
                Tags.Items.SANDSTONE,
                Tags.Items.RODS_WOODEN,
                ItemTags.FENCES,
                Tags.Items.DYES,
                ItemTags.PLANKS,
                ItemTags.ARROWS,
                ItemTags.SIGNS,
                ItemTags.SMALL_FLOWERS,
                ItemTags.BUTTONS,
                ItemTags.WOODEN_PRESSURE_PLATES,
                ItemTags.RAILS,
                Tags.Items.CHESTS,
                Tags.Items.LEATHER
        };

        return Arrays.stream(tags)
                .map(tag -> tag.getName().toString())
                .collect(Collectors.toList());
    }

    private static List<String> defaultItems() {
        Item[] items = new Item[]{
                Items.FIRE_CHARGE,
                Items.SUGAR,
                Items.FIREWORK_STAR,
                Items.BREAD
        };

        return Arrays.stream(items)
                .map(item -> item.getRegistryName().toString())
                .collect(Collectors.toList());
    }

    private static List<String> defaultTileBlacklist() {
        TileEntityType<?>[] types = new TileEntityType[]{
                TileEntityType.HOPPER,
                TileEntityType.PISTON,
                TileEntityType.BEACON,
                TileEntityType.CONDUIT,
                TileEntityType.BELL,
                TileEntityType.BEEHIVE,
                TileEntityType.DAYLIGHT_DETECTOR,
                TileEntityType.END_GATEWAY
        };

        List<String> list = Arrays.stream(types)
                .map(type -> type.getRegistryName().toString())
                .collect(Collectors.toList());

        list.add("enchantability:air");
        return list;
    }
}
