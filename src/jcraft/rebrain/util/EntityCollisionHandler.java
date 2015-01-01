package jcraft.rebrain.util;

import java.util.List;

import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityLiving;

public class EntityCollisionHandler {

    public static void collide(EntityLiving cEntity) {
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
                            cEntity.collide(entity);

                            collisionsLimiter--;
                        }
                    }
                }
            }
        }
    }

}
