package com.example.gagan.railway;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StationNameToCodeActivity extends AppCompatActivity {
    private TextView stationName,stationCode,longitude,latitude;
    final String TAG = "check";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public  static String REQUEST_URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_station_name_to_code);


        REQUEST_URL=getIntent().getStringExtra("url");
        new ProcessJSON().execute(REQUEST_URL);
    }
    private class ProcessJSON extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings) {
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
            stream = hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream) {

            stationCode = (TextView) findViewById(R.id.mStationName);
            stationName = (TextView) findViewById(R.id.mStationCode);
            longitude = (TextView) findViewById(R.id.mLongitude);
            latitude = (TextView) findViewById(R.id.mLatitude);


            //..........Process JSON DATA................
            if (stream != null) {
                try {
                    JSONObject jsonObj = new JSONObject(stream);


                    JSONArray stations = jsonObj.getJSONArray("stations");
                    // looping through All routes
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = stations.getJSONObject(i);

                        String sCode = c.getString("code");
                        String sName = c.getString("name");
                        double lng = c.getDouble("lng");
                        String s = String.valueOf(lng);
                        double lat = c.getInt("lat");
                        String s1 = String.valueOf(lat);




                        stationCode.setText(stationCode.getText() + sCode);
                        stationName.setText(stationName.getText() + sName);
                        longitude.setText(longitude.getText() + s);
                        latitude.setText(latitude.getText() + s1);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        } // ProcessJSON class end


    }
}
