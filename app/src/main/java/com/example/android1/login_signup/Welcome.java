package com.example.android1.login_signup;

import android.support.annotation.InterpolatorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Welcome extends AppCompatActivity {

    String TAG="welcome";
    ArrayList<String> list;
    String[] values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.pink, R.color.indigo, R.color.lime);


        swipeRefreshLayout.setOnRefreshListener(new     SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshData();
            }
        });


        try {
            JSONObject mainObj = new JSONObject(loadJSONFromAsset());
            if(mainObj != null){
                JSONArray list = mainObj.getJSONArray("contacts");
                if(list != null){
                    for(int i = 0; i < list.length();i++){
                        JSONObject elem = list.getJSONObject(i);
                        Log.d(TAG,elem.getString("name"));
                        if(elem != null){
                            JSONArray prods = elem.getJSONArray("phone");
                            if(prods != null){
                                for(int j = 0; j < prods.length();j++){
                                    JSONObject innerElem = prods.getJSONObject(j);
                                    if(innerElem != null){
                                        String mb = innerElem.getString("mobile");
                                        Toast.makeText(getApplicationContext(),mb,Toast.LENGTH_LONG).show();

                                    }
                                }
                            }
                        }
                    }
                }
            }




            } catch (JSONException e1) {
            e1.printStackTrace();
        }


    }

    void refreshData()
    {




        final ListView listview = (ListView) findViewById(R.id.listview);
         values= new String[] { "Android", "iPhone", "WindowsMobile",
                "Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
                "Linux", "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux",
                "OS/2", "Ubuntu", "Windows7", "Max OS X", "Linux", "OS/2",
                "Android", "iPhone", "WindowsMobile" };

        list = new ArrayList<String>();
        for (int i = 1; i < values.length; ++i) {
            list.add(values[i]);
        }
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);



    }



    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("example.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }




}
