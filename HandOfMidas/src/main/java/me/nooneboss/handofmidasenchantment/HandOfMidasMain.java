package me.nooneboss.handofmidasenchantment;

import com.willfp.eco.core.EcoPlugin;
import com.willfp.eco.core.extensions.Extension;
import com.willfp.ecoenchants.enchantments.EcoEnchant;

public class HandOfMidasMain extends Extension {
    public static final EcoEnchant HOM = new HandOfMidas();
    public HandOfMidasMain(EcoPlugin plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }
}
