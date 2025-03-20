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

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION_NUMBER = "question_number";
    private Button option1, option2, option3;
    private TextView resultText;
    private String correctAnswer;
    private boolean answered = false; // Ensures user can only answer once
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

        ImageView questionImage = view.findViewById(R.id.questionImage);
        TextView questionText = view.findViewById(R.id.questionText);
        option1 = view.findViewById(R.id.option1);
        option2 = view.findViewById(R.id.option2);
        option3 = view.findViewById(R.id.option3);
        resultText = new TextView(getActivity());

        // Initialize ViewModel
        quizViewModel = new ViewModelProvider(requireActivity()).get(QuizViewModel.class);

        // Adding resultText below buttons
        ViewGroup layout = (ViewGroup) view;
        layout.addView(resultText);
        resultText.setTextSize(18);
        resultText.setPadding(0, 20, 0, 0);
        resultText.setVisibility(View.GONE); // Hide initially

        int questionNumber = getArguments() != null ? getArguments().getInt(ARG_QUESTION_NUMBER) : 1;

        // Set question text, image, and correct answer
        switch (questionNumber) {
            case 1:
                questionText.setText("What is the capital of France?");
                questionImage.setImageResource(R.drawable.france);
                option1.setText("Paris");
                option2.setText("Berlin");
                option3.setText("Madrid");
                correctAnswer = "Paris";
                break;
            case 2:
                questionText.setText("Which country has the Great Wall?");
                questionImage.setImageResource(R.drawable.china);
                option1.setText("Japan");
                option2.setText("China");
                option3.setText("South Korea");
                correctAnswer = "China";
                break;
            case 3:
                questionText.setText("Which country is known for pizza?");
                questionImage.setImageResource(R.drawable.italy);
                option1.setText("France");
                option2.setText("Italy");
                option3.setText("Spain");
                correctAnswer = "Italy";
                break;
            case 4:
                questionText.setText("Which country has kangaroos?");
                questionImage.setImageResource(R.drawable.australia);
                option1.setText("Australia");
                option2.setText("Canada");
                option3.setText("Brazil");
                correctAnswer = "Australia";
                break;
            case 5:
                questionText.setText("Where is the Amazon Rainforest?");
                questionImage.setImageResource(R.drawable.brazil);
                option1.setText("India");
                option2.setText("Brazil");
                option3.setText("Mexico");
                correctAnswer = "Brazil";
                break;
            case 6:
                questionText.setText("Which country has the Statue of Liberty?");
                questionImage.setImageResource(R.drawable.usa);
                option1.setText("USA");
                option2.setText("France");
                option3.setText("UK");
                correctAnswer = "USA";
                break;
        }

        // Set answer click listeners
        option1.setOnClickListener(this::checkAnswer);
        option2.setOnClickListener(this::checkAnswer);
        option3.setOnClickListener(this::checkAnswer);

        return view;
    }

    private void checkAnswer(View view) {
        if (answered) return; // Prevent multiple selections

        Button selectedButton = (Button) view;
        String selectedAnswer = selectedButton.getText().toString();
        answered = true;

        // Change button colors based on correctness
        if (selectedAnswer.equals(correctAnswer)) {
            selectedButton.setBackgroundColor(Color.GREEN);
            resultText.setText("Correct!");
            resultText.setTextColor(Color.GREEN);

            // Increment correct answers count
            quizViewModel.incrementCorrectAnswers();
        } else {
            selectedButton.setBackgroundColor(Color.RED);
            resultText.setText("Incorrect!");
            resultText.setTextColor(Color.RED);
        }

        // Show correct answer in green
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

        // Display result message
        resultText.setVisibility(View.VISIBLE);

        // Notify ViewModel that a question was answered
        quizViewModel.incrementCompletedQuestions();
    }

}
