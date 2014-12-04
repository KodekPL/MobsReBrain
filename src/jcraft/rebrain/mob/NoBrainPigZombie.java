package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.NMSUtils;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityPigZombie;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalMeleeAttack;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainPigZombie extends EntityPigZombie {

    public NoBrainPigZombie(World world) {
        super(world);

        final List goalB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        final List goalC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        final List targetB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        final List targetC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.6F, 1.95F);
        ((Navigation) getNavigation()).b(true);

        this.fireProof = true;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
        this.goalSelector.a(2, this.a);
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));

        // this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
        // this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        n();
    }

}
