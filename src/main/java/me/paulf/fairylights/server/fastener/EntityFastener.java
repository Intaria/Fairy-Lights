package me.paulf.fairylights.server.fastener;

import me.paulf.fairylights.server.fastener.accessor.*;
import net.minecraft.entity.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;

public abstract class EntityFastener<E extends Entity> extends AbstractFastener<EntityFastenerAccessor<E>> {
    protected final E entity;

    public EntityFastener(final E entity) {
        this.entity = entity;
        this.bounds = new AxisAlignedBB(entity.getPosition());
        this.setWorld(entity.world);
    }

    @Override
    public Direction getFacing() {
        return Direction.UP;
    }

    public E getEntity() {
        return this.entity;
    }

    @Override
    public BlockPos getPos() {
        return new BlockPos(this.entity);
    }

    @Override
    public Vec3d getConnectionPoint() {
        return this.entity.getPositionVec();
    }
}
