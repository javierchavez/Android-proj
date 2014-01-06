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
    SharedPreferences pref;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();

        if (extras != null ) { logoutUser(extras); }
        setContentView(R.layout.login);
        initViews();
        context = this;

    }

    private void initViews() {

        buttonSubmit = (Button)findViewById(R.id.signinBtn);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ((TextView) findViewById(R.id.respMsg)).setText("");
            }
        });
        editTextUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                ((TextView) findViewById(R.id.respMsg)).setText("");
            }
        });

        buttonSubmit.setOnClickListener(submitBtnListener());
    }

    private View.OnClickListener submitBtnListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);

                pref = getSharedPreferences("lgen", MODE_PRIVATE);
                SharedPreferences.Editor pedit = pref.edit();
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
                // This causesed too many skipped frames!
                if(res == API.STATUS_OK) {
                    pedit.putString("username", usr);
                    pedit.putString("password", pwd);
                    pedit.commit();
                    finish();
                }
                else {
                    TextView tv = (TextView)findViewById(R.id.respMsg);
                    tv.setText("Check Username or Password");
                }
            }
        };
    }

    private void logoutUser(Bundle extras){
        String logout = extras.getString("logout");
        if (logout != null && logout.contains("true")){
            pref = getSharedPreferences("lgen", MODE_PRIVATE);
            SharedPreferences.Editor pedit = pref.edit();
            pedit.putString("username", "");
            pedit.putString("password", "");
            pedit.commit();
        }
    }
}
