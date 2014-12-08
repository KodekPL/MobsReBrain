package jcraft.rebrain.navigable;

import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.Pathfinder;
import net.minecraft.server.v1_8_R1.PathfinderNormal;
import net.minecraft.server.v1_8_R1.World;

public class CustomSimpleNavigation extends Navigation {

    public CustomSimpleNavigation(EntityInsentient entity, World world) {
        super(entity, world);
    }

    @Override
    protected Pathfinder a() {
        this.a = new PathfinderNormal();
        this.a.a(true);
        return new CustomSimplePathfinder(this.a);
    }

}
