package com.javierc.timetracker.API;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import com.javierc.timetracker.MainActivity;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by javierAle on 1/5/14.
 */
public class CheckinStatus extends Updater<TextView, Object, API>{

    Activity activity;

    public CheckinStatus(Activity activity){
        this.activity = activity;
    }


    @Override
    protected API doInBackground(TextView[] tv) {
//        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
//        try {
//            defaultHttpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
//                    new UsernamePasswordCredentials(this.u, this.p));
//
//            HttpGet httpGet = new HttpGet(API.CHECKIN_STATUS_URL.string());
//
//            Log.d("req ", String.valueOf(httpGet.getRequestLine()));
//            HttpResponse response = defaultHttpClient.execute(httpGet);
//            HttpEntity entity = response.getEntity();
//
//            Log.d("status ", String.valueOf(response.getStatusLine()));
//            if (String.valueOf(response.getStatusLine()).contains(API.STATUS_OK.string())){
//
//                final JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
//                defaultHttpClient.getConnectionManager().shutdown();
//                Log.d("ob ", jsonObject.toString());
//                return API.STATUS_OK;
//            }
//
//        } catch(Exception e){
//            e.printStackTrace();
//        }finally {
//            defaultHttpClient.getConnectionManager().shutdown();
//        }

        return API.STATUS_AUTH_FAIL;
    }

    @Override
    public void setContext(Context c) {
        this.context = c;
    }

    @Override
    protected void onPostExecute(API api) {
        super.onPostExecute(api);
    }
}
