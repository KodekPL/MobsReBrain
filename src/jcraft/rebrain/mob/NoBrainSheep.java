package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.navigable.CustomSimpleControllerMove;
import jcraft.rebrain.navigable.CustomSimpleNavigation;
import jcraft.rebrain.util.EntityCollisionHandler;
import jcraft.rebrain.util.ReflectionsUtils;
import net.minecraft.server.v1_8_R1.Block;
import net.minecraft.server.v1_8_R1.BlockLongGrass;
import net.minecraft.server.v1_8_R1.BlockPosition;
import net.minecraft.server.v1_8_R1.BlockStatePredicate;
import net.minecraft.server.v1_8_R1.Blocks;
import net.minecraft.server.v1_8_R1.EntityHuman;
import net.minecraft.server.v1_8_R1.EntitySheep;
import net.minecraft.server.v1_8_R1.EnumTallGrassType;
import net.minecraft.server.v1_8_R1.InventoryCraftResult;
import net.minecraft.server.v1_8_R1.InventoryCrafting;
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

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R1.event.CraftEventFactory;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class NoBrainSheep extends EntitySheep implements NoBrainEntity {

    private int eatDelayTicks;

    public NoBrainSheep(World world) {
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

        // Eat Tile Replace
        this.eatDelayTicks = (this.random.nextInt(1200) + 1200);

        ((Navigation) getNavigation()).a(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        // this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));

        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.1D, Items.WHEAT, false));

        // this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
        // this.goalSelector.a(5, this.bo);

        // this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));

        this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        // InventoryCrafting
        final InventoryCrafting bk = (InventoryCrafting) ReflectionsUtils.getPrivateField("bk", EntitySheep.class, this);

        bk.setItem(0, new ItemStack(Items.DYE, 1, 0));
        bk.setItem(1, new ItemStack(Items.DYE, 1, 0));
        bk.resultInventory = new InventoryCraftResult();
    }

    @Override
    protected void E() {
        super.E();
    }

    @Override
    public void m() {
        super.m();

        eatTile();
    }

    @Override
    public void inactiveTick() {
        super.inactiveTick();

        if ((this.world.isStatic) || (this.ageLocked)) {
            a(isBaby());
        } else {
            int i = getAge();

            if (i < 0) {
                i++;
                setAgeRaw(i);
            } else if (i > 0) {
                i--;
                setAgeRaw(i);
            }
        }

        eatTile();
    }

    private static final Predicate eatableBlocks = BlockStatePredicate.a(Blocks.TALLGRASS).a(BlockLongGrass.TYPE,
            Predicates.equalTo(EnumTallGrassType.GRASS));

    private void eatTile() {
        if (!this.world.isStatic && this.isSheared() && (--this.eatDelayTicks <= 0)) {
            final BlockPosition blockposition = new BlockPosition(this.locX, this.locY, this.locZ);

            if (eatableBlocks.apply(this.world.getType(blockposition))) {
                final boolean eventResult = CraftEventFactory.callEntityChangeBlockEvent(this,
                        this.world.getWorld().getBlockAt(blockposition.getX(), blockposition.getY(), blockposition.getZ()), Material.AIR,
                        !this.world.getGameRules().getBoolean("mobGriefing")).isCancelled();

                if (!eventResult) {
                    this.world.broadcastEntityEffect(this, (byte) 10);
                    this.world.setAir(blockposition, false);
                }

                this.v();
            } else {
                final BlockPosition blockposition1 = blockposition.down();

                if (this.world.getType(blockposition1).getBlock() == Blocks.GRASS) {
                    final boolean eventResult = CraftEventFactory.callEntityChangeBlockEvent(this,
                            this.world.getWorld().getBlockAt(blockposition1.getX(), blockposition1.getY(), blockposition1.getZ()), Material.DIRT,
                            !this.world.getGameRules().getBoolean("mobGriefing")).isCancelled();

                    if (!eventResult) {
                        this.world.broadcastEntityEffect(this, (byte) 10);
                        this.world.triggerEffect(2001, blockposition1, Block.getId(Blocks.GRASS));
                        this.world.setTypeAndData(blockposition1, Blocks.DIRT.getBlockData(), 2);
                    }

                    this.v();
                }
            }

            this.eatDelayTicks = (this.random.nextInt(1200) + 1200);
        }
    }

    int collisionCooldown = 5;

    @Override
    protected void bK() {
        if (collisionCooldown-- > 0) {
            return;
        }

        collisionCooldown = 5;

        EntityCollisionHandler.collide(this);
    }

}
