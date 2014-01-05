package com.javierc.timetracker.com.javierc.api;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by javierAle on 1/5/14.
 */
public class CheckCredentialsTask extends AsyncTask<String,Object,String> {


    @Override
    protected String doInBackground(String[] strings) {
        String returned = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.getCredentialsProvider().setCredentials(
                    new AuthScope(API.HOST.toString(), 443),
                    new UsernamePasswordCredentials(strings[0], strings[1]));

            HttpGet httpget = new HttpGet(API.HOST.toString());

            Log.d("req ", String.valueOf(httpget.getRequestLine()));
            HttpResponse response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();

            Log.d("status ", String.valueOf(response.getStatusLine()));
            if (entity != null) {
                Log.d("len ", String.valueOf(entity.getContentLength()));
                System.out.println(EntityUtils.toString(entity));
                returned = EntityUtils.toString(entity);
            }
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            httpclient.getConnectionManager().shutdown();
        }

        return returned;
    }

}