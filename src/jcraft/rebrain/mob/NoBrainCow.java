package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.NMSUtils;
import net.minecraft.server.v1_8_R1.EntityCow;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTempt;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainCow extends EntityCow implements NoBrainEntity {

    public NoBrainCow(World world) {
        super(world);

        List goalB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.9F, 1.3F);
        ((Navigation) getNavigation()).a(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        // this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));

        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.25D, Items.WHEAT, false));

        // this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
        // this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
        // this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));

        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    }

}
