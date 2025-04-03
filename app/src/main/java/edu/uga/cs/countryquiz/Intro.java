package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class Intro extends AppCompatActivity {

    private static final String DEBUG_TAG = "IntroActivity";
    private static final String PREFS_NAME = "CountryQuizPrefs";
    private static final String KEY_DB_INITIALIZED = "db_initialized";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro); // Link to your activity_intro layout

        Log.d(DEBUG_TAG, "onCreate: Checking if database is already initialized...");

        // Get SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        // UNCOMMENT TO RESET DB TO FALSE
//        prefs.edit().putBoolean(KEY_DB_INITIALIZED, false).apply();
//        Log.d(DEBUG_TAG, "Forced reset: Database initialization flag set to false.");

        boolean isDbInitialized = prefs.getBoolean(KEY_DB_INITIALIZED, false);


        if (!isDbInitialized) {
            Log.d(DEBUG_TAG, "Database is NOT initialized. Proceeding with setup...");

            // Initialize database and populate with CSV data
            CountryQuizDBHelper countryQuizDbHelper = CountryQuizDBHelper.getInstance(this);
            Log.d(DEBUG_TAG, "CountryQuizDBHelper instance created.");

            new CSVReaderTask(this, countriesList -> {
                if (countriesList != null && !countriesList.isEmpty()) {
                    Log.d(DEBUG_TAG, "CSVReaderTask: Countries list retrieved, storing into database...");

                    new StoreCountries(new CountryQuizData(this), () -> {
                        Log.d(DEBUG_TAG, "StoreCountries: Database setup complete! Marking as initialized.");

                        // Mark database as initialized
                        prefs.edit().putBoolean(KEY_DB_INITIALIZED, true).apply();
                        Log.d(DEBUG_TAG, "Database initialization flag set to true.");
                    }).execute(countriesList);
                } else {
                    Log.e(DEBUG_TAG, "CSVReaderTask: No countries to store, skipping database initialization.");
                }
            }).execute();
        } else {
            Log.d(DEBUG_TAG, "Database is ALREADY initialized. Skipping CSV processing.");
        }

        // Find the Start button
        Button startButton = findViewById(R.id.startButton);

        // Set click listener to navigate to MainActivity
        startButton.setOnClickListener(v -> {
            Log.d(DEBUG_TAG, "Start button clicked, launching MainActivity.");
            Intent intent = new Intent(Intro.this, MainActivity.class);
            startActivity(intent);
            finish();  // Close the IntroActivity so the user can't go back to it
        });
    }
}
