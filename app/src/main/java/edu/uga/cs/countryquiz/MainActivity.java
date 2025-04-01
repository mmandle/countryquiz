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

public class MainActivity extends AppCompatActivity implements CSVReaderTask.OnCSVReadListener {

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

        // Initialize buttons
        Button startQuizButton = findViewById(R.id.newQuiz);
        Button viewResultsButton = findViewById(R.id.pastResults);

        // Find the "New Quiz" button
        startQuizButton.setOnClickListener(v -> startQuiz());
        viewResultsButton.setOnClickListener(v -> {});

        // Start reading the CSV file asynchronously
        new CSVReaderTask(this, this).execute();
    }

    private void startQuiz() {
        // Logic to start the quiz (start a new activity, for example)
        Log.d(DEBUG_TAG, "Starting quiz...");
        Intent intent = new Intent(MainActivity.this, QuizActivity.class);
        startActivity(intent);
    }

//    private void viewResults() {
//        // Logic to view results (start a new activity for the results)
//        Log.d(DEBUG_TAG, "Viewing results...");
//        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
//        startActivity(intent);
//    }

    @Override
    public void onCSVRead(List<Country> countries) {
        // Callback method when CSV is read successfully
        if (countries != null && !countries.isEmpty()) {
            Log.d(DEBUG_TAG, "Inserting countries into database...");

            // Create an instance of CountryQuizData to interact with the database
            CountryQuizData countryQuizData = new CountryQuizData(this);

            // Use the InsertCountries class to insert countries asynchronously
            new InsertCountries(countryQuizData).execute(countries);

        } else {
            Log.e(DEBUG_TAG, "No countries to insert.");
        }
    }
}
