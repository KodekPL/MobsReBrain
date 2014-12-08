package jcraft.rebrain.ai;

import net.minecraft.server.v1_8_R1.EntityCreature;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.Item;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalTempt;

public class CustomPathfinderGoalTempt extends PathfinderGoalTempt {

    public CustomPathfinderGoalTempt(EntityCreature entity, double movementSpeed, Item temptItem, boolean checkPlayerDirection) {
        super(entity, movementSpeed, temptItem, checkPlayerDirection);

        this.entity = entity;
        this.movementSpeed = movementSpeed;
        this.temptItem = temptItem;
        this.checkPlayerDirection = checkPlayerDirection;

        a(3);

        if (!(entity.getNavigation() instanceof Navigation)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }

    private int startCooldown;

    private EntityCreature entity;
    private double movementSpeed;
    private Item temptItem;

    private EntityHuman player;
    private double playerLocX;
    private double playerLocY;
    private double playerLocZ;
    private double playerPitch;
    private double playerYaw;
    private boolean checkPlayerDirection; // UNSURE

    private boolean isFollowing;
    private boolean isPathfinding; // UNSURE

    // shouldStart() method ?
    @Override
    public boolean a() {
        if (this.startCooldown > 0) {
            this.startCooldown -= 1;
            return false;
        }

        this.player = this.entity.world.findNearbyPlayer(this.entity, 10.0D);

        if (this.player == null) {
            return false;
        }

        ItemStack itemInHand = this.player.bY();

        if (itemInHand == null) {
            return false;
        }

        if (itemInHand.getItem() != this.temptItem) {
            return false;
        }

        return true;
    }

    // isReady() method ?
    @Override
    public boolean b() {
        if (this.checkPlayerDirection) {
            if (this.entity.h(this.player) < 36.0D) {
                if (this.player.e(this.playerLocX, this.playerLocY, this.playerLocZ) > 0.01D) {
                    return false;
                }

                if ((Math.abs(this.player.pitch - this.playerPitch) > 5.0D) || (Math.abs(this.player.yaw - this.playerYaw) > 5.0D)) {
                    return false;
                }

            } else {
                this.playerLocX = this.player.locX;
                this.playerLocY = this.player.locY;
                this.playerLocZ = this.player.locZ;
            }

            this.playerPitch = this.player.pitch;
            this.playerYaw = this.player.yaw;
        }

        return a();
    }

    // onStart() method ?
    @Override
    public void c() {
        this.playerLocX = this.player.locX;
        this.playerLocY = this.player.locY;
        this.playerLocZ = this.player.locZ;
        this.isFollowing = true;
        this.isPathfinding = ((Navigation) this.entity.getNavigation()).e();

        ((Navigation) this.entity.getNavigation()).a(false);
    }

    // onEnd() method ?
    @Override
    public void d() {
        this.player = null;
        this.entity.getNavigation().n();
        this.startCooldown = 100;
        this.isFollowing = false;

        ((Navigation) this.entity.getNavigation()).a(this.isPathfinding);
    }

    // initPathfind() method ?
    @Override
    public void e() {
        this.entity.getControllerLook().a(this.player, 30.0F, this.entity.bP());

        if (this.entity.h(this.player) < 6.25D) {
            this.entity.getNavigation().n();
        } else {
            this.entity.getNavigation().a(this.player, this.movementSpeed);
        }
    }

    // isFollowing() method ?
    @Override
    public boolean f() {
        return this.isFollowing;
    }

}
