package com.gandler.intellitag.libs.webconn;

import android.content.Context;
import android.net.ConnectivityManager;

public class WebTester
{
    public static boolean isNetworkConnected(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
