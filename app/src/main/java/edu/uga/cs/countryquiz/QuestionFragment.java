package edu.uga.cs.countryquiz;

import android.graphics.Color;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION_NUMBER = "question_number";
    private static final String[] CONTINENTS = {"Oceania", "North America", "South America", "Europe", "Africa", "Asia"};

    private Button option1, option2, option3;
    private TextView questionText, resultText;
    private String correctAnswer;
    private boolean answered = false;
    private QuizViewModel quizViewModel;

    public static QuestionFragment newInstance(int questionNumber) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_QUESTION_NUMBER, questionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        questionText = view.findViewById(R.id.questionText);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        resultText = new TextView(getActivity());

        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        ViewGroup layout = (ViewGroup) view;
        layout.addView(resultText);
        resultText.setTextSize(18);
        resultText.setPadding(0, 20, 0, 0);
        resultText.setVisibility(View.GONE);

        int questionNumber = getArguments() != null ? getArguments().getInt(ARG_QUESTION_NUMBER, 0) : 0;
        List<Country> selectedCountries = quizViewModel.getCountryList();  // Corrected to get the full list

        if (selectedCountries == null || selectedCountries.isEmpty() || questionNumber >= selectedCountries.size()) {
            questionText.setText("No valid question available.");
        } else {
            setupQuestion(selectedCountries.get(questionNumber));
        }

        return view;
    }

    private void setupQuestion(Country selectedCountry) {
        correctAnswer = selectedCountry.getContinent();
        questionText.setText("Which continent is " + selectedCountry.getName() + " in?");

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        // Add two incorrect answers
        List<String> possibleWrongAnswers = new ArrayList<>();
        for (String continent : CONTINENTS) {
            if (!continent.equals(correctAnswer)) {
                possibleWrongAnswers.add(continent);
            }
        }

        Random random = new Random();
        options.add(possibleWrongAnswers.remove(random.nextInt(possibleWrongAnswers.size())));
        options.add(possibleWrongAnswers.remove(random.nextInt(possibleWrongAnswers.size())));

        // Shuffle answer choices manually
        List<Button> buttons = new ArrayList<>();
        buttons.add(option1);
        buttons.add(option2);
        buttons.add(option3);

        for (int i = 0; i < buttons.size(); i++) {
            int randIndex = random.nextInt(buttons.size());
            Button temp = buttons.get(i);
            buttons.set(i, buttons.get(randIndex));
            buttons.set(randIndex, temp);
        }

        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).setText(options.get(i));
            buttons.get(i).setOnClickListener(this::checkAnswer);
        }
    }

    private void checkAnswer(View view) {
        if (answered) return;

        Button selectedButton = (Button) view;
        String selectedAnswer = selectedButton.getText().toString();
        answered = true;

        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setBackgroundColor(Color.GREEN);
            resultText.setText("Correct!");
            resultText.setTextColor(Color.GREEN);
            quizViewModel.incrementCorrectAnswers();
        } else {
            selectedButton.setBackgroundColor(Color.RED);
            resultText.setText("Incorrect!");
            resultText.setTextColor(Color.RED);
        }

        highlightCorrectAnswer();
        resultText.setVisibility(View.VISIBLE);
        quizViewModel.incrementCompletedQuestions();
    }

    private void highlightCorrectAnswer() {
        if (option1.getText().toString().equals(correctAnswer)) option1.setBackgroundColor(Color.GREEN);
        if (option2.getText().toString().equals(correctAnswer)) option2.setBackgroundColor(Color.GREEN);
        if (option3.getText().toString().equals(correctAnswer)) option3.setBackgroundColor(Color.GREEN);
    }
}
