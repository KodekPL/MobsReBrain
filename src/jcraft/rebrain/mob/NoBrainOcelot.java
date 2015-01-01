package jcraft.rebrain.mob;

import jcraft.rebrain.util.EntityCollisionHandler;
import net.minecraft.server.v1_8_R1.EntityOcelot;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainOcelot extends EntityOcelot implements NoBrainEntity {

    public NoBrainOcelot(World world) {
        super(world);
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

}
