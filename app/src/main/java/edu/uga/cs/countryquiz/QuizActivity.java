package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        if (quizViewModel.getCountryList() != null) {
            List<Country> shuffledCountries = new ArrayList<>(quizViewModel.getCountryList());
            Random random = new Random();

            // Fisher-Yates shuffle using Random
            for (int i = shuffledCountries.size() - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                Country temp = shuffledCountries.get(i);
                shuffledCountries.set(i, shuffledCountries.get(j));
                shuffledCountries.set(j, temp);
            }

            quizViewModel.setCountryList(shuffledCountries); // Store the shuffled list
        }

        quizPagerAdapter = new QuizPagerAdapter(this);
        viewPager.setAdapter(quizPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText("Q" + (position + 1));
        }).attach();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save quiz progress (current question and completed questions)
        SharedPreferences prefs = getSharedPreferences("quiz_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("current_question", viewPager.getCurrentItem()); // Save the current question
        editor.putInt("completed_questions", quizViewModel.getCompletedQuestions().getValue() != null ? quizViewModel.getCompletedQuestions().getValue() : 0); // Save completed questions
        editor.putInt("correct_answers", quizViewModel.getCorrectAnswers().getValue() != null ? quizViewModel.getCorrectAnswers().getValue() : 0); // Save correct answers
        editor.apply();
    }



}
