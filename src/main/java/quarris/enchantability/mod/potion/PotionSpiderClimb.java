package quarris.enchantability.mod.potion;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
		World world = entity.world;
		if (entity.collidedHorizontally) {
			entity.motionY = 0.15d + amplifier * 0.05d;
			entity.fallDistance = 0;
		}
		else if (false) { // TODO: Allow sneaking to stop midair like on ladders
			boolean blockNearby = false;
			for (EnumFacing facing : EnumFacing.HORIZONTALS) {
				BlockPos statePos = new BlockPos(entity.getPosition().getX()+facing.getFrontOffsetX(), entity.getPosition().getY(), entity.getPosition().getZ()+facing.getFrontOffsetZ());
				IBlockState state = world.getBlockState(statePos);
				if (state.getMaterial() != Material.AIR) {
					blockNearby = true;
				}
			}
			if (blockNearby) {
				entity.motionX = MathHelper.clamp(entity.motionX, -0.15d, 0.15d);
				entity.motionZ = MathHelper.clamp(entity.motionZ, -0.15d, 0.15d);
				entity.fallDistance = 0.0F;

				if (entity.motionY < -0.15D) {
					entity.motionY = -0.15D;
				}

				if (entity.isSneaking() && entity.motionY < 0.0D) {
					System.out.println("Stop");
					entity.motionY = 0.0D;
				}
				entity.fallDistance = 0;
			}
		}
	}

	@Override
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		return super.getStatusIconIndex();
	}
}
