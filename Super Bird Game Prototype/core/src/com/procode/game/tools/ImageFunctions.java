package com.procode.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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

    // Resizes a Texture and converts it to an ImageButton
    public static ImageButton resizeImageButton(String path, int newWidth, int newHeight) {
        Texture texture = resize(path, newWidth, newHeight);
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)));
    }

    // same as above just with an added image for the pressed image
    public static ImageButton resizeImageButton(String pathUp, String pathDown, int newWidth, int newHeight) {
        Texture texture = resize(pathUp, newWidth, newHeight);
        Texture texture2 = resize(pathDown, newWidth, newHeight);
        return new ImageButton(new TextureRegionDrawable(new TextureRegion(texture)), new TextureRegionDrawable(new TextureRegion(texture2)));
    }
}
