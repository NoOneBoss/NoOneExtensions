package me.nooneboss.guardianangelenchantment;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import com.willfp.ecoenchants.enchantments.util.EnchantmentUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.InvocationTargetException;

public class GuardianAngel extends EcoEnchant {

    protected GuardianAngel() {
        super("guardianangel", EnchantmentType.SPECIAL);
    }


    @EventHandler
    public void PlayerDeathEvent(EntityDamageEvent e){
        if(e.getEntity() instanceof Player) {
            if(!(e.getFinalDamage() > ((Player) e.getEntity()).getHealth()) || !(e.getDamage() > (((Player) e.getEntity()).getHealth()))) {return;}
            int level = EnchantChecks.getArmorPoints((Player)e.getEntity(),this);
            if(level == 0) return;;
            if (!EnchantmentUtils.passedChance(this, level)) return;

            int regenpower = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "regeneration-power-per-level")*level;
            int frtime = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "fire_resistance-time-per-level")*level;
            ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,60,regenpower));
            ((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE,20*frtime,1));
            Boolean animation = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION + "totem-animation");
            if(animation && Bukkit.getPluginManager().getPlugin("ProtocolLib").isEnabled()) {
                ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
                PacketContainer packetTotem = protocolManager.createPacket(PacketType.Play.Server.ENTITY_STATUS);
                packetTotem.getModifier().writeDefaults();
                packetTotem.getIntegers().write(0, e.getEntity().getEntityId());
                packetTotem.getBytes().write(0, (byte) 35);
                try {
                    protocolManager.sendServerPacket((Player) e.getEntity(), packetTotem);
                } catch (InvocationTargetException invocationTargetException) {
                    invocationTargetException.printStackTrace();
                }
            }
            e.setCancelled(true);
        }
    }
}
