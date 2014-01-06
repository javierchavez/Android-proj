package com.javierc.timetracker.API;

import android.content.Context;

/**
 * Created by javierAle on 1/5/14.
 */
public class UpdateCheckIn extends Updater<String,Object,String> {

    public UpdateCheckIn(Context context){
        this.context = context;
        super.setCredentials();
        showDialog();
    }

    @Override
    protected String doInBackground(String[] strings) {
        return null;
    }

    @Override
    public void setContext(Context c) { this.context = c; }
}
