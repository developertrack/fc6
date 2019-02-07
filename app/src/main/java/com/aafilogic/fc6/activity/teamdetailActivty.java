package com.aafilogic.fc6.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.TeamMemberAdapter;
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

public class teamdetailActivty extends AppCompatActivity {


    private static final String TAG_T = "TeamMember Activity";

    String group_id;


    RecyclerView recyclerView, recyclerView2, recyclerView3;


    RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;


    List<PersonUtils> personUtilsList, personUtilsList2, personUtilsList3;

    RecyclerView.LayoutManager layoutManager;


    String user_id, token;

    TextView tv_team_name;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teamdetail);

        tv_team_name = (TextView) findViewById(R.id.teamname);

        /**


         Intent intent = getIntent();
         Bundle args = intent.getBundleExtra("BUNDLE");

         ArrayList<teamdetail> teamdetaillist = (ArrayList<teamdetail>) args.getSerializable("ARRAYLIST");
         String teamname=args.getString("teamname");
         TextView teamnameset=(TextView)findViewById(R.id.teamname);
         teamnameset.setText(teamname);

         final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.teamlistrecyclerview);

         teamdetailAdapter teamdetailAdapter= new teamdetailAdapter(teamdetaillist,this);
         final  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
         recyclerView.setLayoutManager(mLayoutManager);
         recyclerView.setItemAnimator(new DefaultItemAnimator());
         recyclerView.setAdapter(teamdetailAdapter);

         **/

         findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
        onBackPressed();
        }
        });




        try {

            Intent intent = getIntent();
            group_id = intent.getStringExtra("group_id");


            tv_team_name.setText("Team " + group_id);


            recyclerView2 = (RecyclerView) findViewById(R.id.teamlistrecyclerview3);
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
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_team_member.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    Log.e("result team",result);
                    // loading.dismiss();


                    //  Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        JSONArray jsonArray = jObj.getJSONArray("post");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();


                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            personUtils.setUser_image(jsonQuiz.getString("user_image"));
                            personUtils.setUser_name(jsonQuiz.getString("user_name"));
                            personUtils.setWorkout_count(jsonQuiz.getString("workout_count"));
                            personUtils.setMeal_count(jsonQuiz.getString("meal_count"));
                            personUtils.setTotal_points(jsonQuiz.getString("total_point"));




                            personUtilsList2.add(personUtils);


                        }


                        mAdapter2 = new TeamMemberAdapter(getApplicationContext(), personUtilsList2);
                        recyclerView2.setAdapter(mAdapter2);
                        mAdapter2.notifyDataSetChanged();
                        // JSONObject user = jObj.getJSONObject("post");


                        /**
                         String success = jObj.getString("Success");



                         // JSONObject user = jObj.getJSONObject("post");





                         JSONArray jsonArray=jObj.getJSONArray("post");
                         for(int i=0;i<jsonArray.length();i++) {

                         PersonUtils personUtils = new PersonUtils();


                         JSONObject jsonQuiz = jsonArray.getJSONObject(i);




                         personUtils.setPost_id(jsonQuiz.getString("post_id"));
                         personUtils.setUser_image(jsonQuiz.getString("user_image"));
                         personUtils.setUser_name(jsonQuiz.getString("user_name"));
                         personUtils.setPost_add_time(jsonQuiz.getString("post_add_time"));
                         personUtils.setPost_image(jsonQuiz.getString("post_image"));
                         personUtils.setPost_text(jsonQuiz.getString("post_text"));
                         personUtils.setLike_count(jsonQuiz.getString("like_count"));
                         personUtils.setComment_count(jsonQuiz.getString("comment_count"));



                         personUtilsList2.add(personUtils);



                         }



                         RecyclerView.Adapter mAdapter,mAdapter2,mAdapter3;
                         mAdapter2 = new CustomRecyclerAdapter(getActivity(), personUtilsList2);
                         recyclerView2.setAdapter(mAdapter2);


                         if(success.equals("1")){




                         }else if(success.equals("0"))

                         {
                         Toast.makeText(getActivity(), "No post found.", Toast.LENGTH_LONG).show();



                         }



                         **/


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
                    params.put("group_id", group_id);


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
