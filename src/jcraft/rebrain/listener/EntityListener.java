package jcraft.rebrain.listener;

import jcraft.rebrain.NoBrainMobs;
import jcraft.rebrain.ReBrainPlugin;

import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class EntityListener implements Listener {

    private final ReBrainPlugin plugin;

    public EntityListener(ReBrainPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
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
            spawnEntity(event.getEntity());
            return;
        default:
            return;
        }
    }

    public void spawnEntity(final LivingEntity entity) {
        final NoBrainMobs noBrainMob = NoBrainMobs.getNoBrainMob(entity.getType());

        if (noBrainMob == null) {
            return;
        }

        final Entity newEntity = NoBrainMobs.convertToBukkit(NoBrainMobs.spawnEntity(noBrainMob, entity.getLocation()));

        if (entity instanceof Animals) {
            final Animals oldAnimal = (Animals) entity;
            final Animals newAnimal = (Animals) newEntity;

            if (oldAnimal.isAdult()) {
                newAnimal.setAdult();
            } else {
                newAnimal.setBaby();
            }

            newAnimal.setAge(oldAnimal.getAge());
            newAnimal.setBreed(oldAnimal.canBreed());
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
