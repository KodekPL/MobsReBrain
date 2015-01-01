package jcraft.rebrain.mob;

import net.minecraft.server.v1_8_R1.EntityGhast;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainGhast extends EntityGhast implements NoBrainEntity {

    public NoBrainGhast(World world) {
        super(world);
    }

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
