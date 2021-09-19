package com.destructor.destructor2lit;

public class FireballConfig {
    public Double knockbackradius;
    public Double knockbackmodifier;
    public Double explosionheightmodifier;
    public Double explosionpower;
    public int fireradius;
    public float fireprob;
    public Double speed;

    public FireballConfig(Double knockbackradius, Double knockbackmodifier, Double explosionheightmodifier, Double explosionpower, int fireradius, float fireprob, Double speed) {
        this.knockbackradius = knockbackradius;
        this.knockbackmodifier = knockbackmodifier;
        this.explosionheightmodifier = explosionheightmodifier;
        this.explosionpower = explosionpower;
        this.fireradius = fireradius;
        this.fireprob = fireprob;
        this.speed = speed;
    }
}
