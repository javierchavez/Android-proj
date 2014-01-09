package com.javierc.timetracker.API;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.javierc.timetracker.MainActivity;
import com.javierc.timetracker.R;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by javierAle on 1/5/14.
 */
public class MainCheckinStatus extends Updater<Object, Object, String>{
    private String s = "Error";
    private MainActivity activity;
    private TextView textView; TextView textViewUN;
    public MainCheckinStatus(MainActivity a){
        activity = a;
        textView = (TextView) activity.findViewById(R.id.statusTV);
        textViewUN = (TextView) activity.findViewById(R.id.usernameTV);

        this.context = a.getApplicationContext();
        super.setCredentials();
    }

    @Override
    protected String doInBackground(Object[] objects) {
        DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
        try {
            defaultHttpClient.getCredentialsProvider().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                    new UsernamePasswordCredentials(this.getU(), this.getP()));

            HttpGet httpGet = new HttpGet(API.CHECKIN_STATUS_URL.string());

            // Log.d("req ", String.valueOf(httpGet.getRequestLine()));
            HttpResponse response = defaultHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            // Log.d("status ", String.valueOf(response.getStatusLine()));
            if (String.valueOf(response.getStatusLine()).contains(API.STATUS_OK.string())){

                final JSONObject jsonObject = new JSONObject(EntityUtils.toString(entity));
                defaultHttpClient.getConnectionManager().shutdown();
                // Log.d("ob ", jsonObject.toString());
                s = jsonObject.toString();




                s = ((jsonObject.getString("checked-in").contentEquals("true"))?"checked in":"checked out");
                if(!jsonObject.optString("time-in", "").isEmpty()){
                    TextView textViewTime = (TextView) activity.findViewById(R.id.statusTimeTV);
                    textViewTime.setText(jsonObject.optString("time-in", "Error loading time."));
                }
                textViewUN.setText("Welcome, " + this.getU());

                return s;
            }

        } catch(Exception e){
            e.printStackTrace();
        }finally {
            defaultHttpClient.getConnectionManager().shutdown();
        }
        return s;
    }

    @Override
    public void setContext(Context c) {
        this.context = c;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText(s);

        LinearLayout ls = (LinearLayout) activity.findViewById(R.id.listLayout);
        ls.setVisibility(View.VISIBLE);
        LinearLayout ll = (LinearLayout) activity.findViewById(R.id.load_ll);
        ll.setVisibility(View.GONE);
    }
}
