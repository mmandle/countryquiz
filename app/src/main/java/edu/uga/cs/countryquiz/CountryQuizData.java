package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class CountryQuizData {
    private SQLiteDatabase db;
    private CountryQuizDBHelper dbHelper;

    public CountryQuizData(Context context) {
        dbHelper = CountryQuizDBHelper.getInstance(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    // Insert a new quiz result
    public void insertQuiz(String date, int score) {
        ContentValues values = new ContentValues();
        values.put(CountryQuizDBHelper.COLUMN_QUIZ_DATE, date);
        values.put(CountryQuizDBHelper.COLUMN_QUIZ_SCORE, score);
        db.insert(CountryQuizDBHelper.TABLE_QUIZZES, null, values);
    }
}
