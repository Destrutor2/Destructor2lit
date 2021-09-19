package com.destructor.destructor2lit.listeners;

import com.destructor.destructor2lit.Main;
import com.destructor.destructor2lit.events.NpcClickEvent;
import com.destructor.destructor2lit.guis.Shop;
import com.destructor.destructor2lit.guis.Upgrades;
import com.destructor.destructor2lit.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NpcClickListener implements Listener {
    private final Main main;

    public NpcClickListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onNpcClick(NpcClickEvent e) {
        npcClickHandler(e, main);
    }

    public static void npcClickHandler(NpcClickEvent e, Main main) {
        Bukkit.getScheduler().runTask(main, new Runnable() {
            @Override
            public void run() {
                if (new Utils().getMetadata(e.getPlayer(), "alive").asBoolean()) {
                    if (e.isShop()) {
                        Shop.openShopUi(e.getPlayer(), 0, main);
                    }
                    if (e.isUpgrade()) {
                        Upgrades.openUI(e.getPlayer(), 0, main);
                    }
                }

            }
        });
    }

}
