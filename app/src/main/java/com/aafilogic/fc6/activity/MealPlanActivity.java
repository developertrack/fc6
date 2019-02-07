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
import com.aafilogic.fc6.adapter.mealPlan.mealplanAdapter;
import com.aafilogic.fc6.dto.mealplan;
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

public class MealPlanActivity extends AppCompatActivity {

    public String user_id, token;
    List<mealplan> mealplanlist;
    private static final String TAG_T = "MealPlan Activity";
       @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mealplan);

           findViewById(R.id.croossign).setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   onBackPressed();
               }
           });

           LoadWeek();

       }

    public void LoadWeek(){

        try {


            mealplanlist = new ArrayList<mealplan>();

            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");

            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_week.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();
                    Log.e("week",String.valueOf(result));

                    try {

                        JSONObject jObj = new JSONObject(response);

                        String success = jObj.getString("Success");

                        JSONArray jsonArray = jObj.getJSONArray("post");

                        if(jsonArray.length()==0){
                            Toast.makeText(MealPlanActivity.this,"No Meal Plan is assigned to you", Toast.LENGTH_LONG).show();
                        }


                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                            mealplanlist.add(new mealplan(R.drawable.week1, jsonQuiz.getString("week"),jsonQuiz.getString("meal_plan_id")));

                        }

                        final RecyclerView recyclerView = (RecyclerView)findViewById(R.id.mealplanlist);

                        mealplanAdapter mealplanAdapter = new mealplanAdapter(mealplanlist, MealPlanActivity.this);
                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MealPlanActivity.this, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(mealplanAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
//                    Toast.makeText(getApplicationContext(),
//                            error.getMessage(), Toast.LENGTH_LONG).show();
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

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }

    }

}

