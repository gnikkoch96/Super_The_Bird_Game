package com.procode.game;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import androidx.annotation.NonNull;
public class Database {

    private final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private final HashMap<String,Object> userdataMap = new HashMap<>();

    private String username, password, email, birthday, fullName;

    public Database(String username, String password, String email, String birthday, String fullname){
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullname;
        this.birthday = birthday;
    }

    //this class will insert the data to the database including the username, password,
    //email, birthday and fullName
    public void insertData(){
        userdataMap.put("username", "kev");
        userdataMap.put("password", "123");
        userdataMap.put("email", "email");
        userdataMap.put("name", "fullname");
        userdataMap.put("birthday", "birthday");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rootRef.child("Users").child(username).updateChildren(userdataMap).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                   // Toast
                               }else{
                               }
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
