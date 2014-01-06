package com.javierc.timetracker.API;

import android.content.Context;

/**
 * Created by javierAle on 1/5/14.
 */
public class CheckinStatus extends Updater{

    public CheckinStatus(Context context){
        this.context = context;
        super.setCredentials();
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    @Override
    protected void setContext(Context c) {
        this.context = c;
    }
}
