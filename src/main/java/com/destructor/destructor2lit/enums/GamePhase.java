package com.destructor.destructor2lit.enums;

import org.bukkit.Material;

public enum GamePhase {
	Start(5, "Diamond II", 30, 30, 1, 1), DiamondII(6, "Emerald II", 30, 20, 2, 1), EmeraldII(6, "Diamond III", 20, 20, 2, 2), DiamondIII(6, "Emerald III", 10, 20, 3, 2), EmeraldIII(6, "Bed gone", 10, 10, 3, 3), Bedgone(10, "Sudden Death", 10, 10, 3, 3), SuddenDeath(10, "Game End", 10, 10, 3, 3);


	int time;
	int emeraldDelay;
	int diamondDelay;
	int emeraldLevel;
	int diamondLevel;
	String display;

	GamePhase(int time, String display, int emeraldDelay, int diamondDelay, int diamondLevel, int emeraldLevel) {
		this.time = time;
		this.display = display;
		this.emeraldDelay = emeraldDelay;
		this.diamondDelay = diamondDelay;
		this.diamondLevel = diamondLevel;
		this.emeraldLevel = emeraldLevel;
	}


	public int getTime() {
		return time;
	}

	public String getDisplay() {
		return display;
	}

	public GamePhase getNext() {
		return this.ordinal() < GamePhase.values().length - 1 ? GamePhase.values()[this.ordinal() + 1] : null;
	}

	public int getEmeraldDelay() {
		return emeraldDelay;
	}

	public int getDiamondDelay() {
		return diamondDelay;
	}

	public int getGenLevel(Material genTypeMaterial) {
		switch (genTypeMaterial) {
			case DIAMOND:
				return diamondLevel;
			case EMERALD:
				return emeraldLevel;
			default:
				return -1;
		}
	}

	public int getGenDelay(Material genTypeMaterial) {
		switch (genTypeMaterial) {
			case DIAMOND:
				return diamondDelay;
			case EMERALD:
				return emeraldDelay;
			default:
				return -1;
		}
	}
}
