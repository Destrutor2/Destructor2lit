package com.destructor.destructor2lit.enums;

public enum BwDeaths {
	KILL("%bwplayer% was killed by %bwkiller%.","null"),
	VOID("%bwplayer% was knocked into the void by %bwkiller%.","%bwplayer% fell in the void."),
	FALL("%bwplayer% was knocked off a cliff by %bwkiller%.","%bwplayer% died."),
	RECONNECT("null","null"),
	ARROW("%bwplayer% was shot by %bwkiller%.","null"),
	FIRE("%bwplayer% died.","%bwplayer% died."),
	EXPLOSION("%bwplayer% was blown up by %bwkiller%.","%bwplayer% died."),
	GOLEM("%bwplayer% was killed by %bwkiller%'s Dream Defender.","null"),
	BUG("%bwplayer% was killed by %bwkiller%'s Bed Bug.","null");


	private final String defaultKillMessage;
	private final String defaultDeathMessage;

	BwDeaths(String defaultKillMessage,String defaultDeathMessage){
		this.defaultDeathMessage=defaultDeathMessage;
		this.defaultKillMessage=defaultKillMessage;
	}

	public String getDefaultKillMessage() {
		return defaultKillMessage;
	}

	public String getDefaultDeathMessage() {
		return defaultDeathMessage;
	}

}
