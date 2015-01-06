package jcraft.rebrain.pathfinder;

import net.minecraft.server.v1_8_R1.EntityCreature;

public class CustomPathfinderGoalSpiderNearestAttackableTarget extends CustomPathfinderGoalNearestAttackableTarget {

    public CustomPathfinderGoalSpiderNearestAttackableTarget(EntityCreature entity, Class<?> targetClass, boolean useSenses) {
        super(entity, targetClass, useSenses);
    }

    @Override
    public boolean a() {
        float lightLevel = this.e.c(1.0F);

        if (lightLevel >= 0.5F) {
            return false;
        }

        return super.a();
    }

}