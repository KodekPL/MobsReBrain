package jcraft.rebrain.listener;

import jcraft.rebrain.NoBrainMobs;
import jcraft.rebrain.ReBrainPlugin;

import org.bukkit.entity.Ageable;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.inventory.ItemStack;

public class EntityListener implements Listener {

    private final ReBrainPlugin plugin;

    public EntityListener(ReBrainPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntitySpawn(CreatureSpawnEvent event) {
        switch (event.getSpawnReason()) {
        case BREEDING:
        case NATURAL:
        case JOCKEY:
        case MOUNT:
        case EGG:
        case BUILD_IRONGOLEM:
        case CURED:
        case INFECTION:
        case LIGHTNING:
        case NETHER_PORTAL:
        case REINFORCEMENTS:
        case SILVERFISH_BLOCK:
        case VILLAGE_DEFENSE:
        case VILLAGE_INVASION:
            spawnEntity(event.getEntity(), event.getSpawnReason());
            return;
        default:
            return;
        }
    }

    public void spawnEntity(final LivingEntity entity, final SpawnReason reason) {
        final NoBrainMobs noBrainMob = NoBrainMobs.getNoBrainMob(entity.getType());

        if (noBrainMob == null) {
            return;
        }

        final Entity newEntity = NoBrainMobs.convertToBukkit(NoBrainMobs.spawnEntity(noBrainMob, entity.getLocation()));

        if (entity instanceof Ageable) {
            final Ageable oldAgeable = (Ageable) entity;
            final Ageable newAgeable = (Ageable) newEntity;

            if (oldAgeable.isAdult()) {
                newAgeable.setAdult();
            } else {
                newAgeable.setBaby();
            }

            newAgeable.setAge(oldAgeable.getAge());
            newAgeable.setBreed(oldAgeable.canBreed());
        }

        if (entity instanceof Tameable) {
            final Tameable oldTameable = (Tameable) entity;
            final Tameable newTameable = (Tameable) newEntity;

            if (oldTameable.isTamed()) {
                newTameable.setTamed(true);
                newTameable.setOwner(oldTameable.getOwner());
            }
        }

        if (entity instanceof Sheep) {
            final Sheep oldSheep = (Sheep) entity;
            final Sheep newSheep = (Sheep) newEntity;

            newSheep.setColor(oldSheep.getColor());
        }

        if (entity instanceof Chicken) {
            final Chicken chicken = (Chicken) newEntity;

            if (reason == SpawnReason.EGG) {
                chicken.setBaby();
                chicken.setAge(-24000);
            }
        }

        if (entity instanceof Horse) {
            final Horse oldHorse = (Horse) entity;
            final Horse newHorse = (Horse) newEntity;

            newHorse.setColor(oldHorse.getColor());
            newHorse.setStyle(oldHorse.getStyle());
            newHorse.setVariant(oldHorse.getVariant());

            newHorse.setJumpStrength(oldHorse.getJumpStrength());
            newHorse.setCarryingChest(oldHorse.isCarryingChest());

            newHorse.getInventory().clear();

            final ItemStack[] content = oldHorse.getInventory().getContents();

            for (int i = 0; i < content.length; i++) {
                ItemStack item = content[i];

                if (item != null) {
                    newHorse.getInventory().setItem(i, item);
                }
            }

            newHorse.setMaxDomestication(oldHorse.getMaxDomestication());
            newHorse.setDomestication(oldHorse.getDomestication());
        }

        if (entity instanceof Zombie) {
            final Zombie oldZombie = (Zombie) entity;
            final Zombie newZombie = (Zombie) newEntity;

            newZombie.setBaby(oldZombie.isBaby());
            newZombie.setVillager(oldZombie.isVillager());
        }

        if (entity instanceof Villager) {
            final Villager oldVillager = (Villager) entity;
            final Villager newVillager = (Villager) newEntity;

            newVillager.setProfession(oldVillager.getProfession());
        }

        if (newEntity instanceof LivingEntity && entity.getEquipment() != null) {
            final LivingEntity lEntity = (LivingEntity) newEntity;

            lEntity.getEquipment().setItemInHand(entity.getEquipment().getItemInHand());
            lEntity.getEquipment().setHelmet(entity.getEquipment().getHelmet());
            lEntity.getEquipment().setChestplate(entity.getEquipment().getChestplate());
            lEntity.getEquipment().setLeggings(entity.getEquipment().getLeggings());
            lEntity.getEquipment().setBoots(entity.getEquipment().getBoots());

            lEntity.getEquipment().setItemInHandDropChance(entity.getEquipment().getItemInHandDropChance());
            lEntity.getEquipment().setHelmetDropChance(entity.getEquipment().getHelmetDropChance());
            lEntity.getEquipment().setChestplateDropChance(entity.getEquipment().getChestplateDropChance());
            lEntity.getEquipment().setLeggingsDropChance(entity.getEquipment().getLeggingsDropChance());
            lEntity.getEquipment().setBootsDropChance(entity.getEquipment().getBootsDropChance());
        }

        if (entity instanceof IronGolem) {
            final IronGolem oldIronGolem = (IronGolem) entity;
            final IronGolem newIronGolem = (IronGolem) newEntity;

            if (oldIronGolem.isPlayerCreated()) {
                newIronGolem.setPlayerCreated(true);

                // Wait a tick for blocks to be removed
                this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable() {
                    public void run() {
                        entity.remove();
                    }
                });

                return;
            }
        }

        entity.remove();
    }

}
