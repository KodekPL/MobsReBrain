package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.NMSUtils;
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

public class NoBrainGuardian extends EntityGuardian {

    public NoBrainGuardian(World world) {
        super(world);

        final List goalB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        final List goalC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        final List targetB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        final List targetC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.85F, 0.85F);

        this.b_ = 10;

        // PathfinderGoalGuardianAttack
        final Object pathGoalGuardianAttack = NMSUtils.createPrivateInstance("net.minecraft.server.v1_8_R1.PathfinderGoalGuardianAttack",
                new Class<?>[] { EntityGuardian.class }, this);

        this.goalSelector.a(4, (PathfinderGoal) pathGoalGuardianAttack);

        final PathfinderGoalMoveTowardsRestriction localPathfinderGoalMoveTowardsRestriction;

        this.goalSelector.a(5, localPathfinderGoalMoveTowardsRestriction = new PathfinderGoalMoveTowardsRestriction(this, 1.0D));

        final PathfinderGoalRandomStroll pathGoalRandomStroll = new PathfinderGoalRandomStroll(this, 1.0D, 80);

        NMSUtils.setPrivateField("bq", EntityGuardian.class, this, pathGoalRandomStroll);

        this.goalSelector.a(7, pathGoalRandomStroll);

        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        // this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityGuardian.class, 12.0F, 0.01F));

        this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));

        pathGoalRandomStroll.a(3);
        localPathfinderGoalMoveTowardsRestriction.a(3);

        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));

        // ControllerMoveGuardian
        final Object controllerMoveGuardian = NMSUtils.createPrivateInstance("net.minecraft.server.v1_8_R1.ControllerMoveGuardian",
                new Class<?>[] { EntityGuardian.class }, this);

        this.moveController = (ControllerMove) controllerMoveGuardian;

        final float randomValue = this.random.nextFloat();

        NMSUtils.setPrivateField("b", EntityGuardian.class, this, randomValue);
        NMSUtils.setPrivateField("c", EntityGuardian.class, this, randomValue);
    }

}
