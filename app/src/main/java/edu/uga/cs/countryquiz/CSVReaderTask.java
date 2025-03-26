package edu.uga.cs.countryquiz;

import android.content.Context;
import android.util.Log;
import com.opencsv.CSVReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderTask extends AsyncTask<Void, List<Country>> {
    private static final String TAG = "CSVReaderTask";
    private OnCSVReadListener listener;
    private Context context;

    public CSVReaderTask(Context context, OnCSVReadListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected List<Country> doInBackground(Void... voids) {
        List<Country> countries = new ArrayList<>();
        try {
            // Open the correct CSV file from assets
            InputStream inputStream = context.getAssets().open("country_continent.csv");
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            String[] nextRow;

            while ((nextRow = reader.readNext()) != null) {
                if (nextRow.length >= 2) {
                    String countryName = nextRow[0].trim();
                    String continent = nextRow[1].trim();

                    countries.add(new Country(countryName, continent));
                }
            }

            reader.close();
        } catch (Exception e) {
            Log.e(TAG, "Error reading CSV file: " + e.getMessage(), e);
        }
        return countries;
    }

    @Override
    protected void onPostExecute(List<Country> countries) {
        if (listener != null) {
            listener.onCSVRead(countries);
        }
    }

    public interface OnCSVReadListener {
        void onCSVRead(List<Country> countries);
    }
}
