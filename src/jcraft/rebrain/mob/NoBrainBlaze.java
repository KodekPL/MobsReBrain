package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.pathfinder.CustomPathfinderGoalNearestAttackableTarget;
import jcraft.rebrain.util.EntityCollisionHandler;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.EntityBlaze;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.PathfinderGoal;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainBlaze extends EntityBlaze implements NoBrainEntity {

    public NoBrainBlaze(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        this.fireProof = true;
        this.b_ = 10;

        // PathfinderGoalBlazeFireball
        final Object pathGoalBlazeFireball = ReflectionsUtils.createPrivateInstance("net.minecraft.server.v1_8_R1.PathfinderGoalBlazeFireball",
                new Class<?>[] { EntityBlaze.class }, this);

        this.goalSelector.a(4, (PathfinderGoal) pathGoalBlazeFireball);
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));

        // this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        // this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

    int collisionCooldown = 6;

    @Override
    protected void bK() {
        if (collisionCooldown-- > 0) {
            return;
        }

        collisionCooldown = 6;

        EntityCollisionHandler.collide(this, false);
    }

    boolean fromSpawner = false;

    @Override
    public void setFromSpawner() {
        fromSpawner = true;
    }

    @Override
    public boolean isFromSpawner() {
        return fromSpawner;
    }

}
