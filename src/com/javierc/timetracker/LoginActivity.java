package com.javierc.timetracker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.javierc.timetracker.API.NewLoginTask;

import java.util.concurrent.ExecutionException;


/**
 * Created by Javier
 */
public class LoginActivity extends Activity {
    EditText editTextUsername, editTextPassword;
    Button buttonSubmit;
    SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        initViews();
    }

    private void initViews() {
        buttonSubmit = (Button)findViewById(R.id.signinBtn);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);

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
                String res = "";
                try {
                     res = new NewLoginTask().execute(params).get();
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
                    TextView tv = (TextView)findViewById(R.id.respMsg);
                    tv.setText("Check Username or Password");
                }
            }
        };
    }


}
