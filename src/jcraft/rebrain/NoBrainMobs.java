package jcraft.rebrain;

import java.util.Map;

import jcraft.rebrain.mob.NoBrainBlaze;
import jcraft.rebrain.mob.NoBrainCaveSpider;
import jcraft.rebrain.mob.NoBrainChicken;
import jcraft.rebrain.mob.NoBrainCow;
import jcraft.rebrain.mob.NoBrainCreeper;
import jcraft.rebrain.mob.NoBrainEnderman;
import jcraft.rebrain.mob.NoBrainEndermite;
import jcraft.rebrain.mob.NoBrainEntity;
import jcraft.rebrain.mob.NoBrainGuardian;
import jcraft.rebrain.mob.NoBrainIronGolem;
import jcraft.rebrain.mob.NoBrainMushroomCow;
import jcraft.rebrain.mob.NoBrainPig;
import jcraft.rebrain.mob.NoBrainPigZombie;
import jcraft.rebrain.mob.NoBrainSheep;
import jcraft.rebrain.mob.NoBrainSilverfish;
import jcraft.rebrain.mob.NoBrainSkeleton;
import jcraft.rebrain.mob.NoBrainSpider;
import jcraft.rebrain.mob.NoBrainVillager;
import jcraft.rebrain.mob.NoBrainWitch;
import jcraft.rebrain.mob.NoBrainWolf;
import jcraft.rebrain.mob.NoBrainZombie;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityBlaze;
import net.minecraft.server.v1_8_R1.EntityCaveSpider;
import net.minecraft.server.v1_8_R1.EntityChicken;
import net.minecraft.server.v1_8_R1.EntityCow;
import net.minecraft.server.v1_8_R1.EntityCreeper;
import net.minecraft.server.v1_8_R1.EntityEnderman;
import net.minecraft.server.v1_8_R1.EntityEndermite;
import net.minecraft.server.v1_8_R1.EntityGuardian;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityIronGolem;
import net.minecraft.server.v1_8_R1.EntityMushroomCow;
import net.minecraft.server.v1_8_R1.EntityPig;
import net.minecraft.server.v1_8_R1.EntityPigZombie;
import net.minecraft.server.v1_8_R1.EntitySheep;
import net.minecraft.server.v1_8_R1.EntitySilverfish;
import net.minecraft.server.v1_8_R1.EntitySkeleton;
import net.minecraft.server.v1_8_R1.EntitySpider;
import net.minecraft.server.v1_8_R1.EntityVillager;
import net.minecraft.server.v1_8_R1.EntityWitch;
import net.minecraft.server.v1_8_R1.EntityWolf;
import net.minecraft.server.v1_8_R1.EntityZombie;
import net.minecraft.server.v1_8_R1.GroupDataEntity;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;

public enum NoBrainMobs {

    PIG("Pig", 90, NoBrainPig.class, EntityPig.class, EntityType.PIG),
    SHEEP("Sheep", 91, NoBrainSheep.class, EntitySheep.class, EntityType.SHEEP),
    COW("Cow", 92, NoBrainCow.class, EntityCow.class, EntityType.COW),
    CHIKEN("Chicken", 93, NoBrainChicken.class, EntityChicken.class, EntityType.CHICKEN),
    WOLF("Wolf", 95, NoBrainWolf.class, EntityWolf.class, EntityType.WOLF),
    MUSHROOM_COW("MushroomCow", 96, NoBrainMushroomCow.class, EntityMushroomCow.class, EntityType.MUSHROOM_COW),
    IRON_GOLEM("VillagerGolem", 99, NoBrainIronGolem.class, EntityIronGolem.class, EntityType.IRON_GOLEM),
    VILLAGER("Villager", 120, NoBrainVillager.class, EntityVillager.class, EntityType.VILLAGER),

    CREEPER("Creeper", 50, NoBrainCreeper.class, EntityCreeper.class, EntityType.CREEPER),
    SKELETON("Skeleton", 51, NoBrainSkeleton.class, EntitySkeleton.class, EntityType.SKELETON),
    SPIDER("Spider", 52, NoBrainSpider.class, EntitySpider.class, EntityType.SPIDER),
    ZOMBIE("Zombie", 54, NoBrainZombie.class, EntityZombie.class, EntityType.ZOMBIE),
    PIG_ZOMBIE("PigZombie", 57, NoBrainPigZombie.class, EntityPigZombie.class, EntityType.PIG_ZOMBIE),
    ENDERMAN("Enderman", 58, NoBrainEnderman.class, EntityEnderman.class, EntityType.ENDERMAN),
    CAVE_SPIDER("CaveSpider", 59, NoBrainCaveSpider.class, EntityCaveSpider.class, EntityType.CAVE_SPIDER),
    SILVERFISH("Silverfish", 60, NoBrainSilverfish.class, EntitySilverfish.class, EntityType.SILVERFISH),
    BLAZE("Blaze", 61, NoBrainBlaze.class, EntityBlaze.class, EntityType.BLAZE),
    WITCH("Witch", 66, NoBrainWitch.class, EntityWitch.class, EntityType.WITCH),
    ENDERMITE("Endermite", 67, NoBrainEndermite.class, EntityEndermite.class, EntityType.ENDERMITE),
    GUARDIAN("Guardian", 68, NoBrainGuardian.class, EntityGuardian.class, EntityType.GUARDIAN);

    private final String name;
    private final int id;
    private final Class<? extends Entity> customClass;
    private final Class<? extends Entity> orgClass;
    private final EntityType type;

    NoBrainMobs(String name, int id, Class<? extends Entity> customClass, Class<? extends Entity> orgClass, EntityType type) {
        this.name = name;
        this.id = id;
        this.customClass = customClass;
        this.orgClass = orgClass;
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    public Class<? extends Entity> getCustomClass() {
        return this.customClass;
    }

    public EntityType getType() {
        return this.type;
    }

    public Object getInstance(World world) {
        try {
            if (this.getType() == EntityType.VILLAGER) {
                return this.customClass.getConstructors()[0].newInstance(world, world.random.nextInt(5));
            } else {
                return this.customClass.getConstructors()[0].newInstance(world);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void init() {
        addToMaps(this.customClass, this.orgClass, this.name, this.id);
    }

    private void addToMaps(Class customClass, Class orgClass, String name, int id) {
        // Add custom entity classes
        ((Map) ReflectionsUtils.getPrivateField("c", net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(name, customClass);
        ((Map) ReflectionsUtils.getPrivateField("d", net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(customClass, name);
        ((Map) ReflectionsUtils.getPrivateField("e", net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(Integer.valueOf(id), customClass);
        ((Map) ReflectionsUtils.getPrivateField("f", net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(customClass, Integer.valueOf(id));
        ((Map) ReflectionsUtils.getPrivateField("g", net.minecraft.server.v1_8_R1.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    }

    public static NoBrainMobs getNoBrainMob(EntityType type) {
        for (NoBrainMobs mob : NoBrainMobs.values()) {
            if (mob.getType() == type) {
                return mob;
            }
        }

        return null;
    }

    public static boolean isNoBrainMob(org.bukkit.entity.Entity entity) {
        final Entity nmsEntity = ((CraftEntity) entity).getHandle();

        return nmsEntity instanceof NoBrainEntity;
    }

    public static Entity spawnEntity(NoBrainMobs noBrainMob, Location loc) {
        final World nmsWorld = ((CraftWorld) loc.getWorld()).getHandle();
        final Entity entity = (Entity) noBrainMob.getInstance(nmsWorld);

        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());

        ((EntityInsentient) entity).prepare(entity.world.E(new BlockPosition(entity)), (GroupDataEntity) null);

        nmsWorld.addEntity(entity);

        return entity;
    }

    public static org.bukkit.entity.Entity convertToBukkit(Entity entity) {
        return ((org.bukkit.entity.Entity) entity.getBukkitEntity());
    }

}