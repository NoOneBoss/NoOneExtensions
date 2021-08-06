package me.nooneboss.handofhermesenchantment;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import me.nooneboss.utils.ColorUtil;
import me.nooneboss.utils.Countdown;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class HandOfHermes extends EcoEnchant {

    public HandOfHermes() {
        super("handofhermes",EnchantmentType.SPECIAL);
    }
    /*HashMap<UUID,double> PlayerWithAdditionalDamage = new HashMap<UUID, double>();
    HashMap<UUID,double> PlayerWithAdditionalHealth = new HashMap<UUID, double>();*/
    HashMap<UUID,Integer> cooldownPlayers = new HashMap<UUID, Integer>();

    @Override
    public void onMeleeAttack(LivingEntity attacker, LivingEntity victim, int level, EntityDamageByEntityEvent e) {
        if(!(attacker instanceof Player)) return;
        if(victim instanceof Player) return;
        if(!EnchantChecks.mainhand((Player)e.getDamager(),this)) return;
        String type = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "type");
        if(type.equalsIgnoreCase("cooldown")) {
            System.out.println(type);
            String message = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "cooldown-message").replace("{cooldown}", String.valueOf(cooldownPlayers.getOrDefault(attacker.getUniqueId(), 0)));
            Boolean messageenable = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION + "cooldown-message-enable");
            if (cooldownPlayers.containsKey(attacker.getUniqueId())) {
                if (messageenable)
                    ((Player) attacker).spigot().sendMessage(ChatMessageType.ACTION_BAR,
                            TextComponent.fromLegacyText(ColorUtil.legacyhex(message)));
                return;
            }
        }
        if(type.equalsIgnoreCase("chance")){
            if(!checkChance(level)) return;
        }
        List<String> blacklistedmobs = this.getConfig().getBukkitHandle().getStringList(EcoEnchants.CONFIG_LOCATION + "blacklisted-mob");
        for (String mob: blacklistedmobs) {
            if(mob.toUpperCase(Locale.ROOT).equals(victim.getType().name())) return;
        }
        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
        int time = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "effect-time");
        int cooldown = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "cooldown");
        int cooldown_reduction = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "cooldown-reduction-per-level") * level-1;
        int maxaddhealth = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "max-steal-health");
        int maxadddamage = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "max-steal-damage");

        double addhealth = victim.getHealth();
        double adddamage = 0;
        try {
            if (victim.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue() != 0) {
                adddamage = victim.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getValue();
            }
        }
        catch (Exception exc){

        }

        if(addhealth > maxaddhealth) addhealth = maxaddhealth;
        if(adddamage > maxadddamage) adddamage = maxadddamage;
        attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                .setBaseValue(attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + addhealth);
        attacker.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)
                .setBaseValue(adddamage + 1);
        attacker.setHealth(attacker.getMaxHealth());
        cooldownPlayers.put(attacker.getUniqueId(),cooldown - cooldown_reduction);
        Bukkit.getWorld(attacker.getWorld().getUID()).playSound(attacker.getLocation(), Sound.valueOf(sound), 1f,0.3f);
        e.setDamage(victim.getHealth() + 1);

        double finalAddhealth = addhealth;
        new BukkitRunnable() {
            @Override
            public void run() {
                attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH)
                        .setBaseValue(attacker.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() - finalAddhealth);
                attacker.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)
                        .setBaseValue(1);
                /*PlayerWithAdditionalDamage.remove(attacker.getUniqueId());
                PlayerWithAdditionalHealth.remove(attacker.getUniqueId());*/
            }
        }.runTaskLater(this.getPlugin(), time*20);
        if(type.equals("cooldown")) {
            new Countdown(cooldown - cooldown_reduction, this.getPlugin()) {
                @Override
                public void count(int current) {
                    current--;
                    cooldownPlayers.replace(attacker.getUniqueId(), current);
                    System.out.println(cooldownPlayers.get(attacker.getUniqueId()));
                }
            }.start();

            new BukkitRunnable() {
                @Override
                public void run() {
                    cooldownPlayers.remove(attacker.getUniqueId());
                }
            }.runTaskLater(this.getPlugin(), cooldown * 20 - cooldown_reduction * 20);
        }

    }
    public static int Random()
    {
        return (int) (Math.random() * 100);
    }
    public Boolean checkChance(int level){
        int rnd = Random();
        int chance = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "chance") * level;
        if(chance > Random() + 0.01){
            return true;
        }
        return false;
    }
}
