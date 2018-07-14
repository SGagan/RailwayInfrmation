package com.example.gagan.railway;

public class Event {


    public String trainName;
    public String trainNumber;


    public String pnr;


    public  String doj;


    public  int totalPassengers;
    public boolean chartPrepared;
    public String fromStation;
    public String toStation;
    public String boardingPoint;
    public String reservationUpto;
    public String journeyClass;
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
    //for pnr
    public Event(String mPnr,String mDoj,int mTP,boolean mChartPrepared,String mFromStation,String mToStation,String mBoardingPoint,String mReservationUpto,String mTrainName,String mTrainNumber,String mJourneyClass){
        pnr=mPnr;
        doj=mDoj;
        totalPassengers=mTP;
        chartPrepared=mChartPrepared;
        fromStation=mFromStation;
        toStation=mToStation;
        boardingPoint=mBoardingPoint;
        reservationUpto=mReservationUpto;
        trainName=mTrainName;
        trainNumber=mTrainNumber;
        journeyClass=mJourneyClass;
}
}

