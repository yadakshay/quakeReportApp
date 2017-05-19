package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Akshay on 07-05-2017.
 */


public class CustomAdapter extends ArrayAdapter<eqevent> {

    private static final String LOCATION_SEPARATOR = "of";

    public CustomAdapter(Context context, ArrayList<eqevent> eventArray){
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, eventArray);
    }

    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);

    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        eqevent currentEvent = getItem(position);

        TextView magnitudedView = (TextView) listItemView.findViewById(R.id.magDisplay);
        double mag = currentEvent.getMag();
        DecimalFormat magFormat = new DecimalFormat("0.00");
        String magOutput = magFormat.format(mag);
        magnitudedView.setText(magOutput);

        TextView locationView = (TextView) listItemView.findViewById(R.id.placeDisplay);
        TextView locationView1 = (TextView) listItemView.findViewById(R.id.placeDisplay1);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudedView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEvent.getMag());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        String place = currentEvent.getPlace();

        if(place.contains(LOCATION_SEPARATOR)) {
            int pos = place.indexOf(LOCATION_SEPARATOR);
            locationView.setText(place.substring(0,(pos+2)));
            locationView1.setText(place.substring(pos+2));
        }else{
            locationView.setText(R.string.near_the);
            locationView1.setText(place);
        }



        TextView dateView   = (TextView) listItemView.findViewById(R.id.dateDisplay);

        Date dateObject = new Date(currentEvent.getDate());
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        String dateForDisplay = dateFormat.format(dateObject);
        dateView.setText(dateForDisplay);


        TextView timeView   = (TextView) listItemView.findViewById(R.id.time);
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        String timeForDisplay = timeFormat.format(dateObject);
        timeView.setText(timeForDisplay);


        return listItemView;

    }

}
