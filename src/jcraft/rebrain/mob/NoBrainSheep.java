package jcraft.rebrain.mob;

import java.util.List;

import jcraft.rebrain.util.NMSUtils;
import net.minecraft.server.v1_8_R1.EntitySheep;
import net.minecraft.server.v1_8_R1.InventoryCraftResult;
import net.minecraft.server.v1_8_R1.InventoryCrafting;
import net.minecraft.server.v1_8_R1.ItemStack;
import net.minecraft.server.v1_8_R1.Items;
import net.minecraft.server.v1_8_R1.Navigation;
import net.minecraft.server.v1_8_R1.PathfinderGoalBreed;
import net.minecraft.server.v1_8_R1.PathfinderGoalEatTile;
import net.minecraft.server.v1_8_R1.PathfinderGoalFloat;
import net.minecraft.server.v1_8_R1.PathfinderGoalRandomLookaround;
import net.minecraft.server.v1_8_R1.PathfinderGoalSelector;
import net.minecraft.server.v1_8_R1.PathfinderGoalTempt;
import net.minecraft.server.v1_8_R1.World;

public class NoBrainSheep extends EntitySheep {

    public NoBrainSheep(World world) {
        super(world);

        final List goalB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, goalSelector);
        goalB.clear();
        final List goalC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, goalSelector);
        goalC.clear();
        final List targetB = (List) NMSUtils.getPrivateField("b", PathfinderGoalSelector.class, targetSelector);
        targetB.clear();
        final List targetC = (List) NMSUtils.getPrivateField("c", PathfinderGoalSelector.class, targetSelector);
        targetC.clear();

        a(0.9F, 1.3F);
        ((Navigation) getNavigation()).a(true);

        this.goalSelector.a(0, new PathfinderGoalFloat(this));

        // this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));

        this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
        this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.1D, Items.WHEAT, false));

        // this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));

        // PathfinderGoalEatTile
        final PathfinderGoalEatTile bo = (PathfinderGoalEatTile) NMSUtils.getPrivateField("bo", EntitySheep.class, this);
        this.goalSelector.a(5, bo);

        // this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
        // this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));

        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));

        // InventoryCrafting
        final InventoryCrafting bk = (InventoryCrafting) NMSUtils.getPrivateField("bk", EntitySheep.class, this);

        bk.setItem(0, new ItemStack(Items.DYE, 1, 0));
        bk.setItem(1, new ItemStack(Items.DYE, 1, 0));
        bk.resultInventory = new InventoryCraftResult();
    }

}
