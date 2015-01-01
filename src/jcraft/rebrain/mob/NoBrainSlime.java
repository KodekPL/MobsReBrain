package jcraft.rebrain.mob;

import jcraft.rebrain.util.EntityCollisionHandler;
import net.minecraft.server.v1_8_R1.EntitySlime;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainSlime extends EntitySlime implements NoBrainEntity {

    public NoBrainSlime(World world) {
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

    @Override
    public void setFromSpawner() {

    }

    @Override
    public boolean isFromSpawner() {
        return false;
    }

}
