package com.javierc.timetracker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.javierc.timetracker.API.API;
import com.javierc.timetracker.API.NewLoginTask;

import java.util.concurrent.ExecutionException;


/**
 * Created by Javier
 */
public class LoginActivity extends Activity {
    EditText editTextUsername, editTextPassword;
    Button buttonSubmit;
    ///SharedPreferences pref;
    Context context;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        initViews();

        context = this;
        if (extras != null ) { logoutUser(extras); }
    }

    private void initViews() {
        setContentView(R.layout.login);

        tv = (TextView)findViewById(R.id.respMsg);
        buttonSubmit = (Button)findViewById(R.id.signinBtn);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.respMsg)).setText("");
            }
        });
        editTextUsername.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((TextView) findViewById(R.id.respMsg)).setText("");
            }
        });

        buttonSubmit.setOnClickListener(submitBtnListener());
    }

    private View.OnClickListener submitBtnListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                tv.setText("Verifying...");
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);

                String pwd = editTextPassword.getText().toString();
                String usr = editTextUsername.getText().toString();

                String[] params = new String[]{usr,pwd};
                API res = API.STATUS_AUTH_FAIL;
                try {
                     res = new NewLoginTask(context).execute(params).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                if(res == API.STATUS_OK) {
                    tv.setText("Great!");
                    finish();
                }
                else {
                    tv.setText("Check Username or Password");
                    editTextPassword.setText("");
                    editTextUsername.setText("");
                }
            }
        };
    }

    private void logoutUser(Bundle extras){
        String logout = extras.getString("logout");
        if (logout != null && logout.contains("true")){
            SharedPreferences pref = getSharedPreferences("lgen", MODE_PRIVATE);
            SharedPreferences.Editor pedit = pref.edit();
            pedit.putString("username", "");
            pedit.putString("password", "");
            pedit.commit();
            tv.setText("Logged out.");
        }
    }
}
