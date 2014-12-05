package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.EntityCaveSpider;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntitySpider;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalLeapAtTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainCaveSpider extends EntityCaveSpider implements NoBrainEntity {

    public NoBrainCaveSpider(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.7F, 0.5F);

        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, this.a);
        this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));

        // PathfinderGoalSpiderMeleeAttack
        final Object pathGoalSpiderMeleeAttack = ReflectionsUtils.createPrivateInstance(
                "net.minecraft.server.v1_8_R1.PathfinderGoalSpiderMeleeAttack", new Class<?>[] { EntitySpider.class, Class.class }, this,
                EntityHuman.class);

        this.goalSelector.a(4, (PathfinderGoalMeleeAttack) pathGoalSpiderMeleeAttack);

        // this.goalSelector.a(4, new PathfinderGoalSpiderMeleeAttack(this, EntityIronGolem.class));
        // this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
        // this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));

        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));

        // PathfinderGoalSpiderNearestAttackableTarget
        final Object pathGoalSpiderNearestAttackableTarget = ReflectionsUtils.createPrivateInstance(
                "net.minecraft.server.v1_8_R1.PathfinderGoalSpiderNearestAttackableTarget", new Class<?>[] { EntitySpider.class, Class.class }, this,
                EntityHuman.class);

        this.targetSelector.a(2, (PathfinderGoalNearestAttackableTarget) pathGoalSpiderNearestAttackableTarget);

        // this.targetSelector.a(3, new PathfinderGoalSpiderNearestAttackableTarget(this, EntityIronGolem.class));
    }

}
