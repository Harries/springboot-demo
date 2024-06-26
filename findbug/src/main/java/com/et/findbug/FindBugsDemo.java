package com.et.findbug;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class FindBugsDemo {

    private static final DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

    public static String yyyyMMddForMat(Date date) {
        return yyyyMMdd.format(date);
    }

    public static int getRanDom() {
        return new Random().nextInt();
    }

    public static int round(int num) {
        return Math.round(num);
    }

    public static void printMap(Map<?, ?> map) {
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                System.out.println("key--->" + key);
                System.out.println("value--->" + map.get(key));
            }
        }
    }

    public static String trimString(String str) {
        str.trim();
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}