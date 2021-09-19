package com.destructor.destructor2lit;

public class FireballJumpConfig {
    public Double knockbackradius;
    public Double knockbackmodifier;
    public Double explosionheightmodifier;
    public Double throwermultiplier;
    public Double velocitymodifier;

    public FireballJumpConfig(Double knockbackradius, Double knockbackmodifier, Double explosionheightmodifier, Double throwermultiplier, Double velocitymodifier) {
        this.knockbackradius = knockbackradius;
        this.knockbackmodifier = knockbackmodifier;
        this.explosionheightmodifier = explosionheightmodifier;
        this.throwermultiplier = throwermultiplier;
        this.velocitymodifier = velocitymodifier;
    }
}
