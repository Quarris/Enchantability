package quarris.enchantability.mod;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
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
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Logger;
import quarris.enchantability.api.EnchantabilityAPI;
import quarris.enchantability.mod.blocks.BlockAirIce;
import quarris.enchantability.mod.capability.player.CapabilityHandler;
import quarris.enchantability.mod.command.CommandModTree;
import quarris.enchantability.mod.config.ConfigEnchants;
import quarris.enchantability.mod.container.gui.GuiHandler;
import quarris.enchantability.mod.enchant.Enchants;
import quarris.enchantability.mod.enchant.impl.EnchantEffectMending;
import quarris.enchantability.mod.event.EnchantEffectEventHandler;
import quarris.enchantability.mod.event.ModEvents;
import quarris.enchantability.mod.network.PacketHandler;
import quarris.enchantability.mod.potion.PotionSpiderClimb;

@Mod(modid = Enchantability.MODID, name = Enchantability.NAME, version = Enchantability.VERSION)
public class Enchantability {
    public static final String MODID = "enchantability";
    public static final String NAME = "Enchantability";
    public static final String VERSION = "1.1.1";

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        logger = e.getModLog();
        EnchantabilityAPI.setInstance(new InternalHooks());
        MinecraftForge.EVENT_BUS.register(new ModEvents());
        MinecraftForge.EVENT_BUS.register(new EnchantEffectEventHandler());
        CapabilityHandler.register();
        Enchants.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        PacketHandler.init();
        ForgeRegistries.POTIONS.register(SPIDER_CLIMB);
		EnchantEffectMending.registerFoodActions();
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
                    EnchantabilityAPI.getInstance().addToEfficiencyList(stack);
                }
                else {
                	logger.warn("Item was not found: "+itemName);
				}
            }
            else {
                if (!OreDictionary.getOres(entry).isEmpty()) {
                    EnchantabilityAPI.getInstance().addToEfficiencyList(entry);
                }
				else {
					logger.warn("OreDict was not found: "+entry);
				}
            }
        }
    }

    public static final Block AIR_ICE = new BlockAirIce();
    public static final Potion SPIDER_CLIMB = new PotionSpiderClimb();
}

