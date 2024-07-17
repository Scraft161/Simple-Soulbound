package dev.scraft.SimpleSoulbound;

import net.minecraft.resources.ResourceLocation;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.craftbukkit.v1_20_R3.util.CraftNamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerDeath(@NotNull PlayerDeathEvent event) {

        final NamespacedKey key = CraftNamespacedKey.fromMinecraft(new ResourceLocation("minecraft", "soulbound"));

        // Check for soulbounded items
        List<ItemStack> soulboundedItems = new ArrayList<ItemStack>();
        for (ItemStack item : event.getEntity().getInventory().getContents()) {
            if (item != null && item.containsEnchantment(Objects.requireNonNull(Registry.ENCHANTMENT.get(key)))) {
                soulboundedItems.add(item);
            }
        }

        getLogger().info("Soulbounded items for " + event.getEntity().getName() + ": " + soulboundedItems);
    }
}
