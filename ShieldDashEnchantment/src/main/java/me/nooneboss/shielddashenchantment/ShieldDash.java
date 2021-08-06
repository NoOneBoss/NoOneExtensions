package me.nooneboss.shielddashenchantment;

import com.willfp.eco.core.integrations.anticheat.AnticheatManager;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.itemtypes.Spell;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Locale;

public class ShieldDash extends Spell {

    public ShieldDash() {
        super("shielddash");
    }

    @Override
    public boolean onUse(Player player, int level, PlayerInteractEvent playerInteractEvent) {
        if (!(playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_AIR) || playerInteractEvent.getAction().equals(Action.RIGHT_CLICK_BLOCK))) {
            return false;
        }
        double size = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "dash-power-per-level")*level;
        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
        int radius = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "entity-throwing-radius-multiplier-per-level") * level;
        AnticheatManager.exemptPlayer(player);
        Vector velocity = player.getEyeLocation().getDirection().clone();
        velocity.normalize();
        velocity.multiply(level * size);
        velocity.setY(0);
        player.setVelocity(velocity);

        Bukkit.getWorld(player.getWorld().getUID()).playSound(player.getLocation(), Sound.valueOf(sound), 0.5f,0.3f);
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.getPlugin(), new Runnable() {
            @Override
            public void run() {
                for (Entity ent : player.getNearbyEntities(radius, radius, radius)) {
                    if(ent instanceof LivingEntity) {
                        Vector v = ent.getLocation().getDirection().multiply(-1.0D).setY(0.9D);
                        ent.setVelocity(v);
                    }
                }
            }
        }, 0L, 10);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getScheduler().cancelTask(id);
            }
        }.runTaskLater(this.getPlugin(), 20);
        return true;
    }
}
