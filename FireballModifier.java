package me.yourplugin.fireball;

// By Star Dream Studio 
// https://xmc.tw

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class FireballModifier extends JavaPlugin implements Listener {
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item != null && item.getType() == Material.FIREBALL) {
                event.setCancelled(true);
                Location eye = player.getEyeLocation();
                World world = player.getWorld();
                Fireball fireball = world.spawn(eye.add(eye.getDirection().multiply(0.5)), Fireball.class);
                fireball.setShooter(player);
                fireball.setDirection(eye.getDirection());
                fireball.setYield(2F); 
                fireball.setIsIncendiary(true); 
                item.setAmount(item.getAmount() - 1);
                player.getInventory().setItemInMainHand(item.getAmount() > 0 ? item : null);
            }
        }
    }

    @EventHandler
    public void onFireballExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Fireball) {
            Fireball fireball = (Fireball) entity;
            if (fireball.getShooter() instanceof Player) {
                event.blockList().clear(); 
            }
        }
    }
}
