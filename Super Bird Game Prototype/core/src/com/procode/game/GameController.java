package com.procode.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class GameController {

    protected OrthographicCamera cam;
    protected Vector2 finger;

    public GameController() {
        cam = new OrthographicCamera();
        finger = new Vector2();
    }

    protected void handleInput() {
        if(Gdx.input.isTouched()) {
            int fingerX = Gdx.input.getX();
            int fingerY = Gdx.input.getY();
            System.out.println("X: " + fingerX + " Y: " + fingerY);
        }
    }
    public void update(float dt) {

    }
    public void render(SpriteBatch sb) {

    }

}