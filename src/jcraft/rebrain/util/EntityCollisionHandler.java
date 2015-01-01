package jcraft.rebrain.util;

import java.util.List;

import jcraft.rebrain.mob.NoBrainEntity;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityLiving;
import net.minecraft.server.v1_8_R1.MathHelper;

public class EntityCollisionHandler {

    public static void collide(EntityLiving cEntity, boolean unstuck) {
        if (cEntity instanceof NoBrainEntity && ((NoBrainEntity) cEntity).isFromSpawner()) {
            return;
        }

        int collisionsLimiter = 4;

        if (cEntity.ae()) {
            final List list = cEntity.world.getEntities(cEntity, cEntity.getBoundingBox().grow(0.2000000029802322D, 0.0D, 0.2000000029802322D));

            if (list != null && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    if (collisionsLimiter < 0) {
                        return;
                    }

                    Entity entity = (Entity) list.get(i);

                    if (!(entity instanceof EntityLiving) || cEntity.ticksLived % 2 != 0) {
                        if (entity.ae()) {
                            if (unstuck) {
                                collideUnstuck(cEntity, entity);
                            } else {
                                cEntity.collide(entity);
                            }

                            collisionsLimiter--;
                        }
                    }
                }
            }
        }
    }

    public static void collideUnstuck(Entity entity, Entity collider) {
        if (collider.passenger != entity && collider.vehicle != entity && !collider.T && !entity.T) {
            double xDiff = collider.locX - entity.locX;
            double zDiff = collider.locZ - entity.locZ;
            double diff = MathHelper.a(xDiff, zDiff);

            if (diff >= 0.009999999776482582D) {
                diff = MathHelper.sqrt(diff);

                xDiff /= diff;
                zDiff /= diff;

                double mDiff = 1.0D / diff;

                if (mDiff > 1.0D) {
                    mDiff = 1.0D;
                }

                xDiff *= mDiff;
                zDiff *= mDiff;

                xDiff *= 0.0500000007450581D;
                zDiff *= 0.0500000007450581D;

                xDiff *= (1.0F - entity.U);
                zDiff *= (1.0F - entity.U);

                if (entity.passenger == null) {
                    entity.g(-xDiff, 0.0D, -zDiff);
                }

                if (collider.passenger == null) {
                    collider.g(xDiff, 0.0D, zDiff);
                }
            } else {
                if (entity.passenger == null) {
                    entity.g(-0.5D, 0.0D, -0.5D);
                }

                if (collider.passenger == null) {
                    collider.g(0.5D, 0.0D, 0.5D);
                }
            }
        }
    }

}
