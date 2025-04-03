package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class PastResultActivity extends AppCompatActivity {

    private TextView textViewResults, textViewTotalQuizzes;
    private CountryQuizData countryQuizData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_result);

        textViewResults = findViewById(R.id.textViewResults);
        textViewTotalQuizzes = findViewById(R.id.textViewTotalQuizzes);
        countryQuizData = new CountryQuizData(this);

        // Fetch past quizzes and display them
        new RetrieveQuizzes(countryQuizData, quizzes -> {
            // Update total quizzes count
            textViewTotalQuizzes.setText("Total number of quizzes: " + quizzes.size());

            // Display quizzes in a two-column format
            StringBuilder resultsText = new StringBuilder();
            resultsText.append("Date          | Score\n");
            resultsText.append("----------------------\n");
            for (Quiz quiz : quizzes) {
                resultsText.append(quiz.getDate()).append("  |  ").append(quiz.getScore()).append("\n");
            }
            textViewResults.setText(resultsText.toString());
        }).execute();

        // Set up the back button
        Button buttonBackToMain = findViewById(R.id.buttonBackToMain);
        buttonBackToMain.setOnClickListener(v -> {
            Intent intent = new Intent(PastResultActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close this activity
        });
    }
}
