package me.nooneboss.curseofposeydonenchantment;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Calendar;
import java.util.HashMap;

public class CurseOfPoseydon extends EcoEnchant {
    protected CurseOfPoseydon() {
        super("curseofposeydon", EnchantmentType.CURSE);
    }

    @EventHandler
    public void MoveEvent(PlayerMoveEvent e){
        int level = EnchantChecks.getArmorPoints(e.getPlayer(),this);
        if(level == 0) return;
        double waterdamage = this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + ".water-damage-per-level") * level;
        if(!e.getPlayer().getLocation().getBlock().isLiquid()) return;
        Block block = e.getPlayer().getLocation().getBlock();
        if(block.getType().name().equals("WATER")){
            e.getPlayer().damage(waterdamage);
        }
        /*else{
            e.getPlayer().setFireTicks(0);
        }*/
    }
    /*@EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        EntityDamageEvent.DamageCause damageCause = event.getCause();

        if (damageCause == EntityDamageEvent.DamageCause.LAVA || damageCause == EntityDamageEvent.DamageCause.FIRE)
            event.setCancelled(true);
    }*/

}
