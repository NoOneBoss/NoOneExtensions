package me.nooneboss.stainerenchantment;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import com.willfp.ecoenchants.enchantments.util.EnchantmentUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerShearEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Stainer extends EcoEnchant {
    protected Stainer() {
        super("stainer", EnchantmentType.NORMAL);
    }

    @EventHandler
    public void InteractIvent(PlayerShearEntityEvent e){
        if(!EnchantChecks.mainhand(e.getPlayer(), this)) return;
        int level = EnchantChecks.getMainhandLevel(e.getPlayer(), this);
        if(!EnchantmentUtils.passedChance(this, level)) return;
        List<Material> colors = Arrays.asList(
                Material.ORANGE_WOOL,
                Material.MAGENTA_WOOL,
                Material.LIGHT_BLUE_WOOL,
                Material.YELLOW_WOOL,
                Material.LIME_WOOL,
                Material.PINK_WOOL,
                Material.GRAY_WOOL,
                Material.LIGHT_GRAY_WOOL,
                Material.CYAN_WOOL,
                Material.PURPLE_WOOL,
                Material.BLUE_WOOL,
                Material.BROWN_WOOL,
                Material.GREEN_WOOL,
                Material.RED_WOOL,
                Material.BLACK_WOOL
        );
        Bukkit.getWorld(e.getEntity().getWorld().getUID()).dropItem(e.getEntity().getLocation(),
                new ItemStack(colors.get(new Random().nextInt(colors.size()))))
                .setVelocity(e.getPlayer().getLocation().getDirection().multiply(-0.3));
        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
        e.getPlayer().playSound(e.getPlayer().getLocation(),  Sound.valueOf(sound), 1f,0.3f);
    }
}
