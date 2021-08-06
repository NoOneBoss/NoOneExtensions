package me.nooneboss.wooleaterenchantment;

import com.willfp.eco.core.Prerequisite;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import com.willfp.ecoenchants.enchantments.util.EnchantmentUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;

import java.util.Locale;

public class WoolEater extends EcoEnchant {
    protected WoolEater() {
        super("wooleater", EnchantmentType.CURSE);
    }
    @EventHandler
    public void InteractIvent(PlayerShearEntityEvent e){
        if(!EnchantChecks.mainhand(e.getPlayer(), this)) return;
        int level = EnchantChecks.getMainhandLevel(e.getPlayer(), this);
        if(!EnchantmentUtils.passedChance(this, level)) return;

        e.setCancelled(true);
        Sheep sheep = (Sheep) e.getEntity();
        sheep.setSheared(true);

        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
        e.getPlayer().playSound(e.getPlayer().getLocation(),  Sound.valueOf(sound), 1f,0.3f);
    }
}
