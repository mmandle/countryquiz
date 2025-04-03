package edu.uga.cs.countryquiz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class QuizViewModel extends ViewModel {

    private static final int TOTAL_QUESTIONS = 6;
    private final MutableLiveData<Integer> completedQuestions = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> correctAnswers = new MutableLiveData<>(0);

    private List<Country> countryList;  // Store the retrieved country list

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

    // Check if the quiz is completed
    public boolean isQuizCompleted() {
        return completedQuestions.getValue() != null && completedQuestions.getValue() == TOTAL_QUESTIONS;
    }

    // Reset the quiz
    public void resetQuiz() {
        completedQuestions.setValue(0);
        correctAnswers.setValue(0);
    }

    // Store the retrieved country list
    public void setCountryList(List<Country> countries) {
        this.countryList = countries;
    }

    // Retrieve the stored country list
    public List<Country> getCountryList() {
        return countryList;
    }

    // Check if the country list is already loaded
    public boolean isCountryListLoaded() {
        return countryList != null && !countryList.isEmpty();
    }

    // Add a method to allow updating the completed questions (needed for restoring state)
    public void setCompletedQuestions(int completed) {
        completedQuestions.setValue(completed);
    }

    // Add a method to allow updating the correct answers (needed for restoring state)
    public void setCorrectAnswers(int correct) {
        correctAnswers.setValue(correct);
    }
}
