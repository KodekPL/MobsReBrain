package jcraft.rebrain.pathfinder;

import java.util.Collections;
import java.util.List;

import net.minecraft.server.v1_8_R1.DistanceComparator;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.IEntitySelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTarget;

import org.bukkit.event.entity.EntityTargetEvent;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class CustomPathfinderGoalNearestAttackableTarget extends PathfinderGoalTarget {

    protected final Class<?> targetClass;
    protected final DistanceComparator distanceComparator;
    protected EntityLiving target;
    protected Predicate<EntityLiving> predicate;

    public CustomPathfinderGoalNearestAttackableTarget(EntityCreature entity, Class<?> targetClass, boolean useSenses) {
        super(entity, useSenses);

        this.targetClass = targetClass;
        this.distanceComparator = new DistanceComparator(entity);
        this.predicate = new CustomEntitySelectorNearestAttackableTarget(this);
    }

    public boolean a() {
        final double followRange = getFollowRange();

        List list = this.e.world.a(this.targetClass, this.e.getBoundingBox().grow(followRange, 4.0D, followRange),
                Predicates.and(this.predicate, IEntitySelector.d));

        if (list.isEmpty()) {
            return false;
        }

        Collections.sort(list, this.distanceComparator);

        this.target = (EntityLiving) list.get(0);

        return true;
    }

    public double getFollowRange() {
        return f();
    }

    public EntityCreature getEntity() {
        return this.e;
    }

    protected boolean pathfinderCheck(EntityLiving paramEntityLiving, boolean paramBoolean) {
        return a(paramEntityLiving, paramBoolean);
    }

    public void c() {
        this.e.setGoalTarget(this.target, (this.target instanceof EntityPlayer) ? EntityTargetEvent.TargetReason.CLOSEST_PLAYER
                : EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);

        super.c();
    }

}