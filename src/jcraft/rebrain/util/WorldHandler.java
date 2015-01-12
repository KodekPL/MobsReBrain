package jcraft.rebrain.util;

import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.DifficultyDamageScaler;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;

public class WorldHandler {

    public DifficultyDamageScaler getRegionDifficultyScaler(World world, int x, int y, int z) {
        final net.minecraft.server.v1_8_R1.WorldServer nmsWorld = ((CraftWorld) world).getHandle();
        final DifficultyDamageScaler regionDifficulty = nmsWorld.E(new BlockPosition(x, y, z));

        return regionDifficulty;
    }

    public Float getRegionDifficulty(World world, int x, int y, int z) {
        final DifficultyDamageScaler regionDifficulty = getRegionDifficultyScaler(world, x, y, z);

        return (Float) ReflectionsUtils.getPrivateField("b", DifficultyDamageScaler.class, regionDifficulty);
    }

    public float getRegionDifficultyScale(World world, int x, int y, int z) {
        final DifficultyDamageScaler regionDifficulty = getRegionDifficultyScaler(world, x, y, z);

        return regionDifficulty.c();
    }

}
