package com.javierc.timetracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.javierc.timetracker.com.javierc.api.CheckCredentialsTask;
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
                     res = new CheckCredentialsTask().execute(params).get();
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


}
