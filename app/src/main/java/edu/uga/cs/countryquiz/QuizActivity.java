package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class QuizActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private QuizPagerAdapter quizPagerAdapter;
    private TextView progressText;
    private QuizViewModel quizViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        Button exitButton = findViewById(R.id.exitButton);
        progressText = findViewById(R.id.progressText);

        // Set up ViewModel
        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // Set up ViewPager2 with the adapter
        quizPagerAdapter = new QuizPagerAdapter(this);
        viewPager.setAdapter(quizPagerAdapter);

        // Link TabLayout with ViewPager2 for page indicators
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText("Q" + (position + 1)); // Label each tab with Q1, Q2, Q3...
        }).attach();

        // Set Exit Button functionality
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        // Observe the number of completed questions
        quizViewModel.getCompletedQuestions().observe(this, completed -> {
            // Update the progress text
            if (completed != null) {
                progressText.setText(completed + " of 6");
            }

            // If all 6 questions are answered, show the ResultsFragment
            if (completed != null && completed == 6) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.quizContainer, new ResultsFragment())
                        .commit();
            }
        });
    }
}
