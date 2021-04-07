package com.procode.game;

public class User {

    private String username, password, email, birthday, fullName;

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
        Database data = new Database(username, password, email, birthday, fullName);
        //insert data to the database
        data.insertData();
    }

    public String toString(){
        return "UserName: " + username + "\n" +
                "Password: " + password + "\n" +
                "FullName: " + fullName + "\n" +
                "Email: " + email + "\n" +
                "Birthday: " + birthday + "\n";
    }
}
