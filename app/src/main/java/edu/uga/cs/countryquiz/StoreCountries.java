package edu.uga.cs.countryquiz;

import android.util.Log;

import java.util.List;

public class StoreCountries extends AsyncTask<List<Country>, Void> {

    private CountryQuizData countryQuizData;
    private static final String DEBUG_TAG = "StoreCountries";
    private OnStoreCompleteListener listener; // Callback for completion

    public StoreCountries(CountryQuizData countryQuizData, OnStoreCompleteListener listener) {
        this.countryQuizData = countryQuizData;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(List<Country>... lists) {
        List<Country> countries = lists[0];

        if (countries != null && !countries.isEmpty()) {
            countryQuizData.open();  // Open database connection

            for (Country country : countries) {
                countryQuizData.storeCountry(country);
                Log.d(DEBUG_TAG, "Stored Country: " + country.getName());
            }

            countryQuizData.close();  // Close the database connection
        } else {
            Log.e(DEBUG_TAG, "No countries to store.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        Log.d(DEBUG_TAG, "Countries storing task completed.");
        if (listener != null) {
            listener.onStoreComplete();
        }
    }

    public interface OnStoreCompleteListener {
        void onStoreComplete();
    }
}

