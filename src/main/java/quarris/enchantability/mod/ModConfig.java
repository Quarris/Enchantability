package quarris.enchantability.mod;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import quarris.enchantability.api.EnchantabilityApi;
import quarris.enchantability.mod.common.util.ModRef;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModConfig {

    // Enabled Features
    public ForgeConfigSpec.BooleanValue enableAdrenaline;
    public ForgeConfigSpec.BooleanValue enableAirWalker;
    public ForgeConfigSpec.BooleanValue enableBlastResist;
    public ForgeConfigSpec.BooleanValue enableDeflection;
    public ForgeConfigSpec.BooleanValue enableDexterity;
    public ForgeConfigSpec.BooleanValue enableFarReach;
    public ForgeConfigSpec.BooleanValue enableFastBreak;
    public ForgeConfigSpec.BooleanValue enableFirePraise;
    public ForgeConfigSpec.BooleanValue enableGluttony;
    public ForgeConfigSpec.BooleanValue enableGravity;
    public ForgeConfigSpec.BooleanValue enableHeat;
    public ForgeConfigSpec.BooleanValue enableSmite;
    public ForgeConfigSpec.BooleanValue enableStrike;
    public ForgeConfigSpec.BooleanValue enableSwiftCharge;
    public ForgeConfigSpec.BooleanValue enableVoid;

    // Dexterity
    public ForgeConfigSpec.ConfigValue<List<String>> dexterityTags;
    public ForgeConfigSpec.ConfigValue<List<String>> dexterityItems;

    // Gluttony
    public ForgeConfigSpec.BooleanValue enableCookie;
    public ForgeConfigSpec.BooleanValue enableRabbitStew;

    // Heat
    public ForgeConfigSpec.IntValue additionalTickSpeed;
    public ForgeConfigSpec.IntValue heatRange;
    public ForgeConfigSpec.BooleanValue treatBlacklistAsWhitelist;
    public ForgeConfigSpec.ConfigValue<List<String>> tileBlacklist;

    private static ModConfig instance;
    public static ModConfig get() {
        return instance;
    }

    public static void init(ForgeConfigSpec.Builder builder) {
        instance = new ModConfig(builder);
    }

    public ModConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("Enabled Features").push("features");
        enableAdrenaline = builder.define("enableAdrenaline", true);
        enableAirWalker = builder.define("enableAirWalker", true);
        enableBlastResist = builder.define("enableBlastResist", true);
        enableDeflection = builder.define("enableDeflection", true);
        enableDexterity = builder.define("enableDexterity", true);
        enableFarReach = builder.define("enableFarReach", true);
        enableFastBreak = builder.define("enableFastBreak", true);
        enableFirePraise = builder.define("enableFirePraise", true);
        enableGluttony = builder.define("enableGluttony", true);
        enableGravity = builder.define("enableGravity", true);
        enableHeat = builder.define("enableHeat", true);
        enableSmite = builder.define("enableSmite", true);
        enableStrike = builder.define("enableStrike", true);
        enableSwiftCharge = builder.define("enableSwiftCharge", true);
        enableVoid = builder.define("enableVoid", true);
        builder.pop();

        builder.comment("Enchant Configs").push("enchants");

        builder.comment("Dexterity").push("dexterity");
        dexterityTags = builder.define("dexterityTags", defaultTags());
        dexterityItems = builder.define("dexterityItems", defaultItems());
        builder.pop();

        builder.comment("Gluttony").push("gluttony");
        enableRabbitStew = builder.define("enableRabbitStew", true);
        enableCookie = builder.define("enableCookie", true);
        builder.pop();

        builder.comment("Heat").push("heat");
        additionalTickSpeed = builder.defineInRange("additionalTickSpeed", 3, 1, Integer.MAX_VALUE);
        heatRange = builder.defineInRange("heatRange - radius", 32, 1, 256);
        treatBlacklistAsWhitelist = builder.define("treatBlacklistAsWhitelist", false);
        tileBlacklist = builder.define("tileBlacklist", defaultTileBlacklist());
        builder.pop();

        builder.pop();
    }

    public void reloadTags() {
        for (String itemName : this.dexterityItems.get()) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
            if (item != null) {
                EnchantabilityApi.getInstance().addToDexterityList(item);
            } else {
                ModRef.LOGGER.warn("Registered item for dexterity doesn't exist, " + itemName);
            }
        }

        for (String tagName : this.dexterityTags.get()) {
            Tag<Item> tag = ItemTags.getCollection().get(new ResourceLocation(tagName));

            if (tag != null) {
                EnchantabilityApi.getInstance().addToDexterityList(tag);
            } else {
                ModRef.LOGGER.warn("Registered tag for dexterity doesn't exist, " + tagName);
            }
        }
    }

    private static List<String> defaultTags() {
        Tag[] tags = new Tag[] {
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
                .map(tag -> tag.getId().toString())
                .collect(Collectors.toList());
    }

    private static List<String> defaultItems() {
        Item[] items = new Item[] {
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
        TileEntityType[] types = new TileEntityType[] {
                TileEntityType.HOPPER,
                TileEntityType.PISTON,
                TileEntityType.BEACON,
                TileEntityType.CONDUIT,
                TileEntityType.BELL,
                TileEntityType.field_226985_G_,
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
