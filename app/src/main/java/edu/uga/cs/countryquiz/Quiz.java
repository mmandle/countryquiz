package edu.uga.cs.countryquiz;

/**
 * This class (a POJO) represents a single quiz, including the id, date,
 * and score.
 * The id is -1 if the object has not been persisted in the database yet, and
 * the db table's primary key value, if it has been persisted.
 */
public class Quiz {
    private long id;
    private String date;
    private int score;

    public Quiz() {
        this.id = -1;
        this.date = null;
        this.score = 0;
    }

    public Quiz(String date, int score) {
        this.id = -1;
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
