package com.ainosi.iman.bankmandiri.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {
    private static final String TAG = "Util";

    public static long extractAmount(String amount){
        Log.e(TAG, "extractAmount: "+ amount);
        String[] amounts = amount.split("\\.");
        Log.e(TAG, "extractAmount: "+ amounts[0]);
        long asjd = Long.parseLong(amounts[0]);
        Log.e(TAG, "extractAmount: "+ asjd);
        return Long.parseLong(amounts[0]);
    }

    public static String getDateNow(String pattern) {
        Date date = new Date();
        date.setTime(date.getTime() + 8);
        SimpleDateFormat outFormat = new SimpleDateFormat(pattern, Locale.getDefault());
        return outFormat.format(date);
    }

    public static String formatIDRCurrency(String value) {
        int length = value.length();
        int count;
        StringBuilder sb = new StringBuilder();
        if (length % 3 == 0) {
            count = (length / 3) - 1;
        } else {
            count = length / 3;
        }

        int m = -1;
        String reverse = sb.append(value).reverse().toString();
        sb.delete(0, length);
        for (int i = count; i > 0; i--) {
            for (int j = 0; j < 3; j++) {
                m++;
                sb.append(reverse.charAt(m));
            }
            sb.append(".");
        }
        if (m < length - 1) {
            while (m < length - 1) {
                m++;
                sb.append(reverse.charAt(m));
            }
        }

        return sb.reverse().toString();
    }
}
