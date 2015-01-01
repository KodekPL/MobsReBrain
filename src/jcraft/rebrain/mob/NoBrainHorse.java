package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.navigable.CustomSimpleControllerMove;
import jcraft.rebrain.navigable.CustomSimpleNavigation;
import jcraft.rebrain.util.EntityCollisionHandler;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.EntityHorse;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTame;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainHorse extends EntityHorse implements NoBrainEntity {

    public NoBrainHorse(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(1.4F, 1.6F);

        // Apply simple pathfinder
        this.moveController = new CustomSimpleControllerMove(this);
        this.navigation = new CustomSimpleNavigation(this, this.world);

        ((Navigation) getNavigation()).a(true);

        this.fireProof = false;
        setHasChest(false);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        // this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.2D));

        this.goalSelector.a(1, new PathfinderGoalTame(this, 1.2D));
        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));

        // this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.0D));
        // this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.7D));

        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        loadChest();
    }

    int collisionCooldown = 4;

    @Override
    protected void bK() {
        if (collisionCooldown-- > 0) {
            return;
        }

        collisionCooldown = 4;

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
