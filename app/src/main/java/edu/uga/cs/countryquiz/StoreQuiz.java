package edu.uga.cs.countryquiz;

import android.util.Log;

public class StoreQuiz extends AsyncTask<Quiz, Void> {

    private CountryQuizData countryQuizData;
    private static final String DEBUG_TAG = "StoreQuiz";

    public StoreQuiz(CountryQuizData countryQuizData) {
        this.countryQuizData = countryQuizData;
    }

    @Override
    protected Void doInBackground(Quiz... quizzes) {
        Quiz quiz = quizzes[0];

        if (quiz != null) {
            countryQuizData.open();  // Open database connection

            // Store quiz in the database
            countryQuizData.storeQuiz(quiz);
            Log.d(DEBUG_TAG, "Stored Quiz: " + quiz.getId());

            countryQuizData.close();  // Close the database connection
        } else {
            Log.e(DEBUG_TAG, "No quiz to store.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d(DEBUG_TAG, "Quiz storing task completed.");
    }
}
