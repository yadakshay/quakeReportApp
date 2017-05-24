package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akshay on 24-05-2017.
 */



public class EarthquakeLoader extends AsyncTaskLoader<List<eqevent>> {

    /** Tag for log messages */
    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    final String url;
    public EarthquakeLoader(Context context, String url1) {
        super(context);
        // TODO: Finish implementing this constructor
        url = url1;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<eqevent> loadInBackground() {

        // Don't perform the request if there are no URLs, or the first URL is null.
        if (url == null) {
            return null;
        }

        // Perform the HTTP request for earthquake data and process the response.
        // Event earthquake = Utils.fetchEarthquakeData(USGS_REQUEST_URL);
        ArrayList<eqevent> earthquake = QueryUtils.fetchEarthquakeData(url);
        return earthquake;
    }

}
