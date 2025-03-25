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
            long id = cursor.getLong(cursor.getColumnIndex(CountryQuizDBHelper.QUIZ_COLUMN_ID));
            String date = cursor.getString(cursor.getColumnIndex(CountryQuizDBHelper.QUIZ_COLUMN_DATE));
            int score = cursor.getInt(cursor.getColumnIndex(CountryQuizDBHelper.QUIZ_COLUMN_SCORE));

            // Creating a new object
            Quiz quiz = new Quiz(id, date, score);
            quiz.setId(id);
            quizzes.add(quiz);
        } // while

        return quizzes;
    } // retrieveAllQuizzes

//    // Retrieve all countries
//    public List<Country> getAllCountries() {
//        List<Country> countries = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.query(TABLE_COUNTRIES, new String[]{COLUMN_COUNTRY_ID, COLUMN_COUNTRY_NAME, COLUMN_COUNTRY_CONTINENT},
//                null, null, null, null, null);
//        if (cursor.moveToFirst()) {
//            do {
//                int idIndex = cursor.getColumnIndex(COLUMN_COUNTRY_ID);
//                int nameIndex = cursor.getColumnIndex(COLUMN_COUNTRY_NAME);
//                int continentIndex = cursor.getColumnIndex(COLUMN_COUNTRY_CONTINENT);
//
//                if (idIndex != -1 && nameIndex != -1 && continentIndex != -1) {
//                    countries.add(new Country(
//                            cursor.getLong(idIndex),
//                            cursor.getString(nameIndex),
//                            cursor.getString(continentIndex)
//                    ));
//                }
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return countries;
//    }
}
