package com.javierc.timetracker.API;

import android.content.Context;

/**
 * Created by javierAle on 1/5/14.
 */
public class UpdateCheckIn extends Updater<String,Object,String> {

    UpdateCheckIn(Context context){
        super.getCredentials(context);
    }

    @Override
    protected String doInBackground(String[] strings) {
        return null;
    }
}
