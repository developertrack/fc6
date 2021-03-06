package com.aafilogic.fc6;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
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

public class Ranking_Teams extends AppCompatActivity {


    private static final String TAG_T = "RankingTeam Activity";


    RecyclerView recyclerView, recyclerView2, recyclerView3;


    RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;


    List<PersonUtils> personUtilsList, personUtilsList2, personUtilsList3;

    RecyclerView.LayoutManager layoutManager;


    String user_id, token;

    TextView tv_team_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking__teams);


        try {

            // Intent intent = getIntent();
            //  group_id=intent.getStringExtra("group_id");


            //  tv_team_name.setText("Team "+group_id);


            recyclerView2 = (RecyclerView) findViewById(R.id.teamlistrecyclerview4);
            recyclerView2.setHasFixedSize(true);


            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            personUtilsList2 = new ArrayList<>();


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");


            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_team_ranking.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();


                    //  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        JSONArray jsonArray = jObj.getJSONArray("post");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();


                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            //   personUtils.setUser_image(jsonQuiz.getString("user_image"));
                            personUtils.setUser_name(jsonQuiz.getString("group_code"));
                            personUtils.setWorkout_count(jsonQuiz.getString("workout_count"));
                            personUtils.setTotal_points(jsonQuiz.getString("total_point"));
                            personUtils.setMeal_count(jsonQuiz.getString("meal_count"));


                            personUtilsList2.add(personUtils);


                        }


                        mAdapter2 = new RankingTeamAdapter(getApplicationContext(), personUtilsList2);
                        recyclerView2.setAdapter(mAdapter2);


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


    public void back_act(View v) {

        finish();

    }


}
