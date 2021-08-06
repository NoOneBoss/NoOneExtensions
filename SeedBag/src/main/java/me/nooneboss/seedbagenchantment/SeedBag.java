package me.nooneboss.seedbagenchantment;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import com.willfp.ecoenchants.enchantments.util.EnchantmentUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SeedBag extends EcoEnchant {
    public SeedBag() {
        super("seedbag", EnchantmentType.SPECIAL);
    }

    @EventHandler
    public void Interact(PlayerInteractEvent e){
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        if(!EnchantChecks.mainhand(e.getPlayer(), this)) return;
        List<Material> blocks = Arrays.asList(Material.DIRT,Material.GRASS_BLOCK);
        if(!blocks.contains(e.getClickedBlock().getType())) return;
        int level = EnchantChecks.getMainhandLevel(e.getPlayer(), this);
        if(!EnchantmentUtils.passedChance(this, level)) return;
        Location loc = e.getClickedBlock().getLocation();
        loc.setY((double) loc.getBlockY()+1);
        List<String> plants = this.getConfig().getStrings(EcoEnchants.CONFIG_LOCATION + "plants");
        new BukkitRunnable() {
            @Override
            public void run() {
                loc.getBlock().setType(Material.valueOf(plants.get(new Random().nextInt(plants.size()))));
            }
        }.runTaskLater(this.getPlugin(), 1);
        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
        e.getPlayer().playSound(e.getPlayer().getLocation(),  Sound.valueOf(sound), 1f,0.3f);
    }
}
