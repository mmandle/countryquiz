package edu.uga.cs.countryquiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment {

    private static final String ARG_QUESTION_NUMBER = "question_number";

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
        Button option1 = view.findViewById(R.id.option1);
        Button option2 = view.findViewById(R.id.option2);
        Button option3 = view.findViewById(R.id.option3);

        int questionNumber = getArguments() != null ? getArguments().getInt(ARG_QUESTION_NUMBER) : 1;

        // Set question text & image based on question number
        switch (questionNumber) {
            case 1:
                questionText.setText("What is the capital of France?");
                questionImage.setImageResource(R.drawable.france); // Replace with actual image
                option1.setText("Paris");
                option2.setText("Berlin");
                option3.setText("Madrid");
                break;
            case 2:
                questionText.setText("Which country has the Great Wall?");
                questionImage.setImageResource(R.drawable.china);
                option1.setText("Japan");
                option2.setText("China");
                option3.setText("South Korea");
                break;
            case 3:
                questionText.setText("Which country is known for pizza?");
                questionImage.setImageResource(R.drawable.italy);
                option1.setText("France");
                option2.setText("Italy");
                option3.setText("Spain");
                break;
            case 4:
                questionText.setText("Which country has kangaroos?");
                questionImage.setImageResource(R.drawable.australia);
                option1.setText("Australia");
                option2.setText("Canada");
                option3.setText("Brazil");
                break;
            case 5:
                questionText.setText("Where is the Amazon Rainforest?");
                questionImage.setImageResource(R.drawable.brazil);
                option1.setText("India");
                option2.setText("Brazil");
                option3.setText("Mexico");
                break;
            case 6:
                questionText.setText("Which country has the Statue of Liberty?");
                questionImage.setImageResource(R.drawable.usa);
                option1.setText("USA");
                option2.setText("France");
                option3.setText("UK");
                break;
        }

        // Handle button clicks (for now, just show a toast)
        View.OnClickListener answerClickListener = v -> {
            Button selectedButton = (Button) v;
            String answer = selectedButton.getText().toString();
            Toast.makeText(getActivity(), "You chose: " + answer, Toast.LENGTH_SHORT).show();
        };

        option1.setOnClickListener(answerClickListener);
        option2.setOnClickListener(answerClickListener);
        option3.setOnClickListener(answerClickListener);

        return view;
    }
}
