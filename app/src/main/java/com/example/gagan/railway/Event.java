package com.example.gagan.railway;

public class Event {


    public final String trainName;


    public final String trainNumber;


    public  String pnr;


    public  String doj;


    public  int totalPassengers;
    public  String days;
    public  String runs;


    public Event(String mTrainName, String mTrainNumber, String mDays,String mRuns) {
        trainName=mTrainName;
        trainNumber=mTrainNumber;
        days=mDays;
        runs=mRuns;
    }
    public Event(String mPnr, String mDoj, int mTP,String mTrainName,String mTrainNumber) {
        pnr=mPnr;
        doj=mDoj;
        totalPassengers=mTP;
        trainName=mTrainName;
        trainNumber=mTrainNumber;

    }

}

