package edu.uga.cs.countryquiz;

import android.os.AsyncTask;
import java.util.List;

public class RetrieveCountriesTask extends AsyncTask<Void, Void, List<String>> {
    private CountryQuizData dbHelper;
    private OnCountriesRetrievedListener listener;

    public RetrieveCountriesTask(CountryQuizData dbHelper, OnCountriesRetrievedListener listener) {
        this.dbHelper = dbHelper;
        this.listener = listener;
    }

    @Override
    protected List<String> doInBackground(Void... voids) {
        dbHelper.open();
        List<String> countries = dbHelper.getAllCountries();
        dbHelper.close();
        return countries;
    }

    @Override
    protected void onPostExecute(List<String> countries) {
        if (listener != null) {
            listener.onCountriesRetrieved(countries);
        }
    }

    public interface OnCountriesRetrievedListener {
        void onCountriesRetrieved(List<String> countries);
    }
}
