package edu.uga.cs.countryquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

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

    // Retrieve all past quizzes
    public List<Quiz> getAllQuizzes() {
        List<Quiz> quizzes = new ArrayList<>();
        Cursor cursor = db.query(CountryQuizDBHelper.TABLE_QUIZZES,
                new String[]{CountryQuizDBHelper.COLUMN_QUIZ_ID,
                        CountryQuizDBHelper.COLUMN_QUIZ_DATE,
                        CountryQuizDBHelper.COLUMN_QUIZ_SCORE},
                null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Quiz quiz = new Quiz(
                        cursor.getString(cursor.getColumnIndex(CountryQuizDBHelper.COLUMN_QUIZ_DATE)),
                        cursor.getInt(cursor.getColumnIndex(CountryQuizDBHelper.COLUMN_QUIZ_SCORE))
                );
                quiz.setId(cursor.getLong(cursor.getColumnIndex(CountryQuizDBHelper.COLUMN_QUIZ_ID)));
                quizzes.add(quiz);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return quizzes;
    }
}
