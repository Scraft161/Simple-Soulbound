package dev.scraft.SimpleSoulbound;

import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.mineacademy.fo.enchant.SimpleEnchantment;

public final class SoulBoundEnchant extends SimpleEnchantment {

    @Getter
    private static final SoulBoundEnchant instance = new SoulBoundEnchant();

    private SoulBoundEnchant() {
        super("Soulbound", 3);
    }

    @Override
    public int getMinCost(int level) {
        return level + 26;
    }

    @Override
    public int getMaxCost(int level) {
        return 30;
    }

    //    @EventHandler
//    public void onDeath() {
//        // Only fire when the item has soulbound
//        if (this.hasEnchant(item)) {
//        }
//    }
}
