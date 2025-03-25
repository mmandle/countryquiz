package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CountryQuizData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper countryQuizDbHelper;

    // Define all column names for the quizzes table
    private static final String[] allColumns = {
            CountryQuizDBHelper.QUIZ_COLUMN_ID,
            CountryQuizDBHelper.QUIZ_COLUMN_DATE,
            CountryQuizDBHelper.QUIZ_COLUMN_SCORE
    };

    public CountryQuizData(Context context) {
        this.countryQuizDbHelper = CountryQuizDBHelper.getInstance(context);
    }

    public void open() {
        db = countryQuizDbHelper.getWritableDatabase();
    }

    public void close() {
        if (countryQuizDbHelper != null) {
            countryQuizDbHelper.close();
        }
    }

    // Store a new quiz result
    public Quiz storeQuiz(Quiz quiz) {
        ContentValues values = new ContentValues();

        // Store values from quiz argument object
        values.put(CountryQuizDBHelper.QUIZ_COLUMN_DATE, quiz.getDate());
        values.put(CountryQuizDBHelper.QUIZ_COLUMN_SCORE, quiz.getScore());

        // Insert the new row into DB table; the id (primary key) will be automatically generated by the DB system and returned from db.insert
        long id = db.insert(CountryQuizDBHelper.TABLE_QUIZZES, null, values);

        // Store the id in the Quiz instance, as it is now persistent
        quiz.setId(id);

        return quiz;
    } // storeQuiz

    public List<Quiz> retrieveAllQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = null;

        cursor = db.query(CountryQuizDBHelper.TABLE_QUIZZES, allColumns, null, null, null, null, null);

        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(CountryQuizDBHelper.QUIZ_COLUMN_ID);
            int dateIndex = cursor.getColumnIndex(CountryQuizDBHelper.QUIZ_COLUMN_DATE);
            int scoreIndex = cursor.getColumnIndex(CountryQuizDBHelper.QUIZ_COLUMN_SCORE);

            // Ensure indexes are valid to prevent the "Value must be ≥ 0" error
            if (idIndex != -1 && dateIndex != -1 && scoreIndex != -1) {
                long id = cursor.getLong(idIndex);
                String date = cursor.getString(dateIndex);
                int score = cursor.getInt(scoreIndex);

                // Creating a new object
                Quiz quiz = new Quiz(id, date, score);
                quiz.setId(id); // not really necessary
                quizzes.add(quiz);
            }
        }
        cursor.close();
        return quizzes;
    } // retrieveAllQuizzes
}
