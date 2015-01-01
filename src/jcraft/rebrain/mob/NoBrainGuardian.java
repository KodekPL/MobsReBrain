package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.EntityCollisionHandler;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.ControllerMove;
import net.minecraft.server.v1_8_R1.EntityGuardian;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.PathfinderGoal;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainGuardian extends EntityGuardian implements NoBrainEntity {

    public NoBrainGuardian(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.85F, 0.85F);

        this.b_ = 10;

        // PathfinderGoalGuardianAttack
        final Object pathGoalGuardianAttack = ReflectionsUtils.createPrivateInstance("net.minecraft.server.v1_8_R1.PathfinderGoalGuardianAttack",
                new Class<?>[] { EntityGuardian.class }, this);

        this.goalSelector.a(4, (PathfinderGoal) pathGoalGuardianAttack);

        PathfinderGoalMoveTowardsRestriction localPathfinderGoalMoveTowardsRestriction;

        this.goalSelector.a(5, localPathfinderGoalMoveTowardsRestriction = new PathfinderGoalMoveTowardsRestriction(this, 1.0D));

        final PathfinderGoalRandomStroll pathGoalRandomStroll = new PathfinderGoalRandomStroll(this, 1.0D, 80);

        ReflectionsUtils.setPrivateField("bq", EntityGuardian.class, this, pathGoalRandomStroll);

        this.goalSelector.a(7, pathGoalRandomStroll);

        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        // this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityGuardian.class, 12.0F, 0.01F));

        this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));

        pathGoalRandomStroll.a(3);
        localPathfinderGoalMoveTowardsRestriction.a(3);

        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));

        // ControllerMoveGuardian
        final Object controllerMoveGuardian = ReflectionsUtils.createPrivateInstance("net.minecraft.server.v1_8_R1.ControllerMoveGuardian",
                new Class<?>[] { EntityGuardian.class }, this);

        this.moveController = (ControllerMove) controllerMoveGuardian;

        final float randomValue = this.random.nextFloat();

        ReflectionsUtils.setPrivateField("b", EntityGuardian.class, this, randomValue);
        ReflectionsUtils.setPrivateField("c", EntityGuardian.class, this, randomValue);
    }

    int collisionCooldown = 6;

    @Override
    protected void bK() {
        if (collisionCooldown-- > 0) {
            return;
        }

        collisionCooldown = 6;

        EntityCollisionHandler.collide(this);
    }

    @Override
    public void setFromSpawner() {

    }

    @Override
    public boolean isFromSpawner() {
        return false;
    }

}
