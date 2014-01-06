package com.javierc.timetracker.API;

import android.content.Context;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by javierAle on 1/5/14.
 */
public class UpdateCheckIn extends Updater<String,Object,String> {

    public UpdateCheckIn(Context context){
        this.context = context;
        super.setCredentials();
        showProgressDialog();
    }

    @Override
    protected String doInBackground(String[] strings) {

        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            defaultHttpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(this.u, this.p));

            HttpPost httpPost = new HttpPost(API.CHECKIN_URL.string());

            // Log.d("req ", String.valueOf(httpPost.getRequestLine()));
            HttpResponse response = defaultHttpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            // Log.d("status ", String.valueOf(response.getStatusLine()));
            if (String.valueOf(response.getStatusLine()).contains(API.STATUS_OK.string())){

                final JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
                defaultHttpClient.getConnectionManager().shutdown();
                return "You are " + ((jsonObject.getString("checked-in").contentEquals("true"))?"Checked in":"Checked out");
            }

        } catch(Exception e){
            e.printStackTrace();
        }finally {
            defaultHttpClient.getConnectionManager().shutdown();
        }

        return "Something went wrong";
    }

    @Override
    public void setContext(Context c) { this.context = c; }
}
