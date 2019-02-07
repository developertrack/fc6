package com.aafilogic.fc6.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.aafilogic.fc6.adapter.LikeUserAdapter;
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

public class LikeUserFragment extends AppCompatActivity {
    String post_id, user_id;
    ProgressDialog progress;
    public List<PersonUtils> personUtils2;
    String token;
    LikeUserAdapter adapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like_user_list);


        mRecyclerView = (RecyclerView)findViewById(R.id.shoppingrecyclerview);

        SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);
        token = pref.getString("token", "");
        user_id = pref.getString("user_id", "");

        String tag_string_req = "req_POST";
        Intent intent = getIntent();

        post_id = intent.getStringExtra("post_id");

        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_like_detail.php";
        progress = new ProgressDialog(this);

        progress.show();

        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                // loading.dismiss();
                Log.e("like count response", result);

//                                {"Success":"1","post":[{"user_image":"..\/admin\/images\/1781_15467116875439.jpg","user_name":"lakeishia"}]}

                //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                personUtils2=new ArrayList<>();

                try {


                    JSONObject jObj = new JSONObject(response);

                    String success = jObj.getString("Success");

                    if(success.equals("1")){
                        progress.dismiss();
                        JSONArray jsonArray=jObj.getJSONArray("post");


                        for(int i=0;i<jsonArray.length();i++) {

                            PersonUtils personUtils = new PersonUtils();


                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                            personUtils.setLike_user_image(jsonQuiz.getString("user_image"));
                            personUtils.setLike_user_name(jsonQuiz.getString("user_name"));

                            personUtils2.add(personUtils);
                        }

                        adapter = new LikeUserAdapter(LikeUserFragment.this, personUtils2);
                        mRecyclerView.setAdapter(adapter);

                        mRecyclerView.setLayoutManager(new LinearLayoutManager(LikeUserFragment.this));
                        adapter.notifyDataSetChanged();

                    }



                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(LikeUserFragment.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener()

        {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Loading", "Post Loading Error: " + error.getMessage());
                Toast.makeText(LikeUserFragment.this,
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
                params.put("post_id", post_id);

                return params;

            }


        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);




    }



}
