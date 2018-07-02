package com.example.gagan.railway;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

public class Display2 extends AppCompatActivity {

    final String TAG = "check";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public  static String USGS_REQUEST_URL;

    public static TextView pnr, doj,tp , trainnameT, trainnumberT;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display2);
        pd = new ProgressDialog(Display2.this);
        pd.setMessage("Loading . . .");

        pnr = (TextView) findViewById(R.id.pnr);
        doj = (TextView) findViewById(R.id.doj);
        trainnameT = (TextView) findViewById(R.id.trainname);
        trainnumberT = (TextView) findViewById(R.id.trainnumber);
        tp = (TextView) findViewById(R.id.tp);

        USGS_REQUEST_URL=getIntent().getStringExtra("url");
        RailwayAsyncTask task = new RailwayAsyncTask();
        task.execute();
        }
    private static void updateUi(Event railway) {
        trainnameT.setText(railway.trainName.toString());
        trainnumberT.setText(railway.trainNumber.toString());
        doj.setText(railway.doj.toString());
        pnr.setText(railway.pnr.toString());
        tp.setText(railway.totalPassengers);


    }


    private static class RailwayAsyncTask extends AsyncTask<URL, Void, Event> {

        @Override
        protected Event doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(USGS_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                assert url != null;
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {

            }

            // Extract relevant fields from the JSON response and create an {@link Event} object
            Event railway = extractFeatureFromJson(jsonResponse);

            // Return the {@link Event} object as the result fo the {@link TsunamiAsyncTask}
            return railway;
        }


        @Override
        protected void onPostExecute(Event data) {
            if (data == null) {
                return;
            }

            updateUi(data);
        }


        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }


        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } catch (IOException e) {

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    // function must handle java.io.IOException here
                    inputStream.close();
                }
            }
            return jsonResponse;
        }


        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }


        private Event extractFeatureFromJson(String railwayJSON) {
            try {
                JSONObject baseJsonResponse = new JSONObject(railwayJSON);

                String traintname = baseJsonResponse.getString("name");
                String traintnumber = baseJsonResponse.getString("number");
                String pnr=baseJsonResponse.getString("pnr");
                int tp=baseJsonResponse.getInt("total_passengers");
                String doj=baseJsonResponse.getString("doj");
                // Create a new {@link Event} object
                return new Event(pnr,doj,tp,traintname,traintnumber);



            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the Railway JSON results", e);
            }
            return null;
        }




    }
}
