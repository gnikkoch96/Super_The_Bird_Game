package com.procode.game;

import android.content.Intent;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.procode.game.User;
import com.procode.game.screens.LoginScreen;
import java.lang.Object;
import java.util.HashMap;
import java.util.Iterator;

import androidx.annotation.NonNull;
public class Database {

    private final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private final HashMap<String,Object> userdataMap = new HashMap<>();
    private String parentDbName; // this is the parent node from the database

    private FirebaseAuth myAuth;

    private String username, password, email, fullName;
    public static boolean userStatus;
    public static String usernameStatus = "NULL", emailStatus = "NULL";

    public Database(){
        this.username = "";
        this.password = "";
        this.email = "";
        this.fullName = "";
        this.parentDbName = "Users";
        this.userStatus = false;
        this.myAuth = FirebaseAuth.getInstance();
        this.usernameStatus = "NULL";
        this.emailStatus = "NULL";
    }

    public Database(String username, String password){
        this.username = username;
        this.password = password;
        this.parentDbName = "Users";
        this.userStatus = false;
        this. myAuth = FirebaseAuth.getInstance();

    }

    public Database(String username, String password, String email, String fullname){
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullname;
        this.parentDbName = "Users";
        this.userStatus = false;
        this.myAuth = FirebaseAuth.getInstance();
    }

    public void resetPassword(String email){

        myAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    System.out.println(FirebaseAuth.getInstance().getCurrentUser());
                }
            }
        });

    }

    public void upDatePassword(String oldpassword,final String newpassword){
      //  myAuth = FirebaseAuth.getInstance();
        //get current user
        final FirebaseUser user = myAuth.getCurrentUser();
        //re-authenticate the user
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(),oldpassword);

        user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //successfully authenticated begin to update
                user.updatePassword(newpassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("succesffully");
                    }
                });
            }
        });

    }

    public void signInAuthentication(String email, String password){
        //myAuth = FirebaseAuth.getInstance();
        myAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    System.out.println("I'm here: " + FirebaseAuth.getInstance().getUid());
            }
        });

    }

    //this method will insert information to the database after authenticating if the email
    //and password matches or exist
    public void authenticateUser(String email, String password){
        userdataMap.put("username", this.username);
        userdataMap.put("password", this.password);
        userdataMap.put("email", this.email);
        userdataMap.put("name", this.fullName);
        userdataMap.put("scoreboard", 0);

        myAuth = FirebaseAuth.getInstance();

        myAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        rootRef.child("Authentication").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(userdataMap).
                                addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            // Toast
                                            // userStatus = true;
                                        }else{
                                        }
                                    }
                                });;
                    }
                }
        );
    }

    //this class will insert the data to the database including the username, password,
    //email, birthday and fullName
    //only use this one for New Users registering a new account
    public void insertData(){
        userdataMap.put("username", this.username);
        userdataMap.put("password", this.password);
        userdataMap.put("email", this.email);
        userdataMap.put("name", this.fullName);
        userdataMap.put("scoreboard", 0);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rootRef.child("Users").child(username).updateChildren(userdataMap).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                   // Toast
                                   // userStatus = true;
                               }else{
                               }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        final HashMap<String,Object> usernameMap = new HashMap<>();
        final HashMap<String,Object> emailMap = new HashMap<>();

        usernameMap.put("username", this.username);
        emailMap.put("email", this.email);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rootRef.child("Usernames").child("value").push().setValue(usernameMap).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    // Toast
                                    // userStatus = true;
                                }else{
                                }
                            }
                        });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rootRef.child("Emails").child("value").push().setValue(emailMap).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    // Toast
                                    // userStatus = true;
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

        rootRef.child("Users").child(username).child("username").setValue(username)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });

    }

    //update fullname
    public void upDateFullName(String fullname){
        rootRef.child("Users").child(User.currentUser).child("name").setValue(fullname)
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
        rootRef.child("Users").child(User.currentUser).child("password").setValue(password)
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
        rootRef.child("Users").child(User.currentUser).child("email").setValue(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }





    public void upDateScore(int score){
        
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

    //=============================Start of User Validation ========================================

    //this method will check if the username and password matches
    public void checkDatabase(final String userName, final String userPassword){

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDbName).child(userName).exists()){
                    //here I use the constructor that have 5 arguments from User class and populate it from database
                    User userData = dataSnapshot.child(parentDbName).child(userName).getValue(User.class);

                    if(userData.getUserName().equals(userName)){
                        if(userData.getPassword().equals(userPassword)){

                            Database.userStatus = true;
                            Database.usernameStatus = "Exist";
                            LoginScreen.currentUser = userData;
                            System.out.println("it exist : " + LoginScreen.userStatus);
                        }else{
                            Database.usernameStatus = "DNE";
                        }
                    }
                }else{
                    Database.usernameStatus = "DNE";
                    System.out.println("it does not exist");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

    }

    public void checkUserName(final String username){
        rootRef.child("Usernames").child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                boolean itExist = false;
                Iterator<DataSnapshot> ds = dataSnapshot.getChildren().iterator();
                while(ds.hasNext()){
                    User user = ds.next().getValue(User.class);
                    if(username.equals(user.getUserName())) {
                        Database.usernameStatus = "Exist";
                        System.out.println("Username exist");
                        itExist = true;
                    }
                    System.out.println("e: " + user.getEmail());
                }
                if(itExist == false) {
                    System.out.println("New email");
                    Database.usernameStatus = "DNE";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //this method will check if the email exist
    public void checkEmail(final String email){

        rootRef.child("Emails").child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> ds = dataSnapshot.getChildren().iterator();
                while(ds.hasNext()){
                    User user = ds.next().getValue(User.class);
                    if(email.equals(user.getEmail())) {
                        Database.emailStatus = "Exist";
                        System.out.println("Email exist");
                    }
                    System.out.println("e: " + user.getEmail());
                }
                if(!Database.emailStatus.equals("Exist")) {
                    System.out.println("New email");
                    Database.emailStatus = "DNE";
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


        // System.out.println("i am here " + LoginScreen.userStatus);
    }
    public boolean userExist(){
        return userStatus;
    }
}
