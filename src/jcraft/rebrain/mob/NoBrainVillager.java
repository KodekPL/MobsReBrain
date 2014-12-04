package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.NMSUtils;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityInsentient;
import net.minecraft.server.v1_8_R1.EntityVillager;
import net.minecraft.server.v1_8_R1.InventorySubcontainer;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalInteract;
import net.minecraft.server.v1_8_R1.PathfinderGoalInteractVillagers;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtTradingPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalMakeLove;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveIndoors;
import net.minecraft.server.v1_8_R1.PathfinderGoalMoveTowardsRestriction;
import net.minecraft.server.v1_8_R1.PathfinderGoalOpenDoor;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomStroll;
import net.minecraft.server.v1_8_R1.PathfinderGoalRestrictOpenDoor;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTradeWithPlayer;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.craftbukkit.v1_8_R1.entity.CraftVillager;

public class NoBrainVillager extends EntityVillager {

    public NoBrainVillager(World world) {
        super(world, world.random.nextInt(5));
    }

    public NoBrainVillager(World world, int i) {
        super(world, i);

        final List goalB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        final List goalC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        final List targetB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        final List targetC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.6F, 1.8F);
        ((Navigation) getNavigation()).b(true);
        ((Navigation) getNavigation()).a(true);

        this.inventory = new InventorySubcontainer("Items", false, 8, (CraftVillager) getBukkitEntity());

        setProfession(i);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        // this.goalSelector.a(1, new PathfinderGoalAvoidTarget(this, new EntityVillagerInnerClass1(this), 8.0F, 0.6D, 0.6D));

        this.goalSelector.a(1, new PathfinderGoalTradeWithPlayer(this));
        this.goalSelector.a(1, new PathfinderGoalLookAtTradingPlayer(this));
        this.goalSelector.a(2, new PathfinderGoalMoveIndoors(this));
        this.goalSelector.a(3, new PathfinderGoalRestrictOpenDoor(this));
        this.goalSelector.a(4, new PathfinderGoalOpenDoor(this, true));
        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 0.6D));
        this.goalSelector.a(6, new PathfinderGoalMakeLove(this));

        // this.goalSelector.a(7, new PathfinderGoalTakeFlower(this));

        this.goalSelector.a(9, new PathfinderGoalInteract(this, EntityHuman.class, 3.0F, 1.0F));
        this.goalSelector.a(9, new PathfinderGoalInteractVillagers(this));
        this.goalSelector.a(9, new PathfinderGoalRandomStroll(this, 0.6D));
        this.goalSelector.a(10, new PathfinderGoalLookAtPlayer(this, EntityInsentient.class, 8.0F));

        j(true);
    }

}
