package com.destructor.destructor2lit.API;
//created by: oscar at 00:16 of 23/08/2021

import com.destructor.destructor2lit.enums.BwDeaths;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BwKillEvent extends Event {
	private static final HandlerList handlers = new HandlerList();

	Player killer;
	Player victim;
	BwDeaths bwDeathsType;
	boolean isFinal;

	public BwKillEvent(Player killer, Player victim, BwDeaths bwDeathsType, boolean isFinal) {
		this.killer = killer;
		this.victim = victim;
		this.bwDeathsType = bwDeathsType;
		this.isFinal=isFinal;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public Player getKiller() {
		return killer;
	}

	public Player getVictim() {
		return victim;
	}

	public BwDeaths getBwDeathsType() {
		return bwDeathsType;
	}

	public boolean isFinal(){
		return isFinal;
	}

}
