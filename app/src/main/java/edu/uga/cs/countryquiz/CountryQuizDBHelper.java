package edu.uga.cs.countryquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is a SQLiteOpenHelper class, which Android uses to create, upgrade, delete an SQLite database
 * in an app.
 *
 * This class is a singleton, following the Singleton Design Pattern.
 * Only one instance of this class will exist.  To make sure, the
 * only constructor is private.
 * Access to the only instance is via the getInstance method.
 */
public class CountryQuizDBHelper extends SQLiteOpenHelper {

    private static final String DEBUG_TAG = "CountryQuizDBHelper";

    private static final String DATABASE_NAME = "countryquiz.db";
    private static final int DATABASE_VERSION = 1;

    // This is a reference to the only instance for the helper.
    private static CountryQuizDBHelper helperInstance;

    // Define all names (strings) for table and column names.
    // This will be useful if we want to change these names later.
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_QUIZZES = "quizzes";

    // Country table columns
    public static final String COUNTRY_COLUMN_ID = "_id";
    public static final String COUNTRY_COLUMN_NAME = "name";
    public static final String COUNTRY_COLUMN_CONTINENT = "continent";

    // Quiz table columns
    public static final String QUIZ_COLUMN_ID = "_id";
    public static final String QUIZ_COLUMN_DATE = "date";
    public static final String QUIZ_COLUMN_SCORE = "score";

    // A Create table SQL statement to create a table for countries.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_COUNTRIES = "CREATE TABLE " + TABLE_COUNTRIES + " ("
            + COUNTRY_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COUNTRY_COLUMN_NAME + " TEXT NOT NULL, "
            + COUNTRY_COLUMN_CONTINENT + " TEXT NOT NULL);";

    // A Create table SQL statement to create a table for quizzes.
    // Note that _id is an auto increment primary key, i.e. the database will
    // automatically generate unique id values as keys.
    private static final String CREATE_QUIZZES = "CREATE TABLE " + TABLE_QUIZZES + " ("
            + QUIZ_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + QUIZ_COLUMN_DATE + " TEXT NOT NULL, "
            + QUIZ_COLUMN_SCORE + " INTEGER NOT NULL);";

    // This constructor is private, so that it can be called only from this class, in the getInstance method.
    private CountryQuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Access method to the single instance of the class.
    // It is synchronized, so that only one thread can executes this method, at a time.
    public static synchronized CountryQuizDBHelper getInstance(Context context) {
        if (helperInstance == null) {
            helperInstance = new CountryQuizDBHelper(context.getApplicationContext());
        }
        return helperInstance;
    }

    // We must override onCreate method, which will be used to create the database if
    // it does not exist yet.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COUNTRIES);
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRIES + " created" );
        db.execSQL(CREATE_QUIZZES);
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " created" );
    }

    // We should override onUpgrade method, which will be used to upgrade the database if
    // its version (DB_VERSION) has changed.  This will be done automatically by Android
    // if the version will be bumped up, as we modify the database schema.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        onCreate(db);
        Log.d( DEBUG_TAG, "Table " + TABLE_COUNTRIES + " upgraded" );
        Log.d( DEBUG_TAG, "Table " + TABLE_QUIZZES + " upgraded" );
    }
}
