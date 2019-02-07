package com.aafilogic.fc6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.adapter.WeekAdapter;
import com.aafilogic.fc6.dto.MealWeekDetail;
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

public class MealPlanDetailActivity  extends AppCompatActivity {

    TextView week_name;
    String meal_plan_id;
    public String user_id, token;
    List<mealplan> mealplanlist;
    RecyclerView recyclerView2;
    TextView calories,size,breakfast,snacks,lunch,afternoonsnack,dinner;

    String[] day_date, day_name,arr_calories, arr_size,arr_breakfast,morning_snacks,arr_lunch,afternoon_snack,arr_dinner;

    List<MealWeekDetail> mealWeekDetail;
    int pos=0;

    private static final String TAG_T = "MealPlanDetail Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weakdetail);

        Intent intent = getIntent();

        week_name=(TextView)findViewById(R.id.week_name);
        calories=(TextView)findViewById(R.id.calories);
        size=(TextView)findViewById(R.id.size);
        breakfast=(TextView)findViewById(R.id.breakfast);
        snacks=(TextView)findViewById(R.id.snacks);
        lunch=(TextView)findViewById(R.id.lunch);
        afternoonsnack=(TextView)findViewById(R.id.afternoonsnack);
        dinner=(TextView)findViewById(R.id.dinner);

        week_name.setText(intent.getStringExtra("Week"));
        pos=Integer.valueOf(intent.getStringExtra("position"));
        recyclerView2=(RecyclerView)findViewById(R.id.recyclerView2);
        Log.e("week_name",intent.getStringExtra("Week"));
        meal_plan_id=intent.getStringExtra("meal_plan_id");
        Log.e("meal_plan_id",meal_plan_id);

        LoadWeekPlan();

    }

    public void LoadWeekPlan(){

        try {

            mealWeekDetail = new ArrayList<MealWeekDetail>();

            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");

            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_week_data.php";


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

//                        day_date, day_name,arr_calories, arr_size,arr_breakfast,morning_snacks,arr_lunch,afternoon_snack,arr_dinner

                        day_date=new String[jsonArray.length()];
                        day_name=new String[jsonArray.length()];
                        arr_calories=new String[jsonArray.length()];
                        arr_size=new String[jsonArray.length()];
                        arr_breakfast=new String[jsonArray.length()];
                        morning_snacks=new String[jsonArray.length()];
                        arr_lunch=new String[jsonArray.length()];
                        afternoon_snack=new String[jsonArray.length()];
                        arr_dinner=new String[jsonArray.length()];

                        if(jsonArray.length()>0) {

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject jsonQuiz = jsonArray.getJSONObject(i);
                                day_date[i] = jsonQuiz.getString("day_date");
                                day_name[i] = jsonQuiz.getString("day_name");
                                arr_calories[i] = jsonQuiz.getString("calories");
                                arr_size[i] = jsonQuiz.getString("size");
                                arr_breakfast[i] = jsonQuiz.getString("breakfast");
                                morning_snacks[i] = jsonQuiz.getString("morning_snacks");
                                arr_lunch[i] = jsonQuiz.getString("calories");
                                afternoon_snack[i] = jsonQuiz.getString("size");
                                arr_dinner[i] = jsonQuiz.getString("breakfast");

                                mealWeekDetail.add(new MealWeekDetail(day_date[i], day_name[i], arr_calories[i], arr_size[i], arr_breakfast[i], morning_snacks[i], arr_lunch[i], afternoon_snack[i], arr_dinner[i]));

                            }

                            WeekAdapter mAdapter2 = new WeekAdapter(MealPlanDetailActivity.this, mealWeekDetail,pos);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MealPlanDetailActivity.this, LinearLayoutManager.HORIZONTAL, false);
                            recyclerView2.setLayoutManager(linearLayoutManager);
                            recyclerView2.setAdapter(mAdapter2);
                            mAdapter2.notifyDataSetChanged();


//                            calories,size,breakfast,snacks,lunch,afternoonsnack,dinner
                            calories.setText(arr_calories[pos]+" Calories");
                            size.setText("Serving Size: "+arr_size[pos]+" Serving  ");
                            breakfast.setText(arr_breakfast[pos]);
                            snacks.setText(morning_snacks[pos]);
                            lunch.setText(arr_lunch[pos]);
                            afternoonsnack.setText(afternoon_snack[pos]);
                            dinner.setText(arr_dinner[pos]);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG_T, "Post Loading Error: " + error.getMessage());

                }
            }

            ) {


                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", user_id);
                    params.put("meal_plan_id",meal_plan_id);
                    params.put("token", token);

                    return params;

                }


            };

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }

    }

    public void UpdateData(final int position){

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                calories.setText(arr_calories[position]+" Calories");
                size.setText("Serving Sizes: "+arr_size[position]+" Serving");
                breakfast.setText(arr_breakfast[position]);
                snacks.setText(morning_snacks[position]);
                lunch.setText(arr_lunch[position]);
                afternoonsnack.setText(afternoon_snack[position]);
                dinner.setText(arr_dinner[position]);
            }
        });

    }


}

