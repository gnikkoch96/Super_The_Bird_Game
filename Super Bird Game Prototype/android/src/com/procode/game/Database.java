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
import com.procode.game.tools.Scores;

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
        String scoreboard = "0,0,0,0,0,0,0,0,0,0";
        userdataMap.put("username", this.username);
        userdataMap.put("password", this.password);
        userdataMap.put("email", this.email);
        userdataMap.put("fullName", this.fullName);
        userdataMap.put("scoreboard", scoreboard);

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

        emailMap.put("email", this.email);
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

        rootRef.child("Users").child(User.currentUser).setValue(username)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });

        final HashMap<String,Object> usernameMap = new HashMap<>();
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
        final HashMap<String,Object> emailMap = new HashMap<>();
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




    public void upDateScore(String score){

        rootRef.child("Users").child(User.currentUser).child("scoreboard").setValue(score)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });

        /*final HashMap<String,Object> scoreMap = new HashMap<>();
        scoreMap.put("user", score);
        rootRef.child("Scoreboard").child("scores").child("user").push().setValue(scoreMap).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });*/
    }

    public void getGlobalScores(){
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Scoreboard").child("scores").exists()){
                    Iterator<DataSnapshot> ds =
                            dataSnapshot.child("Scoreboard").child("scores").getChildren().iterator();
                    //Iterator<DataSnapshot> ds = dataSnapshot.getChildren().iterator();

                    int []num = new int[20];
                    String concatonate = "";
                    String []values = new String[20];
                    int counter = 0;
                    boolean flag = false;
                    boolean isSpace = false;
                    int []users = new int[21];
                    String name = "";
                    String []names = new String[21];
                    String rank = "";

                    while(ds.hasNext()){
                        values[counter] = ds.next().getValue(String.class);
                        //System.out.println("v:" + values[counter]);
                        for(int i =0; i < values[counter].length(); i++){

                            if(values[counter].charAt(i) == ' '){
                                isSpace = true;
                            }

                            if(flag == true){
                                concatonate += values[counter].charAt(i) + "";
                            }

                            if(isSpace == false){
                                rank += values[counter].charAt(i) + "";
                            }

                            if(values[counter].charAt(i) == ','){
                                flag = true;
                            }

                            if(flag == false && isSpace == true){
                                name += values[counter].charAt(i) + "";
                            }

                        }

                        num[counter] = Integer.parseInt(concatonate);

                        users[Integer.parseInt(rank)] = num[counter];
                        // System.out.println("d: " + rank + " "+ num[counter]);
                        //  System.out.println("u: " + users[Integer.parseInt(rank)]);
                        names[Integer.parseInt(rank)] = name;

                        concatonate = "";
                        name = "";
                        rank = "";
                        flag = false;
                        isSpace = false;
                        counter++;


                    }

                    for(int i=1; i < 21; i++){
                        System.out.println(names[i] + "          " + users[i]);
                        Scores.globalScores[i] = i + ".) " + names[i] + "          " + users[i];
                    }

                }else{

                    System.out.println("it does not exist");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    public void upDateGlobalScores(final int score){

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("Scoreboard").child("scores").exists()){
                    Iterator<DataSnapshot> ds =
                            dataSnapshot.child("Scoreboard").child("scores").getChildren().iterator();
                    //Iterator<DataSnapshot> ds = dataSnapshot.getChildren().iterator();

                    int []num = new int[20];
                    String concatonate = "";
                    String []values = new String[20];
                    int counter = 0;
                    boolean flag = false;
                    boolean isSpace = false;
                    int []users = new int[21];
                    String name = "";
                    String []names = new String[21];
                    String rank = "";

                    while(ds.hasNext()){
                        values[counter] = ds.next().getValue(String.class);
                        //System.out.println("v:" + values[counter]);
                        for(int i =0; i < values[counter].length(); i++){

                            if(values[counter].charAt(i) == ' '){
                                isSpace = true;
                            }

                            if(flag == true){
                                concatonate += values[counter].charAt(i) + "";
                            }

                            if(isSpace == false){
                                rank += values[counter].charAt(i) + "";
                            }

                            if(values[counter].charAt(i) == ','){
                                flag = true;
                            }

                            if(flag == false && isSpace == true){
                                name += values[counter].charAt(i) + "";
                            }

                        }

                        num[counter] = Integer.parseInt(concatonate);

                        users[Integer.parseInt(rank)] = num[counter];
                       // System.out.println("d: " + rank + " "+ num[counter]);
                      //  System.out.println("u: " + users[Integer.parseInt(rank)]);
                        names[Integer.parseInt(rank)] = name;

                        concatonate = "";
                        name = "";
                        rank = "";
                        flag = false;
                        isSpace = false;
                        counter++;


                    }

                    int i = 0;
                    flag =false;
                    int temp = 0;
                    for(i = 1; i < 21; i++){
                       // System.out.println(users[i]);
                        if(score > users[i]){
                            upDateRankScores(User.currentUser,  i + "", score);
                            temp = users[i];
                            flag = true;
                            break;
                        }

                    }

                    if(flag == true){
                        for(int x = i + 1; x < 21;x++){

                                upDateRankScores(names[x - 1],  x + "", temp);
                                temp = users[x];

                        }
                    }

                    /*flag = false;
                    boolean signal = false;
                    int temp = 0;
                    for(int i= 1; i < 11; i++){

                        if(flag = true){
                            upDateRankScores(names[i], i + "", temp);
                            temp = users[i];
                        }

                        if(score > users[i] && signal == false){
                            upDateRankScores(User.currentUser,  i + "", score);
                            temp = users[i];
                            flag = true;
                            signal = true;
                        }


                        //System.out.println("ranks: " + users[i]);
                    }*/


                }else{

                    System.out.println("it does not exist");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });


    }

    public void dumm(){
        int num = 500;
        for(int i=1; i < 21;i++){
            upDateRankScores("NoobMaster69", i + "", num);
            num -= 10;
        }
    }


    public void upDateRankScores(final String username, final String rank, final int score){
        rootRef.child("Scoreboard").child("scores").child("Rank" + rank).setValue(rank + " " + username + "," + score).
                addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    public void testUpdate(String score){

        rootRef.child("Users").child(User.currentUser).child("scoreboard").child(" ").setValue(score)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                        }
                    }
                });
    }

    public void getScores(){

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

                            User.currentUserScores = dataSnapshot.child(parentDbName).child(userName).
                                    child("scoreboard").getValue(String.class);

                            Database.userStatus = true;
                            Database.usernameStatus = "Exist";
                            LoginScreen.currentUser = userData;
                            User.currentUser = userName;
                            //User.userInfo = userData;
                            System.out.println("it exist : " + LoginScreen.userStatus);
                            System.out.println("scoreboad: " +  User.currentUserScores);
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
