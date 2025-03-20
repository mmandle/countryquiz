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

public class ResultsFragment extends Fragment {

    private QuizViewModel quizViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);

        TextView resultsText = view.findViewById(R.id.resultsText);
        TextView feedbackText = view.findViewById(R.id.feedbackText);
        Button homeButton = view.findViewById(R.id.homeButton);

        // Get ViewModel instance
        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        // Display the results
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

        // Home button returns to MainActivity
        homeButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            requireActivity().finish();
        });

        return view;
    }
}
