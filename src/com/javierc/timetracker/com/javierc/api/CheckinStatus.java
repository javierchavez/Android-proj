package com.javierc.timetracker.com.javierc.api;

import android.content.Context;

/**
 * Created by javierAle on 1/5/14.
 */
public class CheckinStatus extends Updater{

    CheckinStatus(Context context){
        super.setCredentials(context);
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}
