package com.example.quizapplication.Class;

public class Items {

    private String playerName, playerScore;

    public Items(String playerName, String playerScore) {
        this.playerName = playerName;
        this.playerScore = playerScore;
    }

    public Items(){

    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(String playerScore) {
        this.playerScore = playerScore;
    }
}
