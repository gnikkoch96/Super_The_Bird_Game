package com.procode.game.tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.procode.game.SuperBirdGame;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits + fixB.getFilterData().categoryBits;

        switch(cDef){
            case SuperBirdGame.BIRD_BIT | SuperBirdGame.ENEMY_BIT: // bird spit makes contact with an enemy
                if(fixA.getFilterData().categoryBits == SuperBirdGame.BIRDSPIT_BIT){ // fixture a is the BirdSpit obj
                    // switch the animation to finished

                }else{ // fixture b is the BirdSpit obj

                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
