package com.javierc.timetracker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                pedit.putString("username", usr);
                pedit.putString("password", pwd);
                pedit.commit();
                finish();
            }
        };
    }

    private class CheckCredsTask extends AsyncTask<String,Object,String>{

        @Override
        protected String doInBackground(String[] strings) {
            return null;
        }
    }
}
