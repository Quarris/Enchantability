package quarris.enchantability.mod.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import quarris.enchantability.mod.common.util.ModUtil;

public class CommonProxy {

    public void setupClient(FMLClientSetupEvent event) {

    }

    public void populateEventPlayerGetters() {
        ModUtil.addEventPlayerGetter(TickEvent.PlayerTickEvent.class, (event -> ((TickEvent.PlayerTickEvent)event).player));
        ModUtil.addEventPlayerGetter(PlayerEvent.class, (event -> ((PlayerEvent)event).getPlayer()));
        ModUtil.addEventPlayerGetter(ProjectileImpactEvent.class, (event -> {
            ProjectileImpactEvent evt = (ProjectileImpactEvent) event;
            if (evt.getRayTraceResult() instanceof EntityRayTraceResult && ((EntityRayTraceResult) evt.getRayTraceResult()).getEntity() instanceof PlayerEntity) {
                return (PlayerEntity) ((EntityRayTraceResult) evt.getRayTraceResult()).getEntity();
            }
            return null;
        }));
    }
}
