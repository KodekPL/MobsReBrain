package jcraft.rebrain;

import jcraft.rebrain.listener.EntityListener;

import org.bukkit.plugin.java.JavaPlugin;

public class ReBrainPlugin extends JavaPlugin {

    public void onEnable() {
        NoBrainMobs.PIG.init();
        NoBrainMobs.SHEEP.init();
        NoBrainMobs.COW.init();
        NoBrainMobs.CHIKEN.init();
        NoBrainMobs.MUSHROOM_COW.init();
        NoBrainMobs.WOLF.init();
        NoBrainMobs.IRON_GOLEM.init();
        NoBrainMobs.HORSE.init();
        NoBrainMobs.VILLAGER.init();

        NoBrainMobs.CREEPER.init();
        NoBrainMobs.SKELETON.init();
        NoBrainMobs.SPIDER.init();
        NoBrainMobs.ZOMBIE.init();
        NoBrainMobs.PIG_ZOMBIE.init();
        NoBrainMobs.ENDERMAN.init();
        NoBrainMobs.CAVE_SPIDER.init();
        NoBrainMobs.SILVERFISH.init();
        NoBrainMobs.BLAZE.init();
        NoBrainMobs.WITCH.init();
        NoBrainMobs.ENDERMITE.init();
        NoBrainMobs.GUARDIAN.init();

        this.getServer().getPluginManager().registerEvents(new EntityListener(this), this);
    }

}
