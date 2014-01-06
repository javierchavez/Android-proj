package com.javierc.timetracker;

import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import com.javierc.timetracker.API.UpdateCheckIn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {
    String string = "";
    SharedPreferences pref;
    ActionBar actionBar;
    String[] dropdownValues = new String[] {"Select", "Check-in History","Manage Sheet", "NFC"};
    Map<Integer, Intent> map = new HashMap<Integer, Intent>();
    Context context;

//    private boolean mResumed = false;
//    EditText mNote;
//
//    TextView tv;
//    NfcAdapter mAdapter;
//    private PendingIntent mPendingIntent;
//    private IntentFilter[] mFilters;
//    private String[][] mTechLists;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        // check to see if user has tried to signin
        pref = getSharedPreferences("lgen", MODE_PRIVATE);
        String u = pref.getString("username", "");
        String p = pref.getString("password", "");

        // TODO add check for changed password
        if(u.isEmpty() || p.isEmpty()){
            Intent intent = new Intent(this,LoginActivity.class);
            // intent.hasExtra("")
            startActivity(intent);
        }
        context = this;
        map.put(1, new Intent(MainActivity.this, CheckinsListViewActivity.class));
        map.put(2, new Intent(MainActivity.this, ManagePanelActivity.class));
        map.put(3, new Intent(MainActivity.this, NFCActivity.class));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setAdapt();

        Intent intent = getIntent();
        resolveIntent (intent);


    }

    private void resolveIntent(Intent intent) {
        String action = intent.getAction();
        if ((NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) ||
                (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)))
        {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            NdefMessage msg = (NdefMessage) rawMsgs[0];

            extractMessage(msg);

        }
    }

    private void extractMessage(NdefMessage msg) {
        byte[] array = null;
        array = msg.getRecords()[0].getPayload();
        String string = convert (array);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        String status="";
        //Check in here
        try {
             status = new UpdateCheckIn(context).execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // set title
        alertDialogBuilder.setTitle(status);

        // set dialog message
        alertDialogBuilder
                .setMessage(string)
                .setCancelable(false)
                .setPositiveButton("I'm done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("Stay in app", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }



    @Override
    public boolean onNavigationItemSelected(int i, long l) {
        if(i != 0) { startActivity(map.get(i)); }
        return false;
    }


    protected void setAdapt (){

        // Get the action bar
        actionBar = getActionBar();
        // Disable icon title
//        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Specify a SpinnerAdapter to populate the dropdown list.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                actionBar.getThemedContext(),
                android.R.layout.simple_spinner_item, android.R.id.text1,
                dropdownValues);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(adapter, this);
    }

    private String convert(byte[] array) {
        StringBuilder sb = new StringBuilder(array.length);
        for (int i = 0; i < array.length; ++ i) {
            if (array[i] < 0) throw new IllegalArgumentException();
            sb.append((char) array[i]);
        }
        return sb.toString();
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionBar.setSelectedNavigationItem(0);
    }
}
