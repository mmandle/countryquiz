package edu.uga.cs.countryquiz;

import android.util.Log;
import java.util.List;

public class RetrieveQuizzes extends AsyncTask<Void, List<Quiz>> {

    private CountryQuizData countryQuizData;
    private static final String DEBUG_TAG = "RetrieveQuizzes";
    private OnQuizzesRetrievedListener listener;

    public interface OnQuizzesRetrievedListener {
        void onQuizzesRetrieved(List<Quiz> quizzes);
    }

    public RetrieveQuizzes(CountryQuizData countryQuizData, OnQuizzesRetrievedListener listener) {
        this.countryQuizData = countryQuizData;
        this.listener = listener;
    }

    @Override
    protected List<Quiz> doInBackground(Void... voids) {
        countryQuizData.open(); // Open database connection
        List<Quiz> quizzes = countryQuizData.retrieveAllQuizzes();
        countryQuizData.close(); // Close database connection
        return quizzes;
    }

    @Override
    protected void onPostExecute(List<Quiz> quizzes) {
        Log.d(DEBUG_TAG, "Retrieved " + (quizzes != null ? quizzes.size() : 0) + " quizzes.");
        if (listener != null) {
            listener.onQuizzesRetrieved(quizzes); // Pass data to the listener
        }
    }
}
