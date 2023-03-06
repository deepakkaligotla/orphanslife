package com.kaligotla.oms.Essentials;

public class CustomDateFormate {

    public static String convert(String date) {
        if (date != null) {
            date = date.replace( "\"", "" );
            date = date.replace( "T", " " );
            date = date.replace( ".000Z", "" );
            date = date.replace( " 00:00:00", "" );
            return date;

        } else {
            return date;
        }
    }
}
