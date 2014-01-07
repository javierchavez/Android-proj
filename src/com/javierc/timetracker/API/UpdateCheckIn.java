package com.javierc.timetracker.API;

import android.content.Context;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by javierAle on 1/5/14.
 */
public class UpdateCheckIn extends Updater<String,Object,String> {
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ssXXX";


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
                    new UsernamePasswordCredentials(this.getU(), this.getP()));
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);

            JSONObject jsonObj = new JSONObject();
            jsonObj.put("time", sdf.format(cal.getTime()));

            StringEntity postEntity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
            postEntity.setContentType("application/json");

            HttpPost httpPost = new HttpPost(API.CHECKIN_URL.string());

            httpPost.setEntity(postEntity);

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
