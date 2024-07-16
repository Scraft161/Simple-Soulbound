package dev.scraft.SimpleSoulbound;

import dev.scraft.nms.specific.enchant.CustomEnchant_v1_20_4;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.mineacademy.fo.enchant.SimpleEnchantment;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class Plugin extends SimplePlugin {

    @Override
    public void onPluginLoad() {
        // Register on_death and on_respawn events
        getLogger().info("Registering event hooks.");

        getLogger().info("Registering Enchantments");

        SimpleEnchantment.registerEnchantmentHandle(CustomEnchant_v1_20_4.class);
    }

    @Override
    protected void onPluginStart() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ssb")) {

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
