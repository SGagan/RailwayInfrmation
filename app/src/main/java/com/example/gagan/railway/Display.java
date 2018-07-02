package com.example.gagan.railway;

import android.app.ProgressDialog;
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

public class Display extends AppCompatActivity {

    final String TAG = "check";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public  String USGS_REQUEST_URL;

   public TextView days, runst, trainnameT, trainnumberT;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        pd = new ProgressDialog(Display.this);
        pd.setMessage("Loading . . .");

        days = (TextView) findViewById(R.id.days);
        runst = (TextView) findViewById(R.id.runs);
        trainnameT = (TextView) findViewById(R.id.trainname);
        trainnumberT = (TextView) findViewById(R.id.trainnumber);

USGS_REQUEST_URL=getIntent().getStringExtra("url");
        RailwayAsyncTask task = new RailwayAsyncTask();
        task.execute();
    }
    private void updateUi(Event railway) {
        trainnameT.setText(railway.trainName.toString());
        trainnumberT.setText(railway.trainNumber.toString());
        days.setText(railway.days.toString());
        runst.setText(railway.runs.toString());


    }
    private class RailwayAsyncTask extends AsyncTask<URL, Void, Event> {

        @Override
        protected Event doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(USGS_REQUEST_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
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
                JSONObject jsonObject = baseJsonResponse.getJSONObject("train");
                String traintname = jsonObject.getString("name");
                String traintnumber = jsonObject.getString("number");

                JSONArray jsonarray = new JSONArray(jsonObject.getString("days"));
                for(int i=0; i < jsonarray.length(); i++) {

                    JSONObject jsonnobject = jsonarray.getJSONObject(i);
                    String runs = jsonnobject.getString("runs");
                    String day = jsonnobject.getString("code");

                    // Create a new {@link Event} object
                    return new Event(traintname, traintnumber, day,runs);


                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the Railway JSON results", e);
            }
            return null;
        }



    }
}
