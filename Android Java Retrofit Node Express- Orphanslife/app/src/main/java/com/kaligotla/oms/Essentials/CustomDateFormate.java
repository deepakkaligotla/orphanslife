package com.kaligotla.oms.Essentials;

public class CustomDateFormate {

    public static String convert(String date) {
        if (date != null) {
            date = date.replace( "T", " " );
            date = date.replace( ".000Z", "" );
            date = date.substring(0,10);
            return date;

        } else {
            return date;
        }
    }
}
