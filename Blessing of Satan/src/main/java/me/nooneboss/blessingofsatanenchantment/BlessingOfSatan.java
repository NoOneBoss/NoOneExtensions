package me.nooneboss.blessingofsatanenchantment;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BlessingOfSatan extends EcoEnchant {
    protected BlessingOfSatan() {
        super("blessingofsatan", EnchantmentType.NORMAL);
    }
    @EventHandler
    public void MoveEvent(PlayerMoveEvent e){
        int level = EnchantChecks.getArmorPoints(e.getPlayer(),this);
        if(level == 0) return;
        if(e.getPlayer().getLocation().getBlock().isLiquid()){
            e.getPlayer().setFireTicks(0);
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        int level = EnchantChecks.getArmorPoints((Player)event.getEntity(),this);
        if(level == 0) return;

        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (damageCause == EntityDamageEvent.DamageCause.LAVA || damageCause == EntityDamageEvent.DamageCause.FIRE)
            event.setCancelled(true);
    }

}
