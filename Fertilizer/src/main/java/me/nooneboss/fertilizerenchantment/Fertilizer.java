package me.nooneboss.fertilizerenchantment;

import com.willfp.eco.core.Prerequisite;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Fertilizer extends EcoEnchant {

    protected Fertilizer() {
        super("fertilizer", EnchantmentType.SPECIAL);
    }
    @EventHandler
    public void InteractEvent(PlayerInteractEvent e){
        if (e.getClickedBlock() == null) return;
        if(!EnchantChecks.mainhand(e.getPlayer(), this)) return;
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
        List<String> blacklistedblocks = this.getConfig().getStrings(EcoEnchants.CONFIG_LOCATION + "blacklist-blocks");
        if(blacklistedblocks.contains(e.getClickedBlock().getType().name())) return;
        if(!e.getClickedBlock().applyBoneMeal(BlockFace.DOWN)) return;
        e.getClickedBlock().applyBoneMeal(BlockFace.DOWN);
        try {
            if (e.getItem().hasItemMeta()) {
                Damageable damageMeta = (Damageable) e.getItem().getItemMeta();
                damageMeta.setDamage(damageMeta.getDamage() + 1);
                e.getItem().setItemMeta((ItemMeta) damageMeta);
            }
        }
        catch (Exception ex){

        }
        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
        e.getPlayer().playSound(e.getPlayer().getLocation(),  Sound.valueOf(sound), 1f,0.3f);
    }

}
