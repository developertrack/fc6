package com.aafilogic.fc6.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.adapter.notification.notificationAdapter;
import com.aafilogic.fc6.dto.notificationDto;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class activityLogActivty extends AppCompatActivity {


    private static final String TAG_T = "ActivityLog Activity";
    String user_id, token;
    List<notificationDto> notificationDotList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitylog);


        notificationDotList = new ArrayList<notificationDto>();


        /**

         notificationDotList.add(new notificationDto("21/07/2012","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","Salman Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","Salman Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","rahul Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","bunty Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","amit Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","sanjay Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","rahul Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","faclity Khan"));
         notificationDotList.add(new notificationDto("23/01/2008","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","Salman Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","Salman Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","rahul Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","bunty Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","amit Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","sanjay Khan"));
         notificationDotList.add(new notificationDto("19/06/1997","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","rahul Khan"));
         notificationDotList.add(new notificationDto("","https://images.mid-day.com/images/2018/jul/Salman-mall-story_d.jpg","faclity Khan"));

         **/


        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        try {

            // Intent intent = getIntent();
            //  group_id=intent.getStringExtra("group_id");


            //  tv_team_name.setText("Team "+group_id);


/**
 recyclerView2 = (RecyclerView)findViewById(R.id.teamlistrecyclerview4);
 recyclerView2.setHasFixedSize(true);


 layoutManager = new LinearLayoutManager(getApplicationContext());
 recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


 personUtilsList2 = new ArrayList<>();


 **/


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");


            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_activity_log_andy.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();

                    //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

                    try {


                        JSONObject jObj = new JSONObject(response);

                        String success = jObj.getString("Success");

                        JSONArray jsonArray = jObj.getJSONArray("post");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();

                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            String user_image = jsonQuiz.getString("user_image");
                            String text_detail = jsonQuiz.getString("text_detail");
                            String text_time = jsonQuiz.getString("text_time");


                            notificationDotList.add(new notificationDto(text_time, "https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image, text_detail));


                        }


                        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activtliylog);

                        notificationAdapter badgesAdapter = new notificationAdapter(notificationDotList, getApplicationContext());
                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(badgesAdapter);


                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();
                    //  hideDialog();
                }
            }

            ) {


                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", user_id);
                    params.put("token", token);


                    return params;


                }


            };


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }


    }


}
