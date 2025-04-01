package edu.uga.cs.countryquiz;

import android.util.Log;

import java.util.List;

public class InsertCountries extends AsyncTask<List<Country>, Void> {

    private CountryQuizData countryQuizData;
    private static final String DEBUG_TAG = "InsertCountries";

    public InsertCountries(CountryQuizData countryQuizData) {
        this.countryQuizData = countryQuizData;
    }

    @Override
    protected Void doInBackground(List<Country>... lists) {
        List<Country> countries = lists[0];

        if (countries != null && !countries.isEmpty()) {
            // Begin database transaction for inserting countries
            countryQuizData.open();  // Open database connection

            for (Country country : countries) {
                // Insert country into the database
                countryQuizData.storeCountry(country);
                Log.d(DEBUG_TAG, "Inserted Country: " + country.getName());
            }

            countryQuizData.close();  // Close the database connection after insertion
        } else {
            Log.e(DEBUG_TAG, "No countries to insert.");
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        // This method will be called after the insertion task completes.
        Log.d(DEBUG_TAG, "Countries insertion completed.");
    }
}
