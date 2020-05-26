package me.paulf.fairylights.server.item;

import me.paulf.fairylights.server.entity.*;
import me.paulf.fairylights.server.sound.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

public class LadderItem extends Item {
    public LadderItem(final Item.Properties properties) {
        super(properties);
    }

    @Override
    public ActionResultType onItemUse(final ItemUseContext context) {
        final World world = context.getWorld();
        final BlockPos pos = context.getPos();
        final Direction side = context.getFace();
        final BlockState state = world.getBlockState(pos);
        final PlayerEntity player = context.getPlayer();
        final boolean replaceable = state.isReplaceable(new BlockItemUseContext(context));
        if (!replaceable && side != Direction.UP) {
            return ActionResultType.FAIL;
        }
        final BlockPos location = replaceable ? pos : pos.offset(side);
        final ItemStack heldStack = context.getItem();
        final double x;
        final double z;
        if (replaceable && side != Direction.UP) {
            x = location.getX() + 0.5D;
            z = location.getZ() + 0.5D;
        } else {
            x = context.getHitVec().x;
            z = context.getHitVec().z;
        }
        final double y = location.getY() + 0.001;
        if (!world.hasNoCollisions(FLEntities.LADDER.orElseThrow(IllegalStateException::new).func_220328_a(x, y, z))) {
            return ActionResultType.FAIL;
        }
        if (!world.isRemote) {
            final LadderEntity ladder = new LadderEntity(world);
            ladder.rotationYawHead = ladder.renderYawOffset = ladder.rotationYaw = context.getPlacementYaw() + 180;
            ladder.setPosition(x, y, z);
            EntityType.applyItemNBT(world, player, ladder, heldStack.getTag());
            world.addEntity(ladder);
            world.playSound(null, ladder.getPosX(), ladder.getPosY(), ladder.getPosZ(), FLSounds.LADDER_PLACE.orElseThrow(IllegalStateException::new), ladder.getSoundCategory(), 0.75F, 0.8F);
        }
        heldStack.shrink(1);
        return ActionResultType.SUCCESS;
    }
}
