package dev.scraft.SimpleSoulbound;

import net.minecraft.resources.ResourceLocation;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftNamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

import static org.bukkit.Bukkit.getLogger;

public class PlayerListener implements Listener {

    private final Map<UUID, List<ItemStack>> soulboundStore = new HashMap<>();

    @EventHandler
    public void onPlayerTakeDamage(@NotNull EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return; // Not our mess to deal with, entities can't respawn.
        }

        // Check if the taken damage kills the player
        if (event.getDamage() < player.getHealth()) {
            return; // He's not dead yet, you just grazed his ear.
        }

        final var key = CraftNamespacedKey.fromMinecraft(new ResourceLocation("minecraft", "soulbound"));
        var soulboundEnchantment = Registry.ENCHANTMENT.get(key);
        if (soulboundEnchantment == null) {
            //TODO: Log a warning/error/etc
            return;
        }

        // Check for soulbounded items
        var soulboundedItems = new ArrayList<ItemStack>();

        // There *HAS* to be a better way of doing this right???
        if (player.getEquipment() != null) {
            var equipment = player.getEquipment();
            if (equipment.getHelmet() != null && equipment.getHelmet().containsEnchantment(soulboundEnchantment)) {
                soulboundedItems.add(equipment.getHelmet());
                equipment.setHelmet(null);
            }
            if (equipment.getChestplate() != null && equipment.getChestplate().containsEnchantment(soulboundEnchantment)) {
                soulboundedItems.add(equipment.getChestplate());
                equipment.setChestplate(null);
            }
            if (equipment.getLeggings() != null && equipment.getLeggings().containsEnchantment(soulboundEnchantment)) {
                soulboundedItems.add(equipment.getLeggings());
                equipment.setLeggings(null);
            }
            if (equipment.getBoots() != null && equipment.getBoots().containsEnchantment(soulboundEnchantment)) {
                soulboundedItems.add(equipment.getBoots());
                equipment.setBoots(null);
            }
        }

        var inventory = player.getInventory();
        for (var item : inventory.getContents()) {
            if (item != null && item.containsEnchantment(soulboundEnchantment)) {
                soulboundedItems.add(item);
                inventory.remove(item);
            }
        }

        getLogger().info("Soulbounded items for " + event.getEntity().getName() + ": " + soulboundedItems);

        //event.getEntity().getInventory().addItem(soulboundedItems);

        // Add player items to soulboundStore
        UUID playerUuid = event.getEntity().getUniqueId();

        soulboundStore.put(playerUuid, soulboundedItems);
    }

    @EventHandler
    public void onPlayerRespawn(@NotNull PlayerRespawnEvent event) {
        // Exit if the player has no soulbounded items in store
        if (!soulboundStore.containsKey(event.getPlayer().getUniqueId())) {
            getLogger().info(event.getPlayer().getName() + "has no soulbounded items in the registry.");
            return;
        };

        final var key = CraftNamespacedKey.fromMinecraft(new ResourceLocation("minecraft", "soulbound"));
        var soulboundEnchantment = Registry.ENCHANTMENT.get(key);

        // Give player their soulbounded items
        List<ItemStack> playerItems = soulboundStore.get(event.getPlayer().getUniqueId());

        getLogger().info(event.getPlayer().getName() + "has the following soulbounded items in the registry: " + playerItems);

        for (ItemStack item : playerItems) {
            // Hack: Remove the item lore to prevent the old one from sticking around.
            if (item.getItemMeta() != null) {
                item.getItemMeta().setLore(new ArrayList<String>());
            }

            // Decrease the soulbound level
            if (item.getEnchantmentLevel(soulboundEnchantment) > 1) {
                var soulboundLevel = item.getEnchantmentLevel(soulboundEnchantment);
                if (soulboundLevel < 2) {   // Something fucked up
                    getLogger().warning(event.getPlayer().getName() + " has somehow broken something with: " + item);
                }

                item.addUnsafeEnchantment(soulboundEnchantment, item.removeEnchantment(soulboundEnchantment) - 1);
            } else {
                item.removeEnchantment(soulboundEnchantment);
                // make absolutely sure we got rid of the lore once and for all
                if (item.getItemMeta() != null) {
                    item.getItemMeta().setLore(new ArrayList<String>());
                }
            }
            // Give item
            event.getPlayer().getInventory().addItem(item);
        }

        // Remove the key-value pair from the map
        soulboundStore.remove(event.getPlayer().getUniqueId());
    }
}
