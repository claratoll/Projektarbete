package com.example.projektarbete;

import java.io.Serializable;


public class Score implements Serializable {

    private int intScore;
    private String stringScore;

    private int score;
    private String player;


    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }


    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }


    public String Score() {
        return intScore + " " + stringScore;
    }


    public int getIntScore() {
        return intScore;
    }

    public void setIntScore(int intScore) {
        this.intScore = intScore;
    }


}
