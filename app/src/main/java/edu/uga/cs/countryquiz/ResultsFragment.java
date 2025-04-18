package edu.uga.cs.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultsFragment extends Fragment {

    private QuizViewModel quizViewModel;
    private CountryQuizData countryQuizData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        TextView resultsText = view.findViewById(R.id.resultsText);
        TextView feedbackText = view.findViewById(R.id.feedbackText);
        Button homeButton = view.findViewById(R.id.homeButton);

        // Get ViewModel instance
        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        // Retrieve the user's score
        int score = quizViewModel.getCorrectAnswers().getValue() != null ? quizViewModel.getCorrectAnswers().getValue() : 0;
        resultsText.setText("You scored " + score + " out of 6!");

        // Set feedback text based on the score
        String feedbackMessage;
        if (score == 0) {
            feedbackMessage = "Yikes...";
        } else if (score <= 2) {
            feedbackMessage = "Go study next time";
        } else if (score <= 4) {
            feedbackMessage = "Not the worst thing I've ever seen";
        } else if (score == 5) {
            feedbackMessage = "Excelsior!";
        } else {
            feedbackMessage = "Okay Einstein";
        }
        feedbackText.setText(feedbackMessage);

        // Store quiz results in the database
        storeQuizResult(score);

        // Home button returns to MainActivity
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }

    /**
     * Stores the quiz result in the database.
     */
    private void storeQuizResult(int score) {
        countryQuizData = new CountryQuizData(requireContext());

        // Get the current date in a readable format
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

        // Create a new Quiz object
        Quiz quiz = new Quiz(currentDate, score);

        // Store the quiz asynchronously using StoreQuiz
        new StoreQuiz(countryQuizData).execute(quiz);
    }
}
