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
    private static CountryQuizDBHelper instance;

    // Table names
    public static final String TABLE_COUNTRIES = "countries";
    public static final String TABLE_QUIZZES = "quizzes";

    // Country table columns
    public static final String COLUMN_COUNTRY_ID = "id";
    public static final String COLUMN_COUNTRY_NAME = "name";
    public static final String COLUMN_COUNTRY_CONTINENT = "continent";

    // Quiz table columns
    public static final String COLUMN_QUIZ_ID = "id";
    public static final String COLUMN_QUIZ_DATE = "date";
    public static final String COLUMN_QUIZ_SCORE = "score";

    // SQL statements
    private static final String CREATE_COUNTRIES_TABLE = "CREATE TABLE " + TABLE_COUNTRIES + " ("
            + COLUMN_COUNTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_COUNTRY_NAME + " TEXT NOT NULL, "
            + COLUMN_COUNTRY_CONTINENT + " TEXT NOT NULL);";

    private static final String CREATE_QUIZZES_TABLE = "CREATE TABLE " + TABLE_QUIZZES + " ("
            + COLUMN_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_QUIZ_DATE + " TEXT NOT NULL, "
            + COLUMN_QUIZ_SCORE + " INTEGER NOT NULL);";

    private CountryQuizDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized CountryQuizDBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new CountryQuizDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COUNTRIES_TABLE);
        db.execSQL(CREATE_QUIZZES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COUNTRIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZZES);
        onCreate(db);
    }

    // Retrieve all countries
    public List<Country> getAllCountries() {
        List<Country> countries = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_COUNTRIES, new String[]{COLUMN_COUNTRY_ID, COLUMN_COUNTRY_NAME, COLUMN_COUNTRY_CONTINENT},
                null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex(COLUMN_COUNTRY_ID);
                int nameIndex = cursor.getColumnIndex(COLUMN_COUNTRY_NAME);
                int continentIndex = cursor.getColumnIndex(COLUMN_COUNTRY_CONTINENT);

                if (idIndex != -1 && nameIndex != -1 && continentIndex != -1) {
                    countries.add(new Country(
                            cursor.getLong(idIndex),
                            cursor.getString(nameIndex),
                            cursor.getString(continentIndex)
                    ));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return countries;
    }
}
