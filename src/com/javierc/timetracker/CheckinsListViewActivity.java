package com.javierc.timetracker;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.javierc.timetracker.API.API;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierAle on 1/4/14.
 */
public class CheckinsListViewActivity extends Activity {
    protected AQuery listAq;
    protected AQuery aq;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_checkins);
        submitBtn = (Button) findViewById(R.id.submitbutton);
        submitBtn.setOnClickListener(submitListener());
        aq = new AQuery(this);


    }

    private View.OnClickListener submitListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadCheckins();
            }
        };
    }

    private void loadCheckins() {
        String url = API.GET_SPEC_WEEK_URL.string();
        aq.progress(R.id.progress_lv).ajax(url, JSONObject.class,this, "renderCheckins");

    }

    public void renderCheckins(String url, JSONObject json, AjaxStatus status) {


        if(json == null) return;
        JSONObject jo = json.optJSONObject("responseData");
        JSONArray ja = jo.optJSONArray("results");
//		Log.i("pub", ja.toString());

        if(ja == null) return;

        List<JSONObject> items = new ArrayList<JSONObject>();
        addItems(ja, items);

        listAq = new AQuery(this);


        ArrayAdapter<JSONObject> aa = new ArrayAdapter<JSONObject>(this, R.layout.content_item_s, items){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.content_item_s, null);
                }

                JSONObject jo = getItem(position);

                AQuery aq = listAq.recycle(convertView);
//                aq.id(R.id.name).text(jo.optString("title", "No Title"));
//                aq.id(R.id.meta).text(jo.optString("publisher", ""));
//                Log.i("pub", jo.optString("publisher", ""));

//				String tb = jo.optString("profile_image_url");

//				aq.id(R.id.tb).progress(R.id.progress).image(tb, true, true, 0, 0, null, AQuery.FADE_IN_NETWORK, 1.0f);


                return convertView;

            }
        };

        aq.id(R.id.list).adapter(aa);
//        ListView lv = (ListView) findViewById(R.id.list);
//        lv.setOnItemClickListener(itemClick());


    }

    private void addItems(JSONArray ja, List<JSONObject> items){
        for(int i = 0 ; i < ja.length(); i++){
            JSONObject jo = ja.optJSONObject(i);
            if(jo.has("content")){
                items.add(jo);
            }
        }
    }

}
