package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.pathfinder.CustomPathfinderGoalNearestAttackableTarget;
import jcraft.rebrain.util.EntityCollisionHandler;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.BiomeBase;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.DifficultyDamageScaler;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntitySkeleton;
import net.minecraft.server.v1_8_R1.GroupDataEntity;
import net.minecraft.server.v1_8_R1.MathHelper;
import net.minecraft.server.v1_8_R1.PathfinderGoalFleeSun;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalHurtByTarget;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalRestrictSun;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.World;
import net.minecraft.server.v1_8_R1.WorldProviderHell;

public class NoBrainSkeleton extends EntitySkeleton implements NoBrainEntity {

    public NoBrainSkeleton(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));

        // this.goalSelector.a(2, this.a);

        this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));

        // this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, new EntitySelectorSkeletonWolf(this), 6.0F, 1.0D, 1.2D));
        // this.goalSelector.a(4, new PathfinderGoalRandomStroll(this, 1.0D));
        // this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));

        this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false, new Class[0]));
        this.targetSelector.a(2, new CustomPathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));

        // this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityIronGolem.class, true));

        if ((world != null) && (!world.isStatic)) {
            n();
        }
    }

    public GroupDataEntity prepare(DifficultyDamageScaler difficultydamagescaler, GroupDataEntity groupdataentity) {
        super.prepare(difficultydamagescaler, groupdataentity);

        // Remove incorrect skeletons appearing in the nether
        if (this.getSkeletonType() == 1 && this.world.worldProvider instanceof WorldProviderHell) {
            final BiomeBase biomebase = this.world.getBiome(new BlockPosition(MathHelper.floor(this.locX), 0, MathHelper.floor(this.locZ)));

            if (biomebase != BiomeBase.HELL) {
                this.world.removeEntity(this);
            }
        }

        return groupdataentity;
    }

    int collisionCooldown = 6;

    @Override
    protected void bK() {
        if (collisionCooldown-- > 0) {
            return;
        }

        collisionCooldown = 6;

        EntityCollisionHandler.collide(this, false);
    }

    boolean fromSpawner = false;

    @Override
    public void setFromSpawner() {
        fromSpawner = true;
    }

    @Override
    public boolean isFromSpawner() {
        return fromSpawner;
    }

}
