package com.ali.earthquake.Util;

import java.util.Date;
import java.util.Random;

public class Constant {
    public static final String URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/all_week.geojson";
    public static final int LIMIT = 30;

    public static String getDate(Long time) {
        java.text.DateFormat dFormat = java.text.DateFormat.getDateInstance();
        String dateFormated = dFormat.format(new Date(time));
        return dateFormated;
    }

    public static int randomInt(int max, int min) {

        return new Random().nextInt((max - min)) + min;
    }
}
