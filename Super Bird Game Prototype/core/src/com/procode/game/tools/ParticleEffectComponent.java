package com.procode.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class ParticleEffectComponent{
    private ParticleEffect particleEffect;
    private ParticleEffectPool particleEffectPool; // manages the particles
    private Array<ParticleEffectPool.PooledEffect> effects;

    // particleDir refers to the directory the .p file is located at
    // imagesDir refers to the directory where the images used to create the .p file is located at
    // example: particleEffect.load(Gdx.files.internal("effects/liquid.p"), Gdx.files.internal("bird animations"));
    public ParticleEffectComponent(String particleDir, String imagesDir){
        this.effects = new Array<ParticleEffectPool.PooledEffect>();

        //Nikko--I am thinking of making this unique to whoever implements it to their class
        this.particleEffect = new ParticleEffect();
        this.particleEffect.load(Gdx.files.internal(particleDir), Gdx.files.internal(imagesDir));

        // for additive emitters
        this.particleEffect.setEmittersCleanUpBlendFunction(false); //Nikko--this might also need to messed around in
        this.particleEffectPool = new ParticleEffectPool(particleEffect, 1, 10); //Nikko--not sure what the other 2 parameters are yet

    }

    public void createParticle(float x, float y){ // create particle only when collisions occur
        ParticleEffectPool.PooledEffect effect = particleEffectPool.obtain();
        effect.setPosition(x, y);
        this.effects.add(effect);
//        Gdx.app.log("(Create Particle) Effect Size: ", String.valueOf(effects.size));
    }

    public void update(float delta){
        for(ParticleEffectPool.PooledEffect effect : this.effects){
            effect.update(delta);
            if(effect.isComplete()){
                effect.free();
                this.effects.removeValue(effect, true);
            }
        }

    }

    public void renderParticles(SpriteBatch sb){ // renders particles that are created
        for(ParticleEffectPool.PooledEffect effect : this.effects){
            effect.draw(sb);
//            Gdx.app.log("Effect Complete:", String.valueOf(effect.isComplete()));
        }
    }

}
