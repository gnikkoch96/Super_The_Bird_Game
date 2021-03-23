package com.procode.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.List;

public class Animation {
    private List<Texture> anim; // the list holding the animations
    private int currFrame; // the current frame the animation is playing
    private float timeFrameUpdated; // the time in which the last frame was updated
    private float timePlayedPerFrame; // the amount of time each frame plays for
    private boolean animationEnded; // boolean that is only true when the current animation reached its last frame
    private boolean isLoop; // boolean (if the animation loops = true, else it is false)

    // the animation class. Only initializes the arraylist and sets the current frame to 0
    // also sets animationEnded boolean to false
    public Animation(){
        anim = new ArrayList<Texture>();
        currFrame = 0;
        animationEnded = false;
        timeFrameUpdated = 0;
        timePlayedPerFrame = 0;
    }

    // sets the animation that is to be played and clears the old one
    // *** the animationDir is a String that simply takes in a directory for the image (not including the number of the image
    // as it will update itself)
    // *** the imgWidth is an int of how large you wish your image width to be
    // *** the imgHeight is an int of how large you wish your image height to be
    // *** the startingFrame is an int of which frame you wish your animation to start off at
    // *** the endingFrame is an int of which frame you wish your animation to end off at (before cycling again)
    // *** the animSecs is a double of how long you wish the entire animation to play per cycle
    // *** the isLoop is a boolean that sets the animation to loop or not (SHOOT, DEAD, and DAMAGED are not looped, but IDLE is)
    public void setAnimation(String animationDir, int imgWidth, int imgHeight, int startingFrame, int endingFrame, float animSecs, boolean isLoop){
        anim.clear(); // clears the animation
        currFrame = 0; // resets the current frame
        this.isLoop = isLoop;
        float totalFrames = ((endingFrame + 1) - startingFrame);
        timePlayedPerFrame = animSecs / totalFrames;                       //Gives each animation equal display time

        // adds all images for the animation to the array
        for(int i = startingFrame; i < (endingFrame + 1); i++) {
            String path = animationDir;
            path = path.concat(Integer.toString(i));
            path = path.concat(".png");
            Texture tempImage = ImageFunctions.resize(path, imgWidth, imgHeight);
            anim.add(tempImage);
        }
    }

    // this function needs to be called continuously to update the current time the animation is played
    // updates the frame and leaves it on there until the TimePlayedPerFrame is reached
    // once the animation cycle is over, the animationFinished variable is set to true for that ending frame
    // until the cycle starts again
    public void updateFrame(float deltaTime){
        // updates the current frame once the time played per frame is up
        if (timeFrameUpdated + timePlayedPerFrame <= deltaTime){
            // resets the counter if the current frame is at the end of the animation
            int maxFrameCount = anim.size();
            if (currFrame + 1 >= maxFrameCount){
                if(isLoop){ // reset frame to zero only when the animation is looped
                    currFrame = 0;
                }else{
                    animationEnded = true;
                }
            }

            // goes to next frame
            else{
                currFrame += 1;
            }

            // updates the time
            timeFrameUpdated = deltaTime;
        }

    }
    public void setAnimationEnded(boolean var){this.animationEnded = var;} // used to fix the transition issue
    public Texture getCurrImg(){return anim.get(currFrame);} // returns the current frame for the image
    public boolean isAnimFinished(){return animationEnded;} // checks if the current animation cycle is finished
    public boolean getIsLoop(){return this.isLoop;} // used to differentiate between looped animation and non-loop
    public void dispose(){
        anim.clear();
    }
    public void replayLoop(){
        currFrame = 0;
        animationEnded = false;
    } // if you want to play an animation again, it will reset the animation
}
