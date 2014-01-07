package com.javierc.timetracker.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;


/**
 * Created by javierAle on 1/5/14.
 */
public class NewLoginTask extends AsyncTask<String,Object,API> {


    private final Context context;
    SharedPreferences pref;

    public NewLoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected API doInBackground(String[] strings) {

        pref = context.getSharedPreferences("lgen", Context.MODE_PRIVATE);
        SharedPreferences.Editor pedit = pref.edit();

        DefaultHttpClient httpclient = new DefaultHttpClient();
        try {
            httpclient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(strings[0], strings[1]));

            HttpPost httpPost = new HttpPost(API.LOGIN_URL.string());

            // Log.d("req ", String.valueOf(httpPost.getRequestLine()));
            HttpResponse response = httpclient.execute(httpPost);
            // HttpEntity entity = response.getEntity();

            // Log.d("status ", String.valueOf(response.getStatusLine()));
            if (String.valueOf(response.getStatusLine()).contains(API.STATUS_OK.string())){
                httpclient.getConnectionManager().shutdown();
                pedit.putString("username", strings[0]);
                pedit.putString("password", strings[1]);
                pedit.commit();

                return API.STATUS_OK;
            }

        } catch(Exception e){
            e.printStackTrace();
        }finally {
            httpclient.getConnectionManager().shutdown();
        }

        return API.STATUS_AUTH_FAIL;
    }

    @Override
    protected void onPostExecute(API api) {
        super.onPostExecute(api);
    }
}