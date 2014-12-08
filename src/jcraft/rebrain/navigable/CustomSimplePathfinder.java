package jcraft.rebrain.navigable;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.IBlockAccess;
import net.minecraft.server.v1_8_R1.Path;
import net.minecraft.server.v1_8_R1.PathEntity;
import net.minecraft.server.v1_8_R1.PathPoint;
import net.minecraft.server.v1_8_R1.Pathfinder;
import net.minecraft.server.v1_8_R1.PathfinderAbstract;

public class CustomSimplePathfinder extends Pathfinder {

    private Path a = new Path();
    private PathfinderAbstract c;

    public CustomSimplePathfinder(PathfinderAbstract paramPathfinderAbstract) {
        super(paramPathfinderAbstract);

        this.c = paramPathfinderAbstract;
    }

    public PathEntity a(IBlockAccess paramIBlockAccess, Entity paramEntity1, Entity paramEntity2, float paramFloat) {
        return a(paramIBlockAccess, paramEntity1, paramEntity2.locX, paramEntity2.getBoundingBox().b, paramEntity2.locZ, paramFloat);
    }

    public PathEntity a(IBlockAccess paramIBlockAccess, Entity paramEntity, BlockPosition paramBlockPosition, float paramFloat) {
        return a(paramIBlockAccess, paramEntity, paramBlockPosition.getX() + 0.5F, paramBlockPosition.getY() + 0.5F,
                paramBlockPosition.getZ() + 0.5F, paramFloat);
    }

    private PathEntity a(IBlockAccess paramIBlockAccess, Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3,
            float paramFloat) {
        this.a.a();
        this.c.a(paramIBlockAccess, paramEntity);

        // PathPoint localPathPoint1 = this.c.a(paramEntity);
        PathPoint localPathPoint2 = this.c.a(paramEntity, paramDouble1, paramDouble2, paramDouble3);

        // PathEntity localPathEntity = a(paramEntity, localPathPoint1, localPathPoint2, paramFloat);

        // Only set path to the last path point
        PathEntity localPathEntity = new PathEntity(new PathPoint[] { localPathPoint2 });

        this.c.a();

        return localPathEntity;
    }

}
