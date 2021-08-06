package me.nooneboss.torchmasterenchantment;

import com.willfp.eco.core.Prerequisite;
import com.willfp.eco.core.integrations.anticheat.AnticheatManager;
import com.willfp.eco.core.integrations.antigrief.AntigriefManager;
import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.FaceAttachable;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.block.data.type.Ladder;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.Wall;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Torch;

import java.util.Locale;

public class TorchMaster extends EcoEnchant {

    protected TorchMaster() {
        super("torchmaster", EnchantmentType.SPECIAL);
    }

    @EventHandler
    public void Interact(PlayerInteractEvent e){
        if(e.getItem() == null) return;
        if(!EnchantChecks.mainhand(e.getPlayer(), this)) return;
        if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(e.getClickedBlock().isLiquid()) return;
        if(!e.getClickedBlock().getType().isBlock()) return;
        if(e.getClickedBlock().getBlockData() instanceof Directional) return;
        if(e.getClickedBlock().getBoundingBox().getMin().distance(e.getClickedBlock().getBoundingBox().getMax()) < 1.73) return;
        Boolean taketorch = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION + "take-torch-from-inventory");
        Boolean takedur = this.getConfig().getBool(EcoEnchants.CONFIG_LOCATION + "use-durabilty");
        Boolean notorch = false;
        if(taketorch) {
            Player player = e.getPlayer();
            Inventory inv = player.getInventory();
            if(!inv.contains(Material.TORCH)) notorch = true;
            if(!notorch) {
                inv.removeItem(new ItemStack(Material.TORCH));
            }
        }

        BlockFace bf = e.getBlockFace();
        Location loc = e.getClickedBlock().getLocation();
        loc.setX(loc.getX() + bf.getModX());
        loc.setY(loc.getY() + bf.getModY());
        loc.setZ(loc.getZ() + bf.getModZ());
        if(loc.getBlock().getBoundingBox().getMin().distance(e.getClickedBlock().getBoundingBox().getMax()) < 1.73) return;

        if(!AntigriefManager.canBreakBlock(e.getPlayer(), loc.getBlock())) return;
        if(bf.getModY() == 0) {
            loc.getBlock().setType(Material.WALL_TORCH);

            Directional dir = (Directional) loc.getBlock().getBlockData();
            dir.setFacing(bf);
            loc.getBlock().setBlockData(dir);
        }
        else if(bf.getModY() == -1){
            return;
        }
        else{
            loc.getBlock().setType(Material.TORCH);
        }
        loc.getBlock().getState().update(true);

        if(takedur && notorch) {
            if (e.getItem().hasItemMeta()) {
                Damageable damageMeta = (Damageable) e.getItem().getItemMeta();
                damageMeta.setDamage(damageMeta.getDamage() + 1);
                e.getItem().setItemMeta((ItemMeta) damageMeta);
            }
        }

        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + "activation-sound").toUpperCase(Locale.ROOT);
        e.getPlayer().playSound(e.getPlayer().getLocation(),  Sound.valueOf(sound), 1f,0.3f);

    }
}
