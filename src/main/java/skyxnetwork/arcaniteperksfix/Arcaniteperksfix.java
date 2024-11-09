package skyxnetwork.arcaniteperksfix;

import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class Arcaniteperksfix extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Enregistrement de l'écouteur d'événements
        Bukkit.getPluginManager().registerEvents(this, this);

        // Lancement de la tâche répétitive pour vérifier les plastrons
        new ArmorCheckTask().runTaskTimer(this, 0, 2); // Toutes les 0,1 secondes
    }

    @Override
    public @NotNull ComponentLogger getComponentLogger() {
        return super.getComponentLogger();
    }

    @Override
    public void onDisable() {
        // Code d'arrêt du plugin si nécessaire
    }
    
    private boolean isCustomLeatherChestplate(ItemStack item) {
        return item.getType() == Material.LEATHER_CHESTPLATE &&
                item.getItemMeta() != null &&
                item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 10006;
    }

    private class ArmorCheckTask extends BukkitRunnable {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack chestplate = player.getInventory().getChestplate();
                if (chestplate != null && isCustomLeatherChestplate(chestplate)) {
                    // Applique l'effet de résistance au feu pendant 0,1 seconde (2 ticks)
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 60, 0, true, false, false));
                }
            }
        }
    }
}