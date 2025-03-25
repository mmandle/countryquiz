package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class CountryQuizDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "countryquiz.db";
    private static final int DATABASE_VERSION = 1;

    // Private reference to the single instance
    private static CountryQuizDBHelper instance;

    // Column names just defined as Java constants
    // Table names
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_QUIZZES = "quizzes";

    // Country table columns
    public static final String COUNTRY_COLUMN_ID = "id";
    public static final String COUNTRY_COLUMN_NAME = "name";
    public static final String COUNTRY_COLUMN_CONTINENT = "continent";

    // Quiz table columns
    public static final String QUIZ_COLUMN_ID = "id";
    public static final String QUIZ_COLUMN_DATE = "date";
    public static final String QUIZ_COLUMN_SCORE = "score";

    // SQL statements
    private static final String CREATE_COUNTRIES = "CREATE TABLE " + TABLE_COUNTRIES + " ("
            + COUNTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COUNTRY_COLUMN_NAME + " TEXT NOT NULL, "
            + COUNTRY_COLUMN_CONTINENT + " TEXT NOT NULL);";

    private static final String CREATE_QUIZZES = "CREATE TABLE " + TABLE_QUIZZES + " ("
            + QUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZ_COLUMN_DATE + " TEXT NOT NULL, "
            + QUIZ_COLUMN_SCORE + " INTEGER NOT NULL);";

    private CountryQuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized CountryQuizDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CountryQuizDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    // This method creates the database
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COUNTRIES);
        db.execSQL(CREATE_QUIZZES);
    }

    // This method will perform a database upgrade, if the old recorded version of the database is lower than the new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        onCreate(db);
    }
}
