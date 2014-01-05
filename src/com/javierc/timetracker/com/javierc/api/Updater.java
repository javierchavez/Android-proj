package com.javierc.timetracker.com.javierc.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * Created by javierAle on 1/5/14.
 */
abstract class Updater extends AsyncTask{
    String u = "";
    String p = "";

    public void getCredentials(Context c){
        SharedPreferences pref = c.getSharedPreferences("lgen", Context.MODE_PRIVATE);
        u = pref.getString("username", "");
        p = pref.getString("password", "");
    };

}
