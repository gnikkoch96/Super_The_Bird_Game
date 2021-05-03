package com.procode.game.tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.procode.game.screens.SettingsScreen;

import java.util.Set;

public class Volume {

    private Container<Table> tableContainer;
    private Table table, buttonTable;
    private Image volumes, volumeLabel;
    private ImageButton leftVolumeBtn, rightVolumeBtn;
    private SpriteDrawable currentVolume,volume0, volume1,volume2, volume3,volume4, volume5,volume6,volume7, volume8,volume9;
    public Volume(){

        //this is for the volume image label
        volumeLabel = new Image(new Texture("screen icons//volumeLabel.png"));

        volumes = new Image();

        //create the images for the volume
        volumeImages();


        leftVolumeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//left_volumebtn.png"))));
        rightVolumeBtn = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("screen icons//right_volumebtn.png"))));
    }

    public void setVolume(int changeVolume){


        if(SettingsScreen.volumeChanges == 1) {
            currentVolume = volume0;
            SettingsScreen.volume = 0.1f;
        }
        else if(SettingsScreen.volumeChanges == 2) {
            currentVolume = volume1;
            SettingsScreen.volume = 0.2f;
        }
        else if(SettingsScreen.volumeChanges == 3) {
            currentVolume = volume2;
            SettingsScreen.volume = 0.3f;
        }
        else if(SettingsScreen.volumeChanges == 4) {
            currentVolume = volume3;
            SettingsScreen.volume = 0.4f;
        }
        else if(SettingsScreen.volumeChanges == 5) {
            currentVolume = volume4;
            SettingsScreen.volume = 0.5f;
        }
        else if(SettingsScreen.volumeChanges == 6) {
            currentVolume = volume5;
            SettingsScreen.volume = 0.6f;
        }
        else if(SettingsScreen.volumeChanges == 7) {
            currentVolume = volume6;
            SettingsScreen.volume = 0.7f;
        }else if(SettingsScreen.volumeChanges == 8) {
            currentVolume = volume7;
            SettingsScreen.volume = 0.8f;
        }else if(SettingsScreen.volumeChanges == 9) {
            currentVolume = volume8;
            SettingsScreen.volume = 0.9f;
        }else if(SettingsScreen.volumeChanges == 10) {
            currentVolume = volume9;
            SettingsScreen.volume = 1f;
        }

        volumes.setDrawable(currentVolume);
    }

    public Image getVolume(){
        return volumes;
    }

    //create the volume Images
    public void volumeImages(){
        volume0 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume0.png")));
        volume1 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume1.png")));
        volume2 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume2.png")));
        volume3 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume3.png")));
        volume4 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume4.png")));
        volume5 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume5.png")));
        volume6 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume6.png")));
        volume7 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume7.png")));
        volume8 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume8.png")));
        volume9 = new SpriteDrawable(new Sprite(new Texture("screen icons//volume9.png")));
    }


}
