package com.procode.game;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;

import androidx.annotation.NonNull;

public class Test extends ApplicationAdapter implements Screen {

    private Texture background;
    private SpriteBatch batch;
    public Test(){
        //onCreate();
     //   background = resize("background stuff/bg.png", Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
      // batch = new SpriteBatch();

    }

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

    public void onCreate(){
        //database reference
        final DatabaseReference rootRef;

        rootRef = FirebaseDatabase.getInstance().getReference();

        final HashMap<String,Object> userdataMap = new HashMap<>();
        userdataMap.put("username", "Mason");
        userdataMap.put("password", "123");
        userdataMap.put("email", "email");
        userdataMap.put("name", "fullname");
        userdataMap.put("birthday", "birthday");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rootRef.child("Users").child("Alexa").updateChildren(userdataMap).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
//                rootRef.child("Users").child(username).updateChildren(userdataMap)
//                        .addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            //check if the acount is created
//                            public void onComplete(@NonNull Task<Void> task) {
//                                if(task.isSuccessful()){
//
//                                }else{
//
//                                }
//                            }
//                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
       // batch.begin();
       //this.batch.draw(background, 0, 0);
        //batch.end();
    }

    @Override
    public void hide() {

    }
}
