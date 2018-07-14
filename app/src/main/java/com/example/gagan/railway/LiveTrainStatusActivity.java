package com.example.gagan.railway;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.TextView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LiveTrainStatusActivity extends AppCompatActivity {

    final String TAG = "check";
    public static final String LOG_TAG = LiveTrainStatusActivity.class.getSimpleName();
    public  static String REQUEST_URL;
    private TextView trainName,trainCode,stationName,position,arrival,departure,lateMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_livestatus);


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
            trainName = (TextView) findViewById(R.id.trainCode);
//            trainCode = (TextView) findViewById(R.id.trainName);
            lateMinute = (TextView) findViewById(R.id.stationCode);
            stationName = (TextView) findViewById(R.id.txtStation);
            arrival = (TextView) findViewById(R.id.SChar);
            departure = (TextView) findViewById(R.id.schdep);
            position = (TextView) findViewById(R.id.halt);


            //..........Process JSON DATA................
            if (stream != null) {
                try {
                    JSONObject jsonObj = new JSONObject(stream);
                    String positionn=jsonObj.getString("position");
                    JSONObject train = jsonObj.getJSONObject("train");
                    String trainNamee = train.getString("name");
//                    String trainNumberr = train.getString("number");

                    JSONArray route = jsonObj.getJSONArray("route");
                    // looping through All routes
                    for (int i = 0; i < 1; i++) {
                        JSONObject c = route.getJSONObject(0);

                        String arrivall = c.getString("scharr");
                        String departuree = c.getString("schdep");
                        int lateMinutee=c.getInt("latemin");
                        String s=String.valueOf(lateMinutee);

                        JSONObject cinner = c.getJSONObject("station");
                        String stationNamee = cinner.getString("name");


                        trainName.setText(trainName.getText() + trainNamee);
//                        trainCode.setText(trainCode.getText() + trainNumberr);
                        position.setText(position.getText()+positionn);
                        stationName.setText(stationName.getText()+stationNamee);
                        arrival.setText(arrival.getText() + arrivall);
                        departure.setText(departure.getText() + departuree);
                        lateMinute.setText(lateMinute.getText()+s);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        } // ProcessJSON class end


    }
}

