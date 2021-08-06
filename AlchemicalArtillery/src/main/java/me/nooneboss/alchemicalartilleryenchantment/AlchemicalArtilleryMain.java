package me.nooneboss.alchemicalartilleryenchantment;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.extensions.Extension;
import com.willfp.ecoenchants.enchantments.EcoEnchant;

public class AlchemicalArtilleryMain extends Extension {
    public static final EcoEnchant WoolEater = new AlchemicalArtillery();
    public AlchemicalArtilleryMain(EcoPlugin plugin) {super(plugin);}

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
