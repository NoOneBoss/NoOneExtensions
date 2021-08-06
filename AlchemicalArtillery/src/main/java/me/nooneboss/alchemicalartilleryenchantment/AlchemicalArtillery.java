package me.nooneboss.alchemicalartilleryenchantment;

import com.willfp.eco.core.Prerequisite;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import com.willfp.ecoenchants.enchantments.util.EnchantmentUtils;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TippedArrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AlchemicalArtillery extends EcoEnchant {
    protected AlchemicalArtillery() {
        super("alchemicalartillery", EnchantmentType.SPECIAL);
    }
    @EventHandler
    public void Shoot(EntityShootBowEvent e){
        int level = EnchantChecks.getMainhandLevel(e.getEntity(), this);
        if(!EnchantmentUtils.passedChance(this, level)) return;
        if(e.getProjectile() instanceof Arrow && e.getEntity() instanceof Player) {
            if(!EnchantChecks.mainhand(e.getEntity(), this)) return;
            Arrow arrow = (Arrow) e.getProjectile();
            List<String> effects = this.getConfig().getStrings(EcoEnchants.CONFIG_LOCATION + "effects");
            int duration = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "effect-duration") * 20;
            int power = this.getConfig().getInt(EcoEnchants.CONFIG_LOCATION + "effect-power");
            if(!arrow.hasCustomEffects()) {
                PotionEffectType pet = PotionEffectType.getByName(effects.get(new Random().nextInt(effects.size())));
                arrow.addCustomEffect(new PotionEffect(pet, duration, power), true);
                e.setProjectile(arrow);
            }
            String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
            ((Player) e.getEntity()).playSound(e.getEntity().getLocation(),  Sound.valueOf(sound), 1f,0.3f);
        }
    }
}
