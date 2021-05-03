package com.procode.game.tools;

import com.procode.game.Database;
import com.procode.game.User;

public class Scores {

    private String user, username, scoreboard;
    private int[] scores;
    private int userScore;

    public static String[] globalScores = new String[21];
    public Scores(){
        user = "";
        username = "";
        scoreboard = "";
        scores = new int[10];

    }

    public void setScore(int score){
        userScore = score;
        String scores = User.currentUserScores;

        Database database = new Database();
        database.upDateGlobalScores(score);

        int []populateScores = new int[10];
        boolean flag = false;
        String concatonate = "";
        int counter = 0;
        int i = 0;
        for(i=0; i < scores.length(); i++){

            if(scores.charAt(i) == ',') {
                populateScores[counter++] = Integer.parseInt(concatonate);
                concatonate = "";
                //flag = true;
            }
            else {
                concatonate += scores.charAt(i) + "";
                // flag = false;
            }

            //if(flag == false)

        }
        //populateScores[counter] = Integer.parseInt(concatonate);
        int temp = 0;
        flag = false;
        for(i = 0; i < 10; i++){



            if(flag == true){
                int temp1 = temp;
                temp = populateScores[i];
                populateScores[i] = temp1;
            }

            if(userScore > populateScores[i] && flag == false){
                temp =  populateScores[i];
                populateScores[i] = userScore;
                flag = true;
            }

            if(populateScores[i] == 0)
                break;
        }

        for(i=0; i < 10; i++){

            if(populateScores[i] == 0)
                break;
            if(i < 9)
            scoreboard += populateScores[i] + ",";
            else
                scoreboard += populateScores[i];
        }

        User.currentUserScores = scoreboard;

        database.upDateScore(scoreboard);
    }

    //this method will populate the scoreboard from the database
    public void populateScoreboard(){


        for(int i=0; i < user.length(); i++){
            char letter = user.charAt(i);

            if(letter == ','){
                break;

            }else{
                username += letter + "";
            }
        }


        String numbers = user.substring(username.length(), this.user.length());

        scoreboard = numbers;


        int []num = new int[10];
        int counter = 0;
        String score = "";
        for(int i=0; i < numbers.length(); i++){

            char letter = numbers.charAt(i);

            if(letter == ','){
                scores[counter++] = Integer.parseInt(score);
                score = "";
            }else{
                score += letter + "";
            }
        }

       // display();
    }

    public void display(){
        System.out.println("name : " + username);
        System.out.println("length of name: " + username.length());
        System.out.println("num: " + scores[3]);
    }

    public String getUser(){
        return user;
    }


    public String getUserName(){
        return username;
    }


    public int[] getScores(){
        String localScores = User.currentUserScores;
        String concatonate = "";
        int counter = 0;
        for(int i = 0; i < localScores.length(); i++){
            if(localScores.charAt(i) == ','){
                scores[counter++] = Integer.parseInt(concatonate);
                concatonate = "";
            }else{
                concatonate += localScores.charAt(i) + "";
            }
        }
//        scores[counter] = Integer.parseInt(concatonate);
        return scores;
    }


    public String getScoreboard(){
        return scoreboard;
    }
}
