package dev.scraft.nms.specific.enchant;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.bukkit.NamespacedKey;
import org.mineacademy.fo.enchant.NmsEnchant;
import org.mineacademy.fo.enchant.SimpleEnchantment;
import org.mineacademy.fo.enchant.SimpleEnchantmentRarity;

public class CustomEnchant_v1_20_4 extends Enchantment implements NmsEnchant {

    private final SimpleEnchantment simpleEnchantment;

    protected CustomEnchant_v1_20_4(SimpleEnchantment simpleEnchantment) {
        super(
                Rarity.valueOf(simpleEnchantment.getRarity().name()),
                EnchantmentCategory.valueOf(simpleEnchantment.getTarget().name()),
                EquipmentSlot.values()
        );

        this.simpleEnchantment = simpleEnchantment;
    }

    @Override
    public void register() {
        Registry.register(
                BuiltInRegistries.ENCHANTMENT,
                simpleEnchantment.getNamespacedName(),
                this
        );
    }

    @Override
    public org.bukkit.enchantments.Enchantment toBukkit() {
        return org.bukkit.enchantments.Enchantment.getByKey(
                new NamespacedKey("minecraft", simpleEnchantment.getNamespacedName())
        );
    }

    @Override
    public int getMinLevel() {
        return this.simpleEnchantment.getStartLevel();
    }

    @Override
    public int getMaxLevel() {
        return this.simpleEnchantment.getMaxLevel();
    }

    @Override
    public int getMinCost(int level) {
        return this.simpleEnchantment.getMinCost(level);
    }

    @Override
    public int getMaxCost(int level) {
        return this.simpleEnchantment.getMaxCost(level);
    }
}
