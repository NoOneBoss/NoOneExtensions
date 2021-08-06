package me.nooneboss.gluttonyenchantment;

import com.willfp.ecoenchants.enchantments.EcoEnchant;
import com.willfp.ecoenchants.enchantments.EcoEnchants;
import com.willfp.ecoenchants.enchantments.meta.EnchantmentType;
import com.willfp.ecoenchants.enchantments.util.EnchantChecks;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

public class Gluttony extends EcoEnchant {
    protected Gluttony() {
        super("gluttony", EnchantmentType.SPECIAL);
    }

    @EventHandler
    public void eatevent(FoodLevelChangeEvent e){
        if(!(e.getEntity() instanceof Player player)) return;
        int level = EnchantChecks.getArmorPoints(player,this);
        if(level == 0) return;
        ItemStack[] invcont = player.getInventory().getContents();
        List<ItemStack> filtredinv = Arrays.stream(invcont).filter(Objects::nonNull).filter(y -> y.getType().isEdible()).collect(Collectors.toList());
        if(filtredinv.size() == 0) return;
        List<String> blacklist = this.getConfig().getStrings("config.food-black-list");
        if(!(player.getFoodLevel() < 20)) return;
        ItemStack min = filtredinv.get(0);
        /*ItemStack max = filtredinv.get(filtredinv.size() - 1);*/
        for(ItemStack i : filtredinv){
            if(blacklist.contains(i.getType().name())) continue;
            if(foodValue(i.getType()) < foodValue(min.getType())) min = i;
            /*else if(foodValue(i.getType()) > foodValue(max.getType())) max = i;*/
        }
        if(foodValue(min.getType()) + player.getFoodLevel() > 20) return;

        player.getInventory().removeItem(new ItemStack(min.getType()));
        player.setFoodLevel(foodValue(min.getType()) + player.getFoodLevel());

        String sound = this.getConfig().getString(EcoEnchants.CONFIG_LOCATION + ".activation-sound").toUpperCase(Locale.ROOT);
        player.playSound(player.getLocation(),  Sound.valueOf(sound), 1f,0.3f);

        e.setCancelled(true);
        float es = (float) this.getConfig().getDouble(EcoEnchants.CONFIG_LOCATION + "extra-saturation-per-level");
        float saturation = saturationValue(min.getType());
        if(saturation + es > 20) {saturation = 20;}
        else{saturation += es;}
        player.setSaturation(saturation);
    }
    private float saturationValue(Material m){
        switch (m) {
            case GOLDEN_CARROT:
                return 14.4f;
            case COOKED_BEEF:
            case COOKED_PORKCHOP:
                return 12.8f;
            case RABBIT_STEW:
                return 12f;
            case COOKED_MUTTON:
            case COOKED_SALMON:
            case ENCHANTED_GOLDEN_APPLE:
            case GOLDEN_APPLE:
                return 9.6f;
            case MUSHROOM_STEW:
            case BEETROOT_SOUP:
            case COOKED_CHICKEN:
            case SUSPICIOUS_STEW:
                return 7.2f;
            case BAKED_POTATO:
            case COOKED_RABBIT:
            case COOKED_COD:
            case BREAD:
                return 6f;
            case PUMPKIN_PIE:
                return 4.8f;
            case CARROT:
                return 3.6f;
            case SPIDER_EYE:
                return 3.2f;
            case APPLE:
            case CHORUS_FRUIT:
                return 2.4f;
            case BEEF:
            case RABBIT:
            case PORKCHOP:
                return 1.8f;
            case HONEY_BOTTLE:
            case MELON_SLICE:
            case CHICKEN:
            case MUTTON:
            case POISONOUS_POTATO:
            case BEETROOT:
                return 1.2f;
            case ROTTEN_FLESH:
                return 0.8f;
            case SWEET_BERRIES:
            case POTATO:
            case DRIED_KELP:
                return 0.6f;
            case GLOW_BERRIES:
            case COD:
            case SALMON:
            case COOKIE:
                return 0.4f;
            case TROPICAL_FISH:
            case PUFFERFISH:
                return 0.2f;
            default:
                return 0;
        }
    }

    private int foodValue(Material m) {
        switch (m) {
            case RABBIT_STEW:
                return 10;
            case COOKED_BEEF:
            case COOKED_PORKCHOP:
            case PUMPKIN_PIE:
                return 8;
            case MUSHROOM_STEW:
            case BEETROOT_SOUP:
            case GOLDEN_CARROT:
            case COOKED_MUTTON:
            case COOKED_CHICKEN:
            case COOKED_SALMON:
            case SUSPICIOUS_STEW:
            case HONEY_BOTTLE:
                return 6;
            case COOKED_RABBIT:
            case COOKED_COD:
            case BREAD:
            case BAKED_POTATO:
                return 5;
            case ENCHANTED_GOLDEN_APPLE:
            case GOLDEN_APPLE:
            case APPLE:
            case CHORUS_FRUIT:
            case ROTTEN_FLESH:
                return 4;
            case CARROT:
            case BEEF:
            case RABBIT:
            case PORKCHOP:
                return 3;
            case SWEET_BERRIES:
            case GLOW_BERRIES:
            case MELON_SLICE:
            case CHICKEN:
            case COD:
            case SALMON:
            case COOKIE:
            case MUTTON:
            case SPIDER_EYE:
            case POISONOUS_POTATO:
                return 2;
            case BEETROOT:
            case POTATO:
            case TROPICAL_FISH:
            case PUFFERFISH:
            case DRIED_KELP:
                return 1;
            default:
                return 0;
        }

    }
}
