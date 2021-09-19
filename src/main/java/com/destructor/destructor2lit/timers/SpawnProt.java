package com.destructor.destructor2lit.timers;

import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnProt extends BukkitRunnable {
    Player player;

    public SpawnProt(Player player) {
        this.player=player;
    }

    @Override
    public void run() {
        new Utils().setMetadata(player,"invincible",false);
    }
}
