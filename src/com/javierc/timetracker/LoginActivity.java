package com.javierc.timetracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.concurrent.ExecutionException;


/**
 * Created by Javier
 */
public class LoginActivity extends Activity {
    EditText editTextUsername, editTextPassword;
    Button buttonSubmitt;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initViews();
    }

    private void initViews() {
        buttonSubmitt = (Button)findViewById(R.id.signinBtn);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        buttonSubmitt.setOnClickListener(submitBtnListener());
    }

    private View.OnClickListener submitBtnListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref = getSharedPreferences("lgen", MODE_PRIVATE);
                SharedPreferences.Editor pedit = pref.edit();
                String pwd = editTextPassword.getText().toString();
                String usr = editTextUsername.getText().toString();

                String[] params = new String[]{usr,pwd};
                String res = "";
                try {
                     res = new CheckCredsTask().execute(params).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(!res.isEmpty()) {
                    pedit.putString("username", usr);
                    pedit.putString("password", pwd);
                    pedit.commit();
                    finish();
                }
                else {

                }
            }
        };
    }

    private class CheckCredsTask extends AsyncTask<String,Object,String>{


        @Override
        protected String doInBackground(String[] strings) {
            String returned = "";
            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                httpclient.getCredentialsProvider().setCredentials(
                        new AuthScope("ttcheckin.eu01.aws.af.cm", 443),
                        new UsernamePasswordCredentials(strings[0], strings[1]));

                HttpGet httpget = new HttpGet("ttcheckin.eu01.aws.af.cm");

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
}
