/*package dev.scraft.SimpleSoulbound;

import lombok.Getter;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.collection.SerializedMap;
import org.mineacademy.fo.constants.FoConstants;
import org.mineacademy.fo.model.ConfigSerializable;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;
import java.util.Map;

public class SoulBoundStore extends YamlConfig {

    List<ItemStack> itemStore;

    @Getter
    private static final SoulBoundStore instance = new SoulBoundStore();

    private SoulBoundStore() {
        this.loadConfiguration(NO_DEFAULT, FoConstants.File.DATA);
    }

    /// Get the player items from the DB
    public List<ItemStack> get(String uuid) {
    }

    /// Save a player's items to the DB
    public void set(String uuid, List<ItemStack> items) {

        // Save to DB
        instance.save();
    }

    /// Remove a player's saved items from the DB
    public void remove(String uuid) {

        // Save to db
        instance.save();
    }
}*/
