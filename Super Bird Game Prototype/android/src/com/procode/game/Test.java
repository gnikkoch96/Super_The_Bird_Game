package com.procode.game;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.badlogic.gdx.ApplicationAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;

public class Test extends ApplicationAdapter {

    public Test(){
        onCreate();
    }

    public void onCreate(){
        //database reference
        final DatabaseReference rootRef;

        rootRef = FirebaseDatabase.getInstance().getReference();

        final HashMap<String,Object> userdataMap = new HashMap<>();
        userdataMap.put("username", "kev");
        userdataMap.put("password", "123");
        userdataMap.put("email", "email");
        userdataMap.put("name", "fullname");
        userdataMap.put("birthday", "birthday");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rootRef.child("Users").child("Nikko").updateChildren(userdataMap).
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
}
