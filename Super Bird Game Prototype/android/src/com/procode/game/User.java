package com.procode.game;

import com.procode.game.screens.LoginScreen;

public class User {

    private String username, password, email, birthday, fullName, scoreboard;

    public User(String username, String password, String email, String birthday, String scoreboard){
        this.username = username;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.scoreboard = scoreboard;
    }

    public User(){
        username = "";
        password = "";
        email = "";
        birthday = "";
        fullName = "";
    }

    public void setUserName(String e){ username = e; }
    public void setPassword(String e){ password = e; }
    public void setEmail(String e){ email = e; }
    public void setBirthday(String e){ birthday = e; }
    public void setFullName(String e){ fullName = e; }

    public String getUserName(){ return username; }
    public String getPassword(){return password;}
    public String getEmail(){return email;}
    public String getBirthday(){return birthday;}
    public String getFullName(){return fullName;}

    public void InsertDataToDatabase(){
        //create new object of database
        Database data = new Database(username, password, email, fullName);
        //insert data to the database
        data.insertData();
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
