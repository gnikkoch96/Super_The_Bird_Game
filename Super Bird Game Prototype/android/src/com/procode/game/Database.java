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
        userdataMap.put("username", this.username);
        userdataMap.put("password", this.password);
        userdataMap.put("email", this.email);
        userdataMap.put("name", this.fullName);
        userdataMap.put("birthday", this.birthday);

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    //=========================This Section if for Updating The Following===========================
    //=====================Username, Password, Birthday, Email, FullName============================
    //==============================================================================================

    //update the username from the database
    public void upDateUsername(String username){

        rootRef.child("Users").child(this.username).child(this.username).setValue(username)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });

        rootRef.child("Users").child(this.username).setValue(username)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    //update the password from the database
    public void upDatePassword(String password){
        rootRef.child("Users").child(this.username).child(this.password).setValue(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    //update the email from the database
    public void upDateEmail(String email){
        rootRef.child("Users").child(this.username).child(this.email).setValue(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    //update the birthday from the database
    public void upDateBirthday(String birthday){
        rootRef.child("Users").child(this.username).child(this.birthday).setValue(birthday)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    //update the fullname from the database
    public void upDateFullName(String fullname){
        rootRef.child("Users").child(this.username).child(this.fullName).setValue(fullname)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    /*public void upDateScore(int score){
        rootRef.child("Users").child(this.username).child(this.score).setValue(score)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });

    }*/
    //===================================End of Updates ============================================
}
