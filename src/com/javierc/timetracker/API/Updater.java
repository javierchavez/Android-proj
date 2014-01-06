package com.javierc.timetracker.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * Created by javierAle on 1/5/14.
 */
abstract class Updater<T,P,R> extends AsyncTask<T,P,R>{
    String u = "";
    String p = "";

    protected void getCredentials(Context c){
        SharedPreferences pref = c.getSharedPreferences("lgen", Context.MODE_PRIVATE);
        u = pref.getString("username", "");
        p = pref.getString("password", "");
    };

}
