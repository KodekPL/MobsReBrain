package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.NMSUtils;
import net.minecraft.server.v1_8_R1.EntityPig;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalPassengerCarrotStick;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTempt;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainPig extends EntityPig {

    public NoBrainPig(World world) {
        super(world);

        final List goalB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        final List goalC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        final List targetB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        final List targetC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.9F, 0.9F);
        ((Navigation) getNavigation()).a(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        // this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));

        // PathfinderGoalPassengerCarrotStick
        final PathfinderGoalPassengerCarrotStick goalPassengerCarrotStick = new PathfinderGoalPassengerCarrotStick(this, 0.3F);

        this.goalSelector.a(2, goalPassengerCarrotStick);
        NMSUtils.setPrivateField("bk", EntityPig.class, this, goalPassengerCarrotStick);

        this.goalSelector.a(3, new PathfinderGoalBreed(this, 1.0D));

        // this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT_ON_A_STICK, false));

        this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT, false));

        // this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.1D));
        // this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        // this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));

        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
    }

}
