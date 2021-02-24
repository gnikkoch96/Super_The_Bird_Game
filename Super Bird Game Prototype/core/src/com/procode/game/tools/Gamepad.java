package com.procode.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.procode.game.SuperBirdGame;
import com.procode.game.screens.PlayScreen;

public class Gamepad extends PlayScreen {
    public float x;
    public float y;
    public Texture arrows;

    public Gamepad(SuperBirdGame game) {
        super(game);
    }

    public Texture getTexture() {
        return arrows;
    }

    public Vector2 getPosition() {
        return new Vector2(x, y);
    }
}
