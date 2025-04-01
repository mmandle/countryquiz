package edu.uga.cs.countryquiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION_NUMBER = "question_number";
    private static final String[] CONTINENTS = {"Oceania", "North America", "South America", "Europe", "Africa", "Asia"};

    private Button option1, option2, option3;
    private TextView questionText, resultText;
    private ImageView questionImage;
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

        questionImage = view.findViewById(R.id.questionImage);
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

        int questionNumber = getArguments() != null ? getArguments().getInt(ARG_QUESTION_NUMBER) : 1;

        // Use the cached country list from QuizViewModel
        List<Country> countries = quizViewModel.getCountryList();
        if (countries == null || countries.isEmpty()) {
            questionText.setText("No countries available in database.");
        } else {
            setupQuestion(countries);
        }

        return view;
    }

    private void setupQuestion(List<Country> countries) {
        Collections.shuffle(countries);
        Country selectedCountry = countries.get(0); // Pick the first random country

        correctAnswer = selectedCountry.getContinent();
        questionText.setText("Which continent is " + selectedCountry.getName() + " in?");
        questionImage.setImageResource(getFlagResource(selectedCountry.getName()));

        List<String> options = new ArrayList<>();
        options.add(correctAnswer);

        // Add two incorrect answers
        List<String> possibleWrongAnswers = new ArrayList<>();
        for (String continent : CONTINENTS) {
            if (!continent.equals(correctAnswer)) {
                possibleWrongAnswers.add(continent);
            }
        }
        Collections.shuffle(possibleWrongAnswers);
        options.add(possibleWrongAnswers.get(0));
        options.add(possibleWrongAnswers.get(1));

        Collections.shuffle(options);

        option1.setText(options.get(0));
        option2.setText(options.get(1));
        option3.setText(options.get(2));

        option1.setOnClickListener(this::checkAnswer);
        option2.setOnClickListener(this::checkAnswer);
        option3.setOnClickListener(this::checkAnswer);
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
        if (option1.getText().toString().equals(correctAnswer)) {
            option1.setBackgroundColor(Color.GREEN);
        } else {
            option1.setBackgroundColor(Color.RED);
        }
        if (option2.getText().toString().equals(correctAnswer)) {
            option2.setBackgroundColor(Color.GREEN);
        } else {
            option2.setBackgroundColor(Color.RED);
        }
        if (option3.getText().toString().equals(correctAnswer)) {
            option3.setBackgroundColor(Color.GREEN);
        } else {
            option3.setBackgroundColor(Color.RED);
        }
    }

    private int getFlagResource(String countryName) {
        String formattedName = countryName.toLowerCase().replace(" ", "_");
        return getResources().getIdentifier(formattedName, "drawable", getActivity().getPackageName());
    }
}
