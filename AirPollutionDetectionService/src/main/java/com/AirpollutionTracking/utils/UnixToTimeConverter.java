package com.AirpollutionTracking.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UnixToTimeConverter {

    public  String convertUnixToTime(long unixTimestamp) {
        // Create a Date object from Unix timestamp
        Date date = new Date(unixTimestamp * 1000L); // Multiply by 1000 to convert from seconds to milliseconds

        // Format the date and time using a SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = sdf.format(date);

        return formattedDateTime;
    }
}