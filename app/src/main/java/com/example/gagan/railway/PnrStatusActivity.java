package com.example.gagan.railway;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

public class PnrStatusActivity extends AppCompatActivity {

    final String TAG = "check";
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    public  static String REQUEST_URL;

    public static TextView pnr, doj,tp , chartPrepared,from,to,boardingPoint,reservationUpto,trainName, trainNumber,journeyClass;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pnr);
        pd = new ProgressDialog(PnrStatusActivity.this);
        pd.setMessage("Loading . . .");

        pnr = (TextView) findViewById(R.id.txtPnrCode);
        doj = (TextView) findViewById(R.id.txtDoj);
        tp= (TextView) findViewById(R.id.txtTotalPassengers);
        chartPrepared = (TextView) findViewById(R.id.txtChartPrepared);
        from = (TextView) findViewById(R.id.txtFromStation);
        to = (TextView) findViewById(R.id.txtToStation);
        boardingPoint = (TextView) findViewById(R.id.txtBoardingPoint);
        reservationUpto = (TextView) findViewById(R.id.txtReservationUpto);
        trainName = (TextView) findViewById(R.id.txtTrain);
        trainNumber = (TextView) findViewById(R.id.txtTrainCode);
        journeyClass = (TextView) findViewById(R.id.txtJourneyClass);


        REQUEST_URL=getIntent().getStringExtra("url");
        new ProcessJSON().execute(REQUEST_URL);
    }
    private class ProcessJSON extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... strings){
            String stream = null;
            String urlString = strings[0];

            HTTPDataHandler hh = new HTTPDataHandler();
             stream= hh.GetHTTPData(urlString);

            // Return the data from specified url
            return stream;
        }

        protected void onPostExecute(String stream){
            TextView pnr = (TextView) findViewById(R.id.txtPnrCode);
            doj = (TextView) findViewById(R.id.txtDoj);
            tp= (TextView) findViewById(R.id.txtTotalPassengers);
            chartPrepared = (TextView) findViewById(R.id.txtChartPrepared);
            from = (TextView) findViewById(R.id.txtFromStation);
            to = (TextView) findViewById(R.id.txtToStation);
            boardingPoint = (TextView) findViewById(R.id.txtBoardingPoint);
            reservationUpto = (TextView) findViewById(R.id.txtReservationUpto);
            trainName = (TextView) findViewById(R.id.txtTrain);
            trainNumber = (TextView) findViewById(R.id.txtTrainCode);
            journeyClass = (TextView) findViewById(R.id.txtJourneyClass);


            //..........Process JSON DATA................
            if(stream !=null){
                try{
                    // Get the full HTTP Data as JSONObject
                    JSONObject reader= new JSONObject(stream);

                String pnrr=reader.getString("pnr");
                String dojj=reader.getString("doj");
                int tpp=reader.getInt("total_passengers");
                    String s=String.valueOf(tpp);
                boolean chartPreparedd=reader.getBoolean("chart_prepared");
                    String s1=String.valueOf(chartPreparedd);

                JSONObject baseJsonResponse2=reader.getJSONObject("from_station");
                String fromm=baseJsonResponse2.getString("name");

                JSONObject baseJsonResponse3=reader.getJSONObject("to_station");
                String too=baseJsonResponse3.getString("name");

                JSONObject baseJsonResponse4=reader.getJSONObject("boarding_point");
                String boardingPointt=baseJsonResponse4.getString("name");

                JSONObject baseJsonResponse5=reader.getJSONObject("reservation_upto");
                String reservationUptoo=baseJsonResponse5.getString("name");

                JSONObject baseJsonResponse6=reader.getJSONObject("train");
                String trainNamee = baseJsonResponse6.getString("name");
                String traintNumberr = baseJsonResponse6.getString("number");

                JSONObject baseJsonResponse7=reader.getJSONObject("journey_class");
                String journeyClasss=baseJsonResponse7.getString("name");


                    pnr.setText(pnr.getText()+pnrr);
        doj.setText(doj.getText()+dojj);
       tp.setText(tp.getText() + s);
       chartPrepared.setText(chartPrepared.getText()+s1);
        from.setText(from.getText()+fromm);
        to.setText(to.getText()+too);
        boardingPoint.setText(boardingPoint.getText()+boardingPointt);
        reservationUpto.setText(reservationUpto.getText()+reservationUptoo);
        trainName.setText(trainName.getText()+trainNamee);
        trainNumber.setText(trainNumber.getText()+traintNumberr);
        journeyClass.setText(journeyClass.getText()+journeyClasss);



                }catch(JSONException e){
                    e.printStackTrace();
                }

            } // if statement end
        } // onPostExecute() end
    } // ProcessJSON class end

}
