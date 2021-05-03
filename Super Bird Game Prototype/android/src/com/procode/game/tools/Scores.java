package com.procode.game.tools;

public class Scores {

    private String user, username, scoreboard;
    private int[] scores;
    public Scores(){
        user = "";
        username = "";
        scoreboard = "";
        scores = new int[10];
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
        return scores;
    }


    public String getScoreboard(){
        return scoreboard;
    }
}
