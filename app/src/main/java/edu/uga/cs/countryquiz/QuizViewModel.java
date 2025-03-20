package edu.uga.cs.countryquiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class QuizViewModel extends ViewModel {

    private static final int TOTAL_QUESTIONS = 6;  // Total number of questions
    private final MutableLiveData<Integer> completedQuestions = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> correctAnswers = new MutableLiveData<>(0); // Track correct answers

    // Increment the count of completed questions
    public void incrementCompletedQuestions() {
        Integer current = completedQuestions.getValue();
        if (current != null && current < TOTAL_QUESTIONS) {
            completedQuestions.setValue(current + 1);
        }
    }

    // Increment the count of correct answers
    public void incrementCorrectAnswers() {
        Integer current = correctAnswers.getValue();
        if (current != null) {
            correctAnswers.setValue(current + 1);
        }
    }

    // Get the current number of completed questions
    public LiveData<Integer> getCompletedQuestions() {
        return completedQuestions;
    }

    // Get the current number of correct answers
    public LiveData<Integer> getCorrectAnswers() {
        return correctAnswers;
    }

    // Check if the quiz is completed (i.e., all questions are answered)
    public boolean isQuizCompleted() {
        return completedQuestions.getValue() != null && completedQuestions.getValue() == TOTAL_QUESTIONS;
    }

    // Reset the quiz progress (in case user wants to restart)
    public void resetQuiz() {
        completedQuestions.setValue(0);
        correctAnswers.setValue(0);
    }
}
