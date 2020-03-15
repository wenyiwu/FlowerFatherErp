package com.flower.father.utils;

import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {
    public static Date stringToDate(String date) throws ParseException {
        if(StringUtils.isEmpty(date)) {
            return null;
        }
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        return sdf.parse(date);
    }

    public static String dateToString(Date date) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        return sdf.format(date);
    }

    public static void main(String[] args) throws ParseException {
        System.out.println(stringToDate("2020-03-14 00:00:00"));
    }
}
