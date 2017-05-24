/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<eqevent>>{

    private static final int EARTHQUAKE_LOADER_ID = 1;

    public void sendIntent(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

    getLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
    //    new getData().execute(USGS_REQUEST_URL);

    }

    @Override
    public Loader<List<eqevent>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<eqevent>> loader, List<eqevent> earthquakes) {
        // If there is no result, do nothing.
        if (earthquakes == null) {
            return;
        }

        final List<eqevent> earthquakes2 = earthquakes;

        // Update the information displayed to the user.
        updateUi(earthquakes);
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick (AdapterView < ? > parent, View view, int position, long id){
                eqevent currentEvent = earthquakes2.get(position);
                String url = currentEvent.getUrl();
                sendIntent(url);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<List<eqevent>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.

    }

    private class getData extends AsyncTask<String, Void, ArrayList<eqevent>> {
        @Override
        protected ArrayList<eqevent> doInBackground(String... url) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (url.length < 1 || url[0] == null) {
                return null;
            }

            // Perform the HTTP request for earthquake data and process the response.
            // Event earthquake = Utils.fetchEarthquakeData(USGS_REQUEST_URL);
            ArrayList<eqevent> earthquake = QueryUtils.fetchEarthquakeData(url[0]);
            return earthquake;
        }

        @Override
        protected void onPostExecute(ArrayList<eqevent> result) {
            // If there is no result, do nothing.
            if (result == null) {
                return;
            }

            final ArrayList<eqevent> earthquakes = result;

            // Update the information displayed to the user.
            updateUi(result);
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick (AdapterView < ? > parent, View view, int position, long id){
                    eqevent currentEvent = earthquakes.get(position);
                    String url = currentEvent.getUrl();
                    sendIntent(url);
                }
            });
        }
    }


    private void updateUi(List<eqevent> earthquakes) {

    // Find a reference to the {@link ListView} in the layout
    ListView earthquakeListView = (ListView) findViewById(R.id.list);

    // Create a new {@link ArrayAdapter} of earthquakes
    CustomAdapter adapter = new CustomAdapter(this, (ArrayList<eqevent>) earthquakes);

    // Set the adapter on the {@link ListView}
    // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

}

}
