package com.procode.game.tools;

import com.badlogic.gdx.math.Vector2;

public class Hitbox {

    public Vector2 position;
    public int width, height;
    public Vector2 topleft, topright, botleft, botright; // coordinates of corners

    public Hitbox(Vector2 currentPos, int w, int h) {
        position = currentPos;
        width = w;
        height = h;
        topleft = new Vector2(position.x, position.y-height);
        topright = new Vector2(position.x+width, position.y-height);
        botleft = new Vector2(position.x, position.y);
        botright = new Vector2(position.x+width, position.y);
    }

    public boolean isHit(Hitbox other) {
        // Use an array to check all 4 corners of incoming objects
        Vector2[] other_corners = {other.topleft, other.topright, other.botleft, other.botright};

        // Check if the corner of the incoming object is inside of this hitbox
        for(Vector2 o : other_corners) {
            if((o.x >= botleft.x && o.x <= topright.x) && (o.y <= botleft.y && o.y >= topright.y)) {
                System.out.println("BIRD JUST GOT HIT BY SOMETHING!");
                return true;
            }
        }
        return false;
    }

    public void update(Vector2 newPos) {
        position = newPos;
        topleft = new Vector2(position.x, position.y-height);
        topright = new Vector2(position.x+width, position.y-height);
        botleft = new Vector2(position.x, position.y);
        botright = new Vector2(position.x+width, position.y);
    }
}
