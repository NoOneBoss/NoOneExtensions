package me.nooneboss.handofmidasenchantment;

import com.willfp.ecoenchants.enchantments.itemtypes.Spell;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class HandOfMidas extends Spell {
    public HandOfMidas() {
        super("handofmidas");
    }

    @Override
    public boolean onUse(Player player, int level, PlayerInteractEvent e) {
        return true;
    }
}
