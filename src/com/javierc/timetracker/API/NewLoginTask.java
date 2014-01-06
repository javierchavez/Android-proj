package com.javierc.timetracker.API;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by javierAle on 1/5/14.
 */
public class NewLoginTask extends AsyncTask<String,Object,API> {


    @Override
    protected API doInBackground(String[] strings) {
        String returned = "";
        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(strings[0], strings[1]));

            HttpPost httpPost = new HttpPost(API.LOGIN_URL.string());

            Log.d("req ", String.valueOf(httpPost.getRequestLine()));
            HttpResponse response = httpclient.execute(httpPost);
            // HttpEntity entity = response.getEntity();

            Log.d("status ", String.valueOf(response.getStatusLine()));
            if (String.valueOf(response.getStatusLine()).indexOf(API.STATUS_OK.string()) != -1){
                httpclient.getConnectionManager().shutdown();
                return API.STATUS_OK;
            }
//            if (entity != null) {
//                Log.d("len ", String.valueOf(entity.getContentLength()));
//                final JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
//                returned = jsonObject.toString();
//            }
        } catch(Exception e){
            e.printStackTrace();
        }finally {
            httpclient.getConnectionManager().shutdown();
        }

        return API.STATUS_AUTH_FAIL;
    }

}