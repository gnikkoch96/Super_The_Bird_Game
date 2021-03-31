package com.procode.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.procode.game.SuperBirdGame;

public class Hitbox implements Disposable {
    public Vector2 position;
    public int width, height;
    public Vector2 topleft, topright, botleft, botright; // coordinates of corners

    //--DEBUG PURPOSES--//
    private ShapeRenderer shapeRenderer;

    public Hitbox(Vector2 currentPos, int w, int h) {
        position = currentPos;
        width = w;
        height = h;

        // y-value is reversed
//        topleft = new Vector2(this.position.x, this.position.y + height);
//        topright = new Vector2(this.position.x+width, this.position.y + height);
//        botleft = new Vector2(this.position.x, this.position.y);
//        botright = new Vector2(this.position.x+width, this.position.y);

        topleft = new Vector2(this.position.x, this.position.y-height);
        topright = new Vector2(this.position.x+width, this.position.y-height);
        botleft = new Vector2(this.position.x, this.position.y);
        botright = new Vector2(this.position.x+width, this.position.y);

        //--DEBUG--//
        shapeRenderer = new ShapeRenderer();
    }

    public boolean isHit(Hitbox other) {
        // Use an array to check all 4 corners of incoming objects
        Vector2[] other_corners = {other.topleft, other.topright, other.botleft, other.botright};

        // Check if the corner of the incoming object is inside of this hitbox
        for(Vector2 o : other_corners) {
            if((o.x >= this.botleft.x && o.x <= this.topright.x) && (o.y <= this.botleft.y && o.y >= this.topright.y)) {
//                Gdx.app.log("This Hitbox (Enemy)", "\nbotleft: (" + this.botleft.x + ", " + this.botleft.y + ")\n"
//                        + "botright: (" + this.botright.x + ", " + this.botright.y + ")\n"
//                        + "topleft: (" + this.topleft.x + ", " + this.topleft.y + ")\n"
//                        + "topright: (" + this.topright.x + ", " + this.topright.y + ")\n");
//                Gdx.app.log("Other Hitbox (Bullet)", "\nbotleft: (" + other.botleft.x + ", " + other.botleft.y + ")\n"
//                        + "botright: (" + other.botright.x + ", " + other.botright.y + ")\n"
//                        + "topleft: (" + other.topleft.x + ", " + other.topleft.y + ")\n"
//                        + "topright: (" + other.topright.x + ", " + other.topright.y + ")\n");
//                System.out.println("BIRD JUST GOT HIT BY SOMETHING!");
                return true;
            }
        }
        return false;
    }

    public void update(Vector2 newPos) {
        float update_y = this.position.y - height;
        float update_x = this.position.x + width;

        position = newPos;
        topleft.set(this.position.x, update_y);
        topright.set(update_x, update_y);
        botleft.set(this.position.x, this.position.y);
        botright.set(update_x, this.position.y);

//        Gdx.app.log("Hitbox " + String.valueOf(this.getClass()), "\nbotleft: (" + this.botleft.x + ", " + this.botleft.y + ")\n"
//                + "botright: (" + this.botright.x + ", " + this.botright.y + ")\n"
//                + "topleft: (" + this.topleft.x + ", " + this.topleft.y + ")\n"
//                + "topright: (" + this.topright.x + ", " + this.topright.y + ")\n");

    }

    //--DEBUG--//
    public void debugHitbox(){
        this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        this.shapeRenderer.setColor(Color.BLACK);
        this.shapeRenderer.rect(this.position.x, this.position.y, width, height);
        this.shapeRenderer.end();
    }

    public String toString() {
        return "(" + position.x + ", " + position.y + ")";
    }

    @Override
    public void dispose() {

    }
}
