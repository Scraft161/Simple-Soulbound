package dev.scraft.SimpleSoulbound;

import dev.scraft.nms.specific.enchant.CustomEnchant_v1_20_4;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.mineacademy.fo.enchant.SimpleEnchantment;
import org.mineacademy.fo.plugin.SimplePlugin;


public final class Plugin extends SimplePlugin {

    @Override
    public void onPluginLoad() {

        getLogger().info("Registering Enchantments");

        SimpleEnchantment.registerEnchantmentHandle(CustomEnchant_v1_20_4.class);
    }

    @Override
    public void onPluginStart() {
        // Register on_death and on_respawn events
        getLogger().info("Registering event hooks.");

        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ssb")) {
            if (args.length > 1) {
                sender.sendMessage("Incorrect usage, type /ssb help for help");
                return true;
            }

            switch(args[0]) {
                case "version":
                    sender.sendMessage("SimpleSoulbound version: 0.0.1");
                    break;
                default:
                    sender.sendMessage("Usage: uuhm...");
                    break;
            }
            return true;
        }

        return false;
    }
}

