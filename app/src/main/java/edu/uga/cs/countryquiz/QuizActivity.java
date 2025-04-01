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

        quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        // Fetch countries only once
        if (!quizViewModel.isCountryListLoaded()) {
            new RetrieveCountries(new CountryQuizData(this), countries -> {
                quizViewModel.setCountryList(countries);
                setupQuiz();
            }).execute();
        } else {
            setupQuiz(); // If already loaded, proceed with quiz setup
        }

        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        quizViewModel.getCompletedQuestions().observe(this, completed -> {
            progressText.setText(completed + " of 6");

            if (completed != null && completed == 6) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.quizContainer, new ResultsFragment())
                        .commit();
            }
        });
    }

    private void setupQuiz() {
        quizPagerAdapter = new QuizPagerAdapter(this);
        viewPager.setAdapter(quizPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText("Q" + (position + 1));
        }).attach();
    }
}
