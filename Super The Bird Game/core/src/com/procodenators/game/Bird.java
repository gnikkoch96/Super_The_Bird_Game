package com.procodenators.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class Bird {

    private Texture[] idlebird;

    public Bird() {
        idlebird = new Texture[4];
        for(int i = 1; i < 5; i++) {
            String path = "Bird_Animations//idle bird ";
            path = path.concat(Integer.toString(i));
            path = path.concat(".png");
            Texture temp = resize(path, 150, 150);
            idlebird[i-1] = temp;
        }
    }

    public Texture resize(String path, int newWidth, int newHeight) {
        Pixmap original = new Pixmap(Gdx.files.internal(path));
        Pixmap resized = new Pixmap(newWidth, newHeight, original.getFormat());
        resized.drawPixmap(original,
                0, 0, original.getWidth(), original.getHeight(),
                0, 0, resized.getWidth(), resized.getHeight());
        Texture output = new Texture(resized);
        original.dispose();
        resized.dispose();
        return output;
    }

    public Texture[] getIdlebird() {
        return idlebird;
    }
}
