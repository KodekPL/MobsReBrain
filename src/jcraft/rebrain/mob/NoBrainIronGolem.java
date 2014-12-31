package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.EntityCollisionHandler;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityIronGolem;
import net.minecraft.server.v1_8_R1.IMonster;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalDefendVillage;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveThroughVillage;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

import com.google.common.base.Predicate;

public class NoBrainIronGolem extends EntityIronGolem implements NoBrainEntity {

    public NoBrainIronGolem(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(1.4F, 2.9F);
        ((Navigation) getNavigation()).a(true);

        this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, true));
        this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
        this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
        this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));

        // this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
        // this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));

        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false, new Class[0]));

        // PathfinderGoalNearestGolemTarget
        final Object pathGoalNearGolemTarget = ReflectionsUtils.createPrivateInstance(
                "net.minecraft.server.v1_8_R1.PathfinderGoalNearestGolemTarget", new Class<?>[] { EntityCreature.class, Class.class, int.class,
                        boolean.class, boolean.class, Predicate.class }, this, EntityInsentient.class, 10, false, true, IMonster.e);

        this.targetSelector.a(3, (PathfinderGoalNearestAttackableTarget) pathGoalNearGolemTarget);
    }

    int collisionCooldown = 5;

    @Override
    protected void bK() {
        if (collisionCooldown-- > 0) {
            return;
        }

        collisionCooldown = 5;

        EntityCollisionHandler.collide(this);
    }

}
