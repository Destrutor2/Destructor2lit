package com.destructor.destructor2lit

import org.bukkit.Location


//created by: oscar at 18:21 of 20/08/2021

data class BwConfig(
    var fireBallConfig: FireBallConfig,
    var fireballjumpConfig: FireballjumpConfig,
    var voidheight: Double,
    var golemdamagemultiplier: Double,
    var golemhealthmultiplier: Double,
    var golemspeedmultiplier: Double,
    var buildlimit: Int,
    var builddownlimit: Int,
    var attacktagstaytimemillis: Int,
    var minbowcharge: Float,
    var traptriggerradius: Double,
    var popuptowerspeedmultiplier: Byte,
    var teamGeneratorsConfig: TeamGeneratorsConfig
)

data class FireBallConfig(
    var knockbackradius: Double,
    var knockbackmodifier: Double,
    var explosionheightmodifier: Double,
    var explosionpower: Double,
    var fireradius: Int,
    var fireprob: Float,
    var speed: Double
)

data class FireballjumpConfig(
    var knockbackradius: Double,
    var knockbackmodifier: Double,
    var explosionheightmodifier: Double,
    var throwermultiplier: Double,
    var velocitymodifier: Double
)

data class TeamGeneratorsConfig(
    var mingolddelay: Int,
    var maxgolddelay: Int,
    var maxirondelay: Int,
    var tickstotry: Int
)