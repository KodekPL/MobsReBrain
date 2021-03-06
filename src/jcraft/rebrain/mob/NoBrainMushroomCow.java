package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.navigable.CustomSimpleControllerMove;
import jcraft.rebrain.navigable.CustomSimpleNavigation;
import jcraft.rebrain.util.EntityCollisionHandler;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.Blocks;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntityItem;
import net.minecraft.server.v1_8_R1.EntityMushroomCow;
import net.minecraft.server.v1_8_R1.EnumParticle;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalLookAtPlayer;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTempt;
import net.minecraft.server.v1_8_R1.World;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class NoBrainMushroomCow extends EntityMushroomCow implements NoBrainEntity {

    public NoBrainMushroomCow(World world) {
        super(world);

        List goalB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        List goalC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        List targetB = (List) ReflectionsUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        List targetC = (List) ReflectionsUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.9F, 1.3F);

        // Apply simple pathfinder
        this.moveController = new CustomSimpleControllerMove(this);
        this.navigation = new CustomSimpleNavigation(this, this.world);

        ((Navigation) getNavigation()).a(true);

        this.bl = Blocks.MYCELIUM;

        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        // this.goalSelector.a(1, new PathfinderGoalPanic(this, 2.0D));

        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.25D, Items.WHEAT, false));

        // this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.25D));
        // this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));

        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
    }

    public boolean a(EntityHuman entityhuman) {
        ItemStack itemstack = entityhuman.inventory.getItemInHand();

        if ((itemstack != null) && (itemstack.getItem() == Items.BOWL) && (getAge() >= 0)) {
            if (itemstack.count == 1) {
                entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, new ItemStack(Items.MUSHROOM_STEW));
                return true;
            }

            if ((entityhuman.inventory.pickup(new ItemStack(Items.MUSHROOM_STEW))) && (!entityhuman.abilities.canInstantlyBuild)) {
                entityhuman.inventory.splitStack(entityhuman.inventory.itemInHandIndex, 1);
                return true;
            }
        }

        if ((itemstack != null) && (itemstack.getItem() == Items.SHEARS) && (getAge() >= 0)) {
            PlayerShearEntityEvent event = new PlayerShearEntityEvent((Player) entityhuman.getBukkitEntity(), getBukkitEntity());

            this.world.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return false;
            }

            die();

            this.world.addParticle(EnumParticle.EXPLOSION_LARGE, this.locX, this.locY + this.length / 2.0F, this.locZ, 0.0D, 0.0D, 0.0D, new int[0]);

            if (!this.world.isStatic) {
                NoBrainCow entitycow = new NoBrainCow(this.world);

                entitycow.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
                entitycow.setHealth(getHealth());
                entitycow.aG = this.aG;

                if (hasCustomName()) {
                    entitycow.setCustomName(getCustomName());
                }

                this.world.addEntity(entitycow);

                for (int i = 0; i < 5; i++) {
                    this.world
                            .addEntity(new EntityItem(this.world, this.locX, this.locY + this.length, this.locZ, new ItemStack(Blocks.RED_MUSHROOM)));
                }

                itemstack.damage(1, entityhuman);
                makeSound("mob.sheep.shear", 1.0F, 1.0F);
            }

            return true;
        }

        return super.a(entityhuman);
    }

    int collisionCooldown = 4;

    @Override
    protected void bK() {
        if (collisionCooldown-- > 0) {
            return;
        }

        collisionCooldown = 4;

        EntityCollisionHandler.collide(this, true);
    }

    @Override
    public void setFromSpawner() {

    }

    @Override
    public boolean isFromSpawner() {
        return false;
    }

}
