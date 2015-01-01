package jcraft.rebrain.mob;

import net.minecraft.server.v1_8_R1.EntitySquid;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainSquid extends EntitySquid implements NoBrainEntity {

    public NoBrainSquid(World world) {
        super(world);
    }

    int collisionCooldown = 4;

    @Override
    protected void bK() {
        // Removed push collisions
    }

    @Override
    public void setFromSpawner() {

    }

    @Override
    public boolean isFromSpawner() {
        return false;
    }

}
