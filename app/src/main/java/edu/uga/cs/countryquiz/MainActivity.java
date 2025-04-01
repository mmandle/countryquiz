package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String DEBUG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button startQuizButton = findViewById(R.id.newQuiz);
        Button viewResultsButton = findViewById(R.id.pastResults);

        startQuizButton.setOnClickListener(v -> startQuiz());
        viewResultsButton.setOnClickListener(v -> {});

        // Check if the database already has countries before reading CSV
        CountryQuizData countryQuizData = new CountryQuizData(this);
        new RetrieveCountries(countryQuizData, countries -> {
            if (countries.isEmpty()) {
                Log.d(DEBUG_TAG, "Database is empty. Reading CSV and storing countries...");
                new CSVReaderTask(this, countriesList -> {
                    if (countriesList != null && !countriesList.isEmpty()) {
                        Log.d(DEBUG_TAG, "Storing countries into database...");
                        new StoreCountries(new CountryQuizData(this)).execute(countriesList);
                    } else {
                        Log.e(DEBUG_TAG, "No countries to store.");
                    }
                }).execute();
            } else {
                Log.d(DEBUG_TAG, "Database already contains " + countries.size() + " countries. Skipping CSV read.");
            }
        }).execute();
    }

    private void startQuiz() {
        Log.d(DEBUG_TAG, "Starting quiz...");
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }
}
