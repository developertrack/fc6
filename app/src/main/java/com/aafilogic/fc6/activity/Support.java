package com.aafilogic.fc6.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.adapter.SupportAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Support extends AppCompatActivity {


    private static final String TAG_T = "Message Activity";
    public String post_id, post_id_n, cmnt_box_string, user_id, token, group_id, msg_string;
    RecyclerView recyclerView, recyclerView2, recyclerView3;
    RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;
    List<PersonUtils> personUtilsList, personUtilsList2, personUtilsList3;
    RecyclerView.LayoutManager layoutManager;
    ImageView img_send_msg;
    EditText et_msg;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support);


        img_send_msg = (ImageView) findViewById(R.id.post_msg);
        et_msg = (EditText) findViewById(R.id.et_msg);


        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        img_send_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    msg_string = et_msg.getText().toString().trim();


                    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

                    user_id = pref.getString("user_id", "");
                    token = pref.getString("token", "");
                    group_id = pref.getString("group_id", "");


                    // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);
                    et_msg.setText("");
                    View view = Support.this.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }

                    String tag_string_req = "req_POST";

                    //   String url = "https://go1shop.com/api/get_deshboard.php";
                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/add_support_message.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            LoadMessage();
                            // loading.dismiss();
                            Log.e("send message", result.toString());
                            et_msg.setText("");
                            View view = Support.this.getCurrentFocus();
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
                                Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        }
                    }, new Response.ErrorListener()

                    {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
//                            Toast.makeText(getApplicationContext(),
//                                    error.getMessage(), Toast.LENGTH_LONG).show();
                            LoadMessage();
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
                            params.put("message", msg_string);

                            return params;

                        }


                    };


                    // Adding request to request queue
                    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


                } catch (Exception e) {


                    e.printStackTrace();
                }


            }
        });



        LoadMessage();

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
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_support_message.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();
                    Log.e("datat support",String.valueOf(result));

                    //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {
//                        "admin_message_id": "1",
//                                "sender_id": "1",
//                                "reciver_id": "0",
//                                "message": "test",
//                                "message_time": "2018-12-27 02:11:54"

                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");


                        JSONArray jsonArray = jObj.getJSONArray("post");



                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();

                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            personUtils.setPost_id(jsonQuiz.getString("admin_message_id"));
                            personUtils.setComment_img(jsonQuiz.getString("sender_id"));
                            personUtils.setCommenter_name(jsonQuiz.getString("reciver_id"));
                            personUtils.setComment_text(jsonQuiz.getString("message"));
                            personUtils.setPost_add_time(jsonQuiz.getString("message_time"));

                            personUtilsList2.add(personUtils);

                        }

                        Collections.reverse(personUtilsList2); // ADD THIS LINE TO REVERSE ORDER!
                        mAdapter2 = new SupportAdapter(getApplicationContext(), personUtilsList2);
                        recyclerView2.setAdapter(mAdapter2);
                        mAdapter2.notifyDataSetChanged();


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
