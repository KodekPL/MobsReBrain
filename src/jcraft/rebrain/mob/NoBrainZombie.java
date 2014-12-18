package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.navigable.CustomSimpleControllerMove;
import jcraft.rebrain.navigable.CustomSimpleNavigation;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R1.EntityChicken;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityPigZombie;
import net.minecraft.server.v1_8_R1.EntityZombie;
import net.minecraft.server.v1_8_R1.GroupDataEntity;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.PathfinderGoalNearestAttackableTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainZombie extends EntityZombie implements NoBrainEntity {

    public NoBrainZombie(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.6F, 1.95F);

        // Apply simple pathfinder
        this.moveController = new CustomSimpleControllerMove(this);
        this.navigation = new CustomSimpleNavigation(this, this.world);

        ((Navigation) getNavigation()).b(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.25D, false)); // 1.0D -> 1.25D

        // this.goalSelector.a(2, this.a);

        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));

        // this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        // this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        n();
    }

    @Override
    protected void n() {
        // if (this.world.spigotConfig.zombieAggressiveTowardsVillager) {
        // this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true));
        // }

        // this.goalSelector.a(5, new PathfinderGoalMeleeAttack(this, EntityIronGolem.class, 1.0D, true));

        // this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));

        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));

        // if (this.world.spigotConfig.zombieAggressiveTowardsVillager) {
        // this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, false));
        // }

        // this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));
    }

    @Override
    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        final Object object = super.prepare(difficultydamagescaler, groupdataentity);

        if (this.isBaby() && this.vehicle != null && this.vehicle instanceof EntityChicken) {
            // TODO: Override prepare method to avoid spawning of chickens at all
            this.world.removeEntity(this.vehicle);
        }

        return (GroupDataEntity) object;
    }

}
