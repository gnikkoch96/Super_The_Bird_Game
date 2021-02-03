package com.procode.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class ImageFunctions {
    public static Texture resize(String path, int newWidth, int newHeight) {
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
}
