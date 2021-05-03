package com.procode.game;

import android.provider.ContactsContract;

import com.procode.game.screens.LoginScreen;
import com.procode.game.screens.Scoreboard;

public class User {

    private String username, password, email, fullName, scoreboard;
    public static String currentUser = "";
    public static String currentUserScores = "";

    public User(String username, String password, String email, String fullName, String scoreboard){
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.scoreboard = scoreboard;
    }

    public User(){
        username = "";
        password = "";
        email = "";
        scoreboard = "";
        fullName = "";
    }

    public void setUserName(String e){ username = e; }
    public void setPassword(String e){ password = e; }
    public void setEmail(String e){ email = e; }
    public void setBirthday(String e){ scoreboard = e; }
    public void setFullName(String e){ fullName = e; }

    public String getUserName(){ return username; }
    public String getPassword(){return password;}
    public String getEmail(){return email;}
    public String getBirthday(){return scoreboard;}
    public String getFullName(){return fullName;}

    public void InsertDataToDatabase(){
        //create new object of database
        Database data = new Database(username, password, email, fullName);
        //insert data to the database
        data.insertData();
    }

    public void resetPassword(String email, String password){
        Database data = new Database(email,"null");
        data.authenticateUser(email, password);
        data.resetPassword(email);
    }

    public void Test(String email, String password){
        Database data = new Database(email, password);

        //data.authenticateUser(email, password);
        data.signInAuthentication(email,password);
    }


    public void userExist(String username, String password){

        Database data = new Database(username, password);
        data.checkDatabase(username, password);
        //check if user exist

    }

    public String toString(){
        return "UserName: " + username + "\n" +
                "Password: " + password + "\n" +
                "FullName: " + fullName + "\n" +
                "Email: " + email + "\n";
    }
}
