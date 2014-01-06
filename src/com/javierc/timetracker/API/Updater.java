package com.javierc.timetracker.API;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * Created by javierAle on 1/5/14.
 */
abstract class Updater<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>{
    protected String u = "";
    protected String p = "";
    protected Context context;
    private ProgressDialog progressDialog;

    public abstract void setContext(Context c);


    protected void setCredentials(){
        SharedPreferences pref = context.getSharedPreferences("lgen", Context.MODE_PRIVATE);
        u = pref.getString("username", "");
        p = pref.getString("password", "");
    }

    protected void showProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logging in");
        progressDialog.show();
    }
    protected void dissmisProgressDialog(){
        progressDialog.dismiss();
    }

    @Override
    protected void onPostExecute(Result result) {
        if (progressDialog.isShowing()){ progressDialog.dismiss(); }
        super.onPostExecute(result);
    }
}
