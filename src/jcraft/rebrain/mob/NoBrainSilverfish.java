package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntitySilverfish;
import net.minecraft.server.v1_8_R1.PathfinderGoal;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainSilverfish extends EntitySilverfish implements NoBrainEntity {

    public NoBrainSilverfish(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.4F, 0.3F);

        this.goalSelector.a(1, new PathfinderGoalFloat(this));

        // PathfinderGoalSilverfishWakeOthers
        final Object pathGoalSilverfishWakeOthers = ReflectionsUtils.createPrivateInstance("net.minecraft.server.v1_8_R1.PathfinderGoalSilverfishWakeOthers",
                new Class<?>[] { EntitySilverfish.class }, this);

        this.goalSelector.a(3, (PathfinderGoal) pathGoalSilverfishWakeOthers);
        ReflectionsUtils.setPrivateField("b", EntitySilverfish.class, this, pathGoalSilverfishWakeOthers);

        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));

        // this.goalSelector.a(5, new PathfinderGoalSilverfishHideInBlock(this));

        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[0]));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
    }

}
