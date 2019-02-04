package quarris.enchantability.mod;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import quarris.enchantability.api.EnchantabilityAPI;
import quarris.enchantability.mod.blocks.BlockAirIce;
import quarris.enchantability.mod.blocks.TileAirIce;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.command.CommandModTree;
import quarris.enchantability.mod.config.ConfigEnchants;
import quarris.enchantability.mod.container.gui.GuiHandler;
import quarris.enchantability.mod.enchant.Enchants;
import quarris.enchantability.mod.event.EnchantEffectEventHandler;
import quarris.enchantability.mod.event.ModEvents;
import quarris.enchantability.mod.network.PacketHandler;

@Mod(modid = Enchantability.MODID, name = Enchantability.NAME, version = Enchantability.VERSION)
public class Enchantability {
    public static final String MODID = "enchantability";
    public static final String NAME = "Enchantability";
    public static final String VERSION = "0.0.1";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        MinecraftForge.EVENT_BUS.register(new EnchantEffectEventHandler());
        CapabilityHandler.register();
        Enchants.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        PacketHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        initEfficiencyList();
    }

    @EventHandler
    public void registerCommands(FMLServerStartingEvent e) {
        e.registerServerCommand(new CommandModTree());
    }

    private static void initEfficiencyList() {
        for (String entry : ConfigEnchants.modifyEnchants.itemsForEfficiency) {
            if (entry.contains(":")) {
                String[] split = entry.split("#");
                String itemName = split[0];
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName));
                if (item != null) {
                    int meta = split.length == 2 ? Integer.parseInt(split[1]) : OreDictionary.WILDCARD_VALUE;
                    ItemStack stack = new ItemStack(item, 1, meta);
                    EnchantabilityAPI.addToEfficiencyList(stack);
                }
                // TODO: Add a warning for stupid people (item is null)
            }
            else {
                if (!OreDictionary.getOres(entry).isEmpty()) {
                    EnchantabilityAPI.addToEfficiencyList(entry);
                }
                // TODO: Add a warning for stupid people part 2
            }
        }
    }

    public static final Block AIR_ICE = new BlockAirIce();
}

