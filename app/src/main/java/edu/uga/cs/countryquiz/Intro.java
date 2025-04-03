package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class Intro extends AppCompatActivity {

    private static final String DEBUG_TAG = "IntroActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // Link to your activity_intro layout

        Log.d(DEBUG_TAG, "onCreate: Checking if database has countries...");

        // Initialize database helper
        CountryQuizDBHelper dbHelper = CountryQuizDBHelper.getInstance(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Check if any countries exist in the database
        boolean isDbEmpty = isDatabaseEmpty(db);
        db.close(); // Close DB after checking

        if (isDbEmpty) {
            Log.d(DEBUG_TAG, "Database is empty. Proceeding with CSV import...");

            new CSVReaderTask(this, countriesList -> {
                if (countriesList != null && !countriesList.isEmpty()) {
                    Log.d(DEBUG_TAG, "CSVReaderTask: Countries list retrieved, storing into database...");

                    new StoreCountries(new CountryQuizData(this), () -> {
                        Log.d(DEBUG_TAG, "StoreCountries: Database setup complete!");
                    }).execute(countriesList);
                } else {
                    Log.e(DEBUG_TAG, "CSVReaderTask: No countries to store, skipping database initialization.");
                }
            }).execute();
        } else {
            Log.d(DEBUG_TAG, "Database already contains countries. Skipping CSV processing.");
        }

        // Find the Start button
        Button startButton = findViewById(R.id.startButton);

        // Set click listener to navigate to MainActivity
        startButton.setOnClickListener(v -> {
            Log.d(DEBUG_TAG, "Start button clicked, launching MainActivity.");
            Intent intent = new Intent(Intro.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close IntroActivity
        });
    }

    /**
     * Check if the countries table is empty.
     */
    private boolean isDatabaseEmpty(SQLiteDatabase db) {
        boolean isEmpty = true;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM countries", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                isEmpty = cursor.getInt(0) == 0; // If count == 0, DB is empty
            }
            cursor.close();
        }
        return isEmpty;
    }
}
