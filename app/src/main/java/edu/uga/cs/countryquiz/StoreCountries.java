package edu.uga.cs.countryquiz;

import android.util.Log;

import java.util.List;

public class StoreCountries extends AsyncTask<List<Country>, Void> {

    private CountryQuizData countryQuizData;
    private static final String DEBUG_TAG = "StoreCountries";

    public StoreCountries(CountryQuizData countryQuizData) {
        this.countryQuizData = countryQuizData;
    }

    @Override
    protected Void doInBackground(List<Country>... lists) {
        List<Country> countries = lists[0];

        if (countries != null && !countries.isEmpty()) {
            // Begin database transaction for storing countries
            countryQuizData.open();  // Open database connection

            for (Country country : countries) {
                // Stored country into the database
                countryQuizData.storeCountry(country);
                Log.d(DEBUG_TAG, "Stored Country: " + country.getName());
            }

            countryQuizData.close();  // Close the database connection after storing task
        } else {
            Log.e(DEBUG_TAG, "No countries to store.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // This method will be called after the storing task completes.
        Log.d(DEBUG_TAG, "Countries storing task completed.");
    }
}
