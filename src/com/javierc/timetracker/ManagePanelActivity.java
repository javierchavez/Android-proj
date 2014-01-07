package com.javierc.timetracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by javierAle on 1/4/14.
 */
public class ManagePanelActivity extends Activity {
    private static final int CONTACT_PICKER_RESULT = 100;

    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.time_sheet_man);

        init();
    }

    private void init() {
        b = (Button) findViewById(R.id.contactsBtn);
        b.setOnClickListener(contactsBtnListener());
    }

    private View.OnClickListener contactsBtnListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);

                startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CONTACT_PICKER_RESULT:
                    Cursor cursor = null;
                    String email = "";
                    try {
                        Uri result = data.getData();
                        String id = result.getLastPathSegment();

                        cursor = getContentResolver().query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?",
                                new String[] { id },
                                null);

                        int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);

                        if (cursor.moveToFirst()) {
                            email = cursor.getString(emailIndex);
                        } else {

                        }
                    } catch (Exception e) {
                    } finally {
                        if (cursor != null) {
                            cursor.close();
                        }
                        EditText emailEntry = (EditText) findViewById(R.id.emailET);
                        emailEntry.setText(email);
                        if (email.length() == 0) {

                        }
                    }
                    break;
            }
        } else {
        }
    }
}