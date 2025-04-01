package edu.uga.cs.countryquiz;

import android.util.Log;
import java.util.List;

public class RetrieveCountries extends AsyncTask<Void, List<Country>> {

    private CountryQuizData countryQuizData;
    private static final String DEBUG_TAG = "RetrieveCountries";
    private OnCountriesRetrievedListener listener;

    public interface OnCountriesRetrievedListener {
        void onCountriesRetrieved(List<Country> countries);
    }

    public RetrieveCountries(CountryQuizData countryQuizData, OnCountriesRetrievedListener listener) {
        this.countryQuizData = countryQuizData;
        this.listener = listener;
    }

    @Override
    protected List<Country> doInBackground(Void... voids) {
        countryQuizData.open(); // Open database connection
        List<Country> countries = countryQuizData.retrieveAllCountries();
        countryQuizData.close(); // Close database connection
        return countries;
    }

    @Override
    protected void onPostExecute(List<Country> countries) {
        Log.d(DEBUG_TAG, "Retrieved " + (countries != null ? countries.size() : 0) + " countries.");
        if (listener != null) {
            listener.onCountriesRetrieved(countries); // Pass data to the listener
        }
    }
}
