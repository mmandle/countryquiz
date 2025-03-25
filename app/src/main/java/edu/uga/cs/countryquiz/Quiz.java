package edu.uga.cs.countryquiz;

import java.util.Date;

public class Quiz {
    private long id;
    private String date;
    private int score;

    public Quiz() {
    }

    public Quiz(String date, int score) {
        this.date = date;
        this.score = score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
