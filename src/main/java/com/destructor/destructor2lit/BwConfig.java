package com.destructor.destructor2lit;

public class BwConfig {
    public FireballConfig fireballConfig;
    public FireballJumpConfig fireballJumpConfig;
    public Double voidheight;
    public Double golemdamagemultiplier;
    public Double golemhealthmultiplier;
    public Double golemspeedmultiplier;
    public int buildlimit;
    public int builddownlimit;
    public int attacktagstaytimemillis;
    public float minbowcharge;
    public Double traptriggerradius;
    public byte popuptowerspeedmultiplier;
    public TeamGeneratorsConfig teamGeneratorsConfig;
    public int teamgenmultiplier = 1;
    public Double healpoolradius;
    public int spawnprotectionradius;

    public BwConfig(FireballConfig fireballConfig, FireballJumpConfig fireballJumpConfig, Double voidheight, Double golemdamagemultiplier, Double golemhealthmultiplier, Double golemspeedmultiplier, int buildlimit, int builddownlimit, int attacktagstaytimemillis, float minbowcharge, Double traptriggerradius, byte popuptowerspeedmultiplier, TeamGeneratorsConfig teamGeneratorsConfig, Double healpoolradius, int spawnprotectionradius) {
        this.fireballConfig = fireballConfig;
        this.fireballJumpConfig = fireballJumpConfig;
        this.voidheight = voidheight;
        this.golemdamagemultiplier = golemdamagemultiplier;
        this.golemhealthmultiplier = golemhealthmultiplier;
        this.golemspeedmultiplier = golemspeedmultiplier;
        this.buildlimit = buildlimit;
        this.builddownlimit = builddownlimit;
        this.attacktagstaytimemillis = attacktagstaytimemillis;
        this.minbowcharge = minbowcharge;
        this.traptriggerradius = traptriggerradius;
        this.popuptowerspeedmultiplier = popuptowerspeedmultiplier;
        this.teamGeneratorsConfig = teamGeneratorsConfig;
        this.healpoolradius = healpoolradius;
        this.spawnprotectionradius = spawnprotectionradius;
    }

    public BwConfig(Main main) {
        this(new FireballConfig(
                        main.getConfig().getDouble("Fireballs.knockbackradius"),
                        main.getConfig().getDouble("Fireballs.knockbackmodifier"),
                        main.getConfig().getDouble("Fireballs.explosionheightmodifier"),
                        main.getConfig().getDouble("Fireballs.explosionpower"),
                        main.getConfig().getInt("Fireballs.fireradius"),
                        (float) main.getConfig().getDouble("Fireballs.fireprob"),
                        main.getConfig().getDouble("Fireballs.speed")
                ),
                new FireballJumpConfig(
                        main.getConfig().getDouble("Fireballjump.knockbackradius"),
                        main.getConfig().getDouble("Fireballjump.knockbackmodifier"),
                        main.getConfig().getDouble("Fireballjump.explosionheightmodifier"),
                        main.getConfig().getDouble("Fireballjump.throwermultiplier"),
                        main.getConfig().getDouble("Fireballjump.velocitymodifier")
                ),
                main.getConfig().getDouble("voidheight"),
                main.getConfig().getDouble("golemdamagemultiplier"),
                main.getConfig().getDouble("golemhealthmultiplier"),
                main.getConfig().getDouble("golemspeedmultiplier"),
                main.getConfig().getInt("buildlimit"),
                main.getConfig().getInt("builddownlimit"),
                main.getConfig().getInt("attacktagstaytime"),
                (float) main.getConfig().getDouble("minbowcharge"),
                main.getConfig().getDouble("traptriggerradius"),
                (byte) main.getConfig().getInt("popuptowerspeedmultiplier"),
                new TeamGeneratorsConfig(
                        main.getConfig().getInt("TeamGenerators.mingolddelay"),
                        main.getConfig().getInt("TeamGenerators.maxgolddelay"),
                        main.getConfig().getInt("TeamGenerators.maxirondelay"),
                        main.getConfig().getInt("TeamGenerators.tickstotry")
                ),
                main.getConfig().getDouble("healpoolradius"),
                main.getConfig().getInt("spawnprotectionradius"));
    }
}

