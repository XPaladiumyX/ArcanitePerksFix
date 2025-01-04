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

import java.util.HashSet;
import java.util.Set;

public final class Arcaniteperksfix extends JavaPlugin implements Listener {

    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_LIGHT_GRAY = "\u001B[37m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_LIGHT_GREEN = "\u001B[92m";
    private static final String ANSI_RED = "\u001B[31m";

    private final Set<Player> playersWithHealthBoost = new HashSet<>();

    @Override
    public void onEnable() {
        // Affiche un message de démarrage dans la console
        Bukkit.getLogger().info(ANSI_LIGHT_GRAY + "︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹");
        Bukkit.getLogger().info(ANSI_MAGENTA + " _______  ___   _  __   __    __   __    __    _  _______  _______ " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|       ||   | | ||  | |  |  |  |_|  |  |  |  | ||       ||       |" + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|  _____||   |_| ||  |_|  |  |       |  |   |_| ||    ___||_     _|" + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "| |_____ |      _||       |  |       |  |       ||   |___   |   |  " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|_____  ||     |_ |_     _|   |     |   |  _    ||    ___|  |   |  " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + " _____| ||    _  |  |   |    |   _   |  | | |   ||   |___   |   |  " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|_______||___| |_|  |___|    |__| |__|  |_|  |__||_______|  |___|  " + ANSI_RESET);
        Bukkit.getLogger().info("   ");
        Bukkit.getLogger().info(ANSI_LIGHT_GREEN + "Plugin ArcanitePerksFix enabled !");
        Bukkit.getLogger().info(ANSI_LIGHT_GRAY + "︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺");
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
        Bukkit.getLogger().info(ANSI_LIGHT_GRAY + "︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹︹");
        Bukkit.getLogger().info(ANSI_MAGENTA + " _______  ___   _  __   __    __   __    __    _  _______  _______ " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|       ||   | | ||  | |  |  |  |_|  |  |  |  | ||       ||       |" + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|  _____||   |_| ||  |_|  |  |       |  |   |_| ||    ___||_     _|" + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "| |_____ |      _||       |  |       |  |       ||   |___   |   |  " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|_____  ||     |_ |_     _|   |     |   |  _    ||    ___|  |   |  " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + " _____| ||    _  |  |   |    |   _   |  | | |   ||   |___   |   |  " + ANSI_RESET);
        Bukkit.getLogger().info(ANSI_MAGENTA + "|_______||___| |_|  |___|    |__| |__|  |_|  |__||_______|  |___|  " + ANSI_RESET);
        Bukkit.getLogger().info("   ");
        Bukkit.getLogger().info(ANSI_RED + "  Plugin ArcanitePerksFix disabled !");
        Bukkit.getLogger().info(ANSI_LIGHT_GRAY + "︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺︺");
    }

    private boolean isCustomLeatherChestplate(ItemStack item) {
        return item.getType() == Material.LEATHER_CHESTPLATE &&
                item.getItemMeta() != null &&
                item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 10006;
    }

    private boolean isCustomLeatherLeggings(ItemStack item) {
        return item.getType() == Material.LEATHER_LEGGINGS &&
                item.getItemMeta() != null &&
                item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 10006;
    }

    private boolean isCustomLeatherBoots(ItemStack item) {
        return item.getType() == Material.LEATHER_BOOTS &&
                item.getItemMeta() != null &&
                item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 10006;
    }

    private boolean isCustomLeatherBootsSpeed2(ItemStack item) {
        return item != null && item.getType() == Material.LEATHER_BOOTS &&
                item.getItemMeta() != null && item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 10001;
    }

    private boolean isCustomLeatherHelmet(ItemStack item) {
        return item.getType() == Material.LEATHER_HELMET &&
                item.getItemMeta() != null &&
                item.getItemMeta().hasCustomModelData() &&
                item.getItemMeta().getCustomModelData() == 10005;
    }

    private class ArmorCheckTask extends BukkitRunnable {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ItemStack chestplate = player.getInventory().getChestplate();
                ItemStack leggings = player.getInventory().getLeggings();
                ItemStack boots = player.getInventory().getBoots();
                ItemStack helmet = player.getInventory().getHelmet();

                // Gestion du plastron
                if (chestplate != null && isCustomLeatherChestplate(chestplate)) {
                    if (!playersWithHealthBoost.contains(player)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 0, true, false, false));
                        playersWithHealthBoost.add(player);
                    }
                    player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 0, true, false, false));
                } else if (playersWithHealthBoost.contains(player)) {
                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    playersWithHealthBoost.remove(player);
                }

                // Gestion des jambières (Invisibilité)
                if (leggings != null && isCustomLeatherLeggings(leggings)) {
                    if (!hasActiveEffect(player, PotionEffectType.INVISIBILITY, 0)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 200, 0, true, false, false));
                    }
                }

                // Gestion des bottes (Vitesse)
                if (boots != null) {
                    if (isCustomLeatherBoots(boots) && !hasActiveEffect(player, PotionEffectType.SPEED, 0)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 0, true, false, false));
                    } else if (isCustomLeatherBootsSpeed2(boots) && !hasActiveEffect(player, PotionEffectType.SPEED, 1)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1, true, false, false));
                    }
                }

                // Gestion du casque (Vision nocturne)
                if (helmet != null && isCustomLeatherHelmet(helmet)) {
                    if (!hasActiveEffect(player, PotionEffectType.NIGHT_VISION, 0)) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 400, 0, true, false, false));
                    }
                }
            }
        }

        /**
         * Vérifie si un joueur a un effet actif avec un niveau spécifique.
         *
         * @param player Joueur à vérifier.
         * @param effect Effet à vérifier.
         * @param level  Niveau de l'effet.
         * @return True si l'effet est actif avec le niveau spécifié.
         */
        private boolean hasActiveEffect(Player player, PotionEffectType effect, int level) {
            PotionEffect activeEffect = player.getPotionEffect(effect);
            return activeEffect != null && activeEffect.getAmplifier() == level;
        }
    }
}