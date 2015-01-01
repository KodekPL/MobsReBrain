package jcraft.rebrain;

import jcraft.rebrain.listener.EntityListener;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class ReBrainPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        NoBrainMobs.PIG.init();
        NoBrainMobs.SHEEP.init();
        NoBrainMobs.COW.init();
        NoBrainMobs.CHIKEN.init();
        NoBrainMobs.SQUID.init();
        NoBrainMobs.WOLF.init();
        NoBrainMobs.MUSHROOM_COW.init();
        NoBrainMobs.SNOWMAN.init();
        NoBrainMobs.OCELOT.init();
        NoBrainMobs.IRON_GOLEM.init();
        NoBrainMobs.HORSE.init();
        NoBrainMobs.VILLAGER.init();

        NoBrainMobs.CREEPER.init();
        NoBrainMobs.SKELETON.init();
        NoBrainMobs.SPIDER.init();
        NoBrainMobs.ZOMBIE.init();
        NoBrainMobs.SLIME.init();
        NoBrainMobs.GHAST.init();
        NoBrainMobs.PIG_ZOMBIE.init();
        NoBrainMobs.ENDERMAN.init();
        NoBrainMobs.CAVE_SPIDER.init();
        NoBrainMobs.SILVERFISH.init();
        NoBrainMobs.BLAZE.init();
        NoBrainMobs.MAGMA_CUBE.init();
        NoBrainMobs.WITCH.init();
        NoBrainMobs.ENDERMITE.init();
        NoBrainMobs.GUARDIAN.init();

        this.getServer().getPluginManager().registerEvents(new EntityListener(this), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && label.equalsIgnoreCase("spawnentity")) {
            if (args.length == 0) {
                final StringBuilder builder = new StringBuilder();

                builder.append("Entity types: ");

                for (NoBrainMobs mob : NoBrainMobs.values()) {
                    builder.append(mob.getType().name()).append(", ");
                }

                builder.delete(builder.length() - 2, builder.length());

                sender.sendMessage(builder.toString());
                return true;
            } else if (args.length == 1 || args.length == 2) {
                final Player player = (Player) sender;

                final String entityTypeName = args[0];
                NoBrainMobs mob = null;

                for (NoBrainMobs i : NoBrainMobs.values()) {
                    if (i.getType().name().equalsIgnoreCase(entityTypeName)) {
                        mob = i;
                        break;
                    }
                }

                if (mob == null) {
                    sender.sendMessage(ChatColor.RED + "Entity of type '" + entityTypeName + "' was not found.");
                    return false;
                }

                final Block target = player.getTargetBlock(null, 32);

                if (target == null) {
                    sender.sendMessage(ChatColor.RED + "Target is missing.");
                    return false;
                }

                int amount = 1;

                if (args.length == 2) {
                    try {
                        amount = Integer.parseInt(args[1]);
                    } catch (Exception e) {
                        amount = 1;
                    }
                }

                for (int i = 0; i < amount; i++) {
                    NoBrainMobs.spawnEntity(mob, target.getLocation().add(0.5D, 1D, 0.5D), CreatureSpawnEvent.SpawnReason.CUSTOM);
                }

                return true;
            }
        }

        return false;
    }

}
