package com.aafilogic.fc6.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.aafilogic.fc6.MessageAdapter;
import com.aafilogic.fc6.R;
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

public class messageboardActivty extends AppCompatActivity {


    private static final String TAG_T = "Message Activity";
    public String post_id, post_id_n, cmnt_box_string, user_id, token, group_id, msg_string;
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;
    List<PersonUtils> personUtilsList, personUtilsList2, personUtilsList3;
    RecyclerView.LayoutManager layoutManager;
    ImageView img_send_msg;
    EditText et_msg;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.messageboardlist);


        img_send_msg = (ImageView) findViewById(R.id.post_msg);
        et_msg = (EditText) findViewById(R.id.et_msg);

        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.simpleSwipeRefreshLayout);

        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        LoadMessage();

        img_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    msg_string = et_msg.getText().toString();
//                    Log.e("send message", msg_string.toString());

                    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

                    user_id = pref.getString("user_id", "");
                    token = pref.getString("token", "");
                    group_id = pref.getString("group_id", "");


                    // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);
                    View view = messageboardActivty.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    String tag_string_req = "req_POST";

                    //   String url = "https://go1shop.com/api/get_deshboard.php";
                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/create_new_message.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            // loading.dismiss();
                            Log.e("send message", result.toString());
                            et_msg.setText("");
                            View view = messageboardActivty.this.getCurrentFocus();
                            if (view != null) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                            mAdapter2.notifyDataSetChanged();

                            try {


                                JSONObject jObj = new JSONObject(response);


                            } catch (JSONException e) {
                                // JSON error
                                e.printStackTrace();
//                                Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        }
                    }, new Response.ErrorListener()

                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
//                            Toast.makeText(getApplicationContext(),
//                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            et_msg.setText("");

                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    LoadMessage();
                                }
                            }, 5000);


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
                            params.put("message_text", et_msg.getText().toString());
                            Log.e("send message", msg_string);
                            params.put("group_id", group_id);

                            return params;

                        }


                    };

                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                } catch (Exception e) {


                    e.printStackTrace();
                }


            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // cancel the Visual indication of a refresh
                swipeRefreshLayout.setRefreshing(false);

                LoadMessage();
            }
        });
    }

    public void LoadMessage(){

        try {


            recyclerView2 = (RecyclerView) findViewById(R.id.messagelist);
            recyclerView2.setHasFixedSize(true);


            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            personUtilsList2 = new ArrayList<>();


            SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");

            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_all_message.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();
                    Log.e("datat comment",String.valueOf(result));

                    //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");


                        JSONArray jsonArray = jObj.getJSONArray("post");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();

                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            personUtils.setPost_id(jsonQuiz.getString("message_id"));
                            personUtils.setComment_img(jsonQuiz.getString("user_image"));
                            personUtils.setCommenter_name(jsonQuiz.getString("user_name"));
                            personUtils.setComment_text(jsonQuiz.getString("message_text"));
                            personUtils.setLike_count(jsonQuiz.getString("like_count"));
                            personUtils.setComment_text(jsonQuiz.getString("message_text"));
                            personUtils.setPost_add_time(jsonQuiz.getString("message_time"));
                            personUtils.setLike_status(jsonQuiz.getString("like_status"));
                            personUtils.setComment_count(jsonQuiz.getString("comment_count"));
                            personUtils.setComment_user_id(jsonQuiz.getString("user_id"));

                            personUtilsList2.add(personUtils);

                        }


                        mAdapter2 = new MessageAdapter(getApplicationContext(), personUtilsList2);
                        recyclerView2.setAdapter(mAdapter2);
                        mAdapter2.notifyDataSetChanged();


                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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


    @Override
    protected void onResume() {
        super.onResume();
        LoadMessage();
    }
}
