package jcraft.rebrain.pathfinder;

import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityLiving;

import com.google.common.base.Predicate;

public class CustomEntitySelectorNearestAttackableTarget implements Predicate<EntityLiving> {

    private final CustomPathfinderGoalNearestAttackableTarget pathfinder;

    public CustomEntitySelectorNearestAttackableTarget(CustomPathfinderGoalNearestAttackableTarget pathfinder) {
        this.pathfinder = pathfinder;
    }

    @Override
    public boolean apply(EntityLiving entity) {
        if (entity instanceof EntityHuman) {
            double followRange = this.pathfinder.getFollowRange();

            if (entity.isSneaking()) {
                followRange *= 0.800000011920929D;
            }

            if (entity.isInvisible()) {
                float armorAmount = ((EntityHuman) entity).bX();

                if (armorAmount < 0.1F) {
                    armorAmount = 0.1F;
                }

                followRange *= 0.7F * armorAmount;
            }

            if (entity.g(this.pathfinder.getEntity()) > followRange) {
                return false;
            }
        }

        return this.pathfinder.pathfinderCheck(entity, false);
    }

}
