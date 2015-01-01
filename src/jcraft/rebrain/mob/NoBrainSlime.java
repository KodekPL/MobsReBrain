package jcraft.rebrain.mob;

import jcraft.rebrain.util.EntityCollisionHandler;
import net.minecraft.server.v1_8_R1.EntitySlime;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainSlime extends EntitySlime implements NoBrainEntity {

    public NoBrainSlime(World world) {
        super(world);
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

    @Override
    public void setFromSpawner() {

    }

    @Override
    public boolean isFromSpawner() {
        return false;
    }

}
