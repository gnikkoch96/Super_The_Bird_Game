package com.procode.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.procode.game.tools.ImageFunctions;

public class Bird {
    private Texture[] idlebird;
    private float healthCount;//Nikko

    public Bird() {
        idlebird = new Texture[4];
        healthCount = 6; //Nikko
        for(int i = 1; i < 5; i++) {
            String path = "Bird_Animations//idle bird ";
            path = path.concat(Integer.toString(i));
            path = path.concat(".png");
            Texture temp = ImageFunctions.resize(path, 150, 150);
            idlebird[i-1] = temp;
        }
    }


    public Texture[] getIdlebird() {
        return idlebird;
    }
}
