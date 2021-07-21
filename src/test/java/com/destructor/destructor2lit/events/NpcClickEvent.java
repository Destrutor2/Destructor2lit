package com.destructor.destructor2lit.events;

import com.destructor.destructor2lit.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NpcClickEvent extends Event implements Cancellable {

	private final Player player;
	private final Boolean isShop;
	private final Boolean isUpgrade;
	private boolean isCancelled=false;
	private static final HandlerList handlers = new HandlerList();


	public NpcClickEvent(Player player, int npcid, Main main) {
		this.player = player;
		isUpgrade = main.npcManager.upgrades.contains(npcid);
		isShop = main.npcManager.shops.contains(npcid);
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public void setCancelled(boolean b) {
		isCancelled = b;
	}

	public Player getPlayer() {
		return player;
	}

	public Boolean isShop() {
		return isShop;
	}

	public Boolean isUpgrade() {
		return isUpgrade;
	}
}
