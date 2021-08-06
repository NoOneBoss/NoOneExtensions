package me.nooneboss.fertilizerenchantment;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.extensions.Extension;
import com.willfp.ecoenchants.enchantments.EcoEnchant;

public class FertilizerMain extends Extension {
    public static final EcoEnchant Fertilizer = new Fertilizer();
    public FertilizerMain(EcoPlugin plugin) {super(plugin);}

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
