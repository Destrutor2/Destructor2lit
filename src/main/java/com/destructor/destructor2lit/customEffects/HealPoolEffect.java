package com.destructor.destructor2lit.customEffects;

import com.destructor.destructor2lit.Main;
import de.slikey.effectlib.Effect;
import de.slikey.effectlib.EffectManager;
import de.slikey.effectlib.EffectType;
import de.slikey.effectlib.effect.AnimatedBallEffect;
import de.slikey.effectlib.effect.CircleEffect;
import de.slikey.effectlib.effect.ShieldEffect;
import de.slikey.effectlib.math.VectorTransform;
import de.slikey.effectlib.util.ParticleEffect;
import de.slikey.effectlib.util.RandomUtils;
import de.slikey.effectlib.util.VectorUtils;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class HealPoolEffect extends Effect{
    int particuleIntesity;
    double radius;
    Location cacheLocation;

    public HealPoolEffect(EffectManager effectManager, Location location, double radius) {
        super(effectManager);
        this.setLocation(location);
        this.infinite();
        this.period = 3;
        this.particuleIntesity = 10;
        this.particleOffsetX=1;
        this.particleOffsetY=1;
        this.particleOffsetZ=1;
        this.radius = Math.sqrt(radius);
    }

    @Override
    public void onRun() {
        Location location = getLocation();

        for (int x = -particuleIntesity; x <= particuleIntesity; x++) {
            for (int y = 0; y <= particuleIntesity; y++) {
                for (int z = -particuleIntesity; z <= particuleIntesity; z++) {
                    if((x*x+y*y+z*z)<=(particuleIntesity*particuleIntesity) && RandomUtils.random.nextFloat()<0.01){
                    cacheLocation = location.clone();
                    cacheLocation.add(x*radius/particuleIntesity,y*radius/particuleIntesity,z*radius/particuleIntesity);
                        this.display(ParticleEffect.VILLAGER_HAPPY, cacheLocation);
                    }
                }
            }
        }
    }
}
