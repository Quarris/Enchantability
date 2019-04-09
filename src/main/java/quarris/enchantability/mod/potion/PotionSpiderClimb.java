package quarris.enchantability.mod.potion;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import quarris.enchantability.mod.Enchantability;

public class PotionSpiderClimb extends Potion {

	public static ResourceLocation texture = new ResourceLocation(Enchantability.MODID, "textures/misc/potions.png");

	public PotionSpiderClimb() {
		super(false, 0xc1c1c1);
		setBeneficial();
		setPotionName("Wall Climb");
		setRegistryName(new ResourceLocation(Enchantability.MODID, "spiderClimb"));
		setIconIndex(0, 0);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {

		if (entity.collidedHorizontally) {
			entity.motionY = 0.2 + amplifier*0.05;
		}
		else if (entity.isSneaking()) {
			World world = entity.world;
			for (EnumFacing facing : EnumFacing.HORIZONTALS) {
				IBlockState state = world.getBlockState(entity.getPosition().offset(facing));
				if (state.getMaterial() != Material.AIR) {
					entity.motionY = 0;
				}
			}
		}
	}

	@Override
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		return super.getStatusIconIndex();
	}
}
