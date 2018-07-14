package com.example.gagan.railway;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class TrainNameActivity extends AppCompatActivity {

    final String TAG = "check";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public  static String REQUEST_URL;
    private TextView trainName,trainNumber,day,runs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_train_name);


        REQUEST_URL=getIntent().getStringExtra("url");
        new ProcessJSON().execute(REQUEST_URL);
//        new ProcessJSON().execute();
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
            trainName = (TextView) findViewById(R.id.mStationName);
            trainNumber = (TextView) findViewById(R.id.mStationCode);
            day = (TextView) findViewById(R.id.mLongitude);
            runs=(TextView)findViewById(R.id.mLatitude);


            //..........Process JSON DATA................
            if (stream != null) {
                try {
                    JSONObject jsonObj = new JSONObject(stream);
                    JSONObject train = jsonObj.getJSONObject("train");
                    String trainNamee = train.getString("name");
                    String trainNumberr = train.getString("number");

                    JSONArray days = jsonObj.getJSONArray("days");
                    // looping through All routes
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = days.getJSONObject(0);

                        String code = c.getString("code");
                        String runss = c.getString("runs");





                        trainName.setText(trainName.getText() + trainNamee);
                        trainNumber.setText(trainNumber.getText() + trainNumberr);
                        day.setText(day.getText() + code);
                        runs.setText(runs.getText() + runss);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        } // ProcessJSON class end


    }
}

