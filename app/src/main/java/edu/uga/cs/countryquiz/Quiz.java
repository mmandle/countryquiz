package edu.uga.cs.countryquiz;

public class Quiz {
    private long id;
    private String date;
    private int score;

//    public Quiz() {
//        this.id = -1;
//        this.date = null;
//        this.score = 0;
//    }
//
//    public Quiz(String date, int score) {
//        this.id = -1;  // ID will be assigned by the database
//        this.date = date;
//        this.score = score;
//    }

    public Quiz(long id, String date, int score) {
        this.id = id;
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
