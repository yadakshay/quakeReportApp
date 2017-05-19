package com.example.android.quakereport;

/**
 * Created by Akshay on 06-05-2017.
 */

public class eqevent {

    private String mPlace, mUrl;
    Double mMag;
    long  mDate;

    // constructor
    public eqevent(Double Mag, String Place, long Df, String url){
        mMag = Mag;
        mPlace = Place;
        mDate = Df;
        mUrl = url;
    }

    // return methods :
    public Double getMag(){
        return mMag;
    }

    public String getPlace(){
        return mPlace;
    }

    public long getDate(){
        return mDate;
    }

    public String getUrl(){
        return mUrl;
    }
}
