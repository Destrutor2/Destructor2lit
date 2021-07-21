package com.destructor.destructor2lit.enums;

public enum GamePhase {
	Start(5,"Diamond II"), DiamondII(6,"Emerald II"), EmeraldII(6,"Diamond III"), DiamondIII(6,"Emerald III"), EmeraldIII(6,"Bed gone"), Bedgone(10,"Sudden Death"), SuddenDeath(10,"Game End");


	int time;


	String display;

	GamePhase(int time, String display) {
		this.time = time;
		this.display = display;
	}


	public int getTime() {
		return time;
	}

	public String getDisplay() {
		return display;
	}
	public GamePhase getNext(){
		return this.ordinal() < GamePhase.values().length-1 ? GamePhase.values()[this.ordinal()+1]:null;
	}

}
