package com.aafilogic.fc6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Comment_on_Message extends AppCompatActivity {


    private static final String TAG_T = "Comment on MSG Activity";

    public String post_id, post_id_n, cmnt_box_string, user_id, token, like_status, like_count;


    public TextView tv_post_text, tv_post_user_name, tv_post_time, tv_like_count, tv_comment_count, discp, quantity;
    public EditText et_cmntbox;
    //   public TextView pJobProfile;
    ImageView img_post_image, post_user_image, like_icon, comment_icon, img_post_cmnt;
    RecyclerView recyclerView, recyclerView2, recyclerView3;


    RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;


    List<PersonUtils> personUtilsList, personUtilsList2;

    RecyclerView.LayoutManager layoutManager;
    String position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_on__message);


        tv_post_text = (TextView) findViewById(R.id.messsagedetail);
        tv_post_user_name = (TextView) findViewById(R.id.messagename);
        tv_post_time = (TextView) findViewById(R.id.msg_date);
        tv_like_count = (TextView) findViewById(R.id.like_count);
        tv_comment_count = (TextView) findViewById(R.id.comment_count);


        post_user_image = (ImageView) findViewById(R.id.notificationimage);
        like_icon = (ImageView) findViewById(R.id.like_icon);
        comment_icon = (ImageView) findViewById(R.id.comment_icon);
        img_post_cmnt = (ImageView) findViewById(R.id.post_msg);
        et_cmntbox = (EditText) findViewById(R.id.et_msg);

        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        try {


            Intent intent = getIntent();

            post_id = intent.getStringExtra("post_id");
            position= intent.getStringExtra("position");
            recyclerView2 = (RecyclerView) findViewById(R.id.messagelist);
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
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_single_message.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();


                    //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        JSONObject post = jObj.getJSONObject("post");

                        JSONObject inner_post = post.getJSONObject("post");


                        {


                            post_id_n = inner_post.getString("message_id");
                            String user_image = inner_post.getString("user_image");
                            String user_name = inner_post.getString("user_name");
                            String post_add_time = inner_post.getString("message_time");
                            String post_text = inner_post.getString("message_text");
                            like_count = inner_post.getString("like_count");
                            like_status = inner_post.getString("like_status");
                            //  String comment_count=  inner_post.getString("comment_count");
                            try {

                                SimpleDateFormat dateFormatParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


                                DateFormat targetFormat = new SimpleDateFormat("EE MMM dd yyyy hh:mm:ss aa ", Locale.ENGLISH);

                                Date dateString = dateFormatParse.parse(post_add_time);

                                String formattedDate = targetFormat.format(dateString);

//                            holder.post_time.setText(String.valueOf(formattedDate));

                                tv_post_time.setText(String.valueOf(formattedDate));
                            }catch (Exception e){

                            }
                            tv_post_user_name.setText(user_name);
                            tv_post_text.setText(post_text);

                            tv_post_text.setMovementMethod(new ScrollingMovementMethod());


                            if (!like_count.equals("0")) {

                                tv_like_count.setText(like_count);


                            }


                            if (like_status.equals("1")) {

                                like_icon.setImageResource(R.drawable.likefly);


                            }


                            Picasso.with(getApplicationContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder( R.drawable.progress_animation ).into(post_user_image);


                            JSONArray jsonArray = post.getJSONArray("comment");
                            Log.e("datat comment",String.valueOf(jsonArray));

                            for (int i = 0; i < jsonArray.length(); i++) {

                                PersonUtils personUtils = new PersonUtils();

                                JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                                personUtils.setComment_img(jsonQuiz.getString("user_image"));
                                personUtils.setCommenter_name(jsonQuiz.getString("user_name"));
                                personUtils.setComment_text(jsonQuiz.getString("comment_text"));

                                personUtils.setComment_on_message_id(jsonQuiz.getString("comment_on_message_id"));
                                personUtils.setUserid(jsonQuiz.getString("user_id"));
                                personUtils.setMessage_id(jsonQuiz.getString("message_id"));

                                personUtilsList2.add(personUtils);


                            }


                            mAdapter2 = new CommentAdapter(getApplicationContext(), personUtilsList2);
                            recyclerView2.setAdapter(mAdapter2);
                            mAdapter2.notifyDataSetChanged();


                        }



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
                    params.put("message_id", post_id);


                    return params;


                }


            };


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }


        like_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if (like_status.equals("1")) {


                        like_icon.setImageResource(R.drawable.like);
                        int l_cnt = Integer.valueOf(like_count);
                        int lcnt_m = l_cnt--;
                        lcnt_m--;


                        if (!String.valueOf(lcnt_m).equals("0")) {

                            tv_like_count.setText(String.valueOf(lcnt_m));


                        } else if (lcnt_m < 1) {


                            tv_like_count.setText("");


                        }

                        like_status = "0";
                        like_count = String.valueOf(lcnt_m);


                    } else {


                        like_icon.setImageResource(R.drawable.likefly);

                        int l_cntmn = Integer.valueOf(like_count);
                        int lcnt_mp = l_cntmn++;
                        lcnt_mp++;
                        tv_like_count.setText(String.valueOf(lcnt_mp));
                        like_status = "1";

                        like_count = String.valueOf(lcnt_mp);


                    }


                    SharedPreferences pref = getSharedPreferences(Config.SHARED_PREF, 0);

                    user_id = pref.getString("user_id", "");
                    token = pref.getString("token", "");


                    String tag_string_req = "req_POST";

                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/like_dislike_message.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();


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
                            params.put("message_id", post_id);


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


        img_post_cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cmnt_box_string = et_cmntbox.getText().toString().trim();


                try {


                    String tag_string_req = "req_POST";

                    //   String url = "https://go1shop.com/api/get_deshboard.php";
                    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/comment_on_message.php";


                    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String result = response.toString();
                            // loading.dismiss();


                            //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                            try {

                                et_cmntbox.setText("");

                                load_comment();


                                JSONObject jObj = new JSONObject(response);



                            } catch (JSONException e) {
                                // JSON error
                                load_comment();
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
                            load_comment();
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
                            params.put("comment_text", cmnt_box_string);
                            params.put("message_id", post_id);


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


    }


    public void load_comment() {


        try {


            recyclerView2 = (RecyclerView) findViewById(R.id.messagelist);
            recyclerView2.setHasFixedSize(true);


            layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));


            personUtilsList2 = new ArrayList<>();


            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_single_message.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();


                    //   Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();


                    try {


                        JSONObject jObj = new JSONObject(response);


                        String success = jObj.getString("Success");

                        JSONObject post = jObj.getJSONObject("post");

                        JSONObject inner_post = post.getJSONObject("post");


                        {


                            post_id_n = inner_post.getString("message_id");
                            String user_image = inner_post.getString("user_image");
                            String user_name = inner_post.getString("user_name");
                            String post_add_time = inner_post.getString("message_time");
                            String post_text = inner_post.getString("message_text");
                            String like_count = inner_post.getString("like_count");
                            String like_status = inner_post.getString("like_status");
                            //  String comment_count=  inner_post.getString("comment_count");


                            tv_post_time.setText(post_add_time);
                            tv_post_user_name.setText(user_name);
                            tv_post_text.setText(post_text);


                            if (!like_count.equals("0")) {

                                tv_like_count.setText(like_count);


                            }


                            if (like_status.equals("1")) {

                                like_icon.setImageResource(R.drawable.likefly);


                            }


                            //   if(!comment_count.equals("0")){

                            //     tv_comment_count.setText(comment_count);


                            // }


                            Picasso.with(getApplicationContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).into(post_user_image);

                            JSONArray jsonArray = post.getJSONArray("comment");
                            Log.e("datat comment",String.valueOf(jsonArray));

                            for (int i = 0; i < jsonArray.length(); i++) {

                                PersonUtils personUtils = new PersonUtils();

                                JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                                personUtils.setComment_img(jsonQuiz.getString("user_image"));
                                personUtils.setCommenter_name(jsonQuiz.getString("user_name"));
                                personUtils.setComment_text(jsonQuiz.getString("comment_text"));

                                personUtils.setComment_on_message_id(jsonQuiz.getString("comment_on_message_id"));
                                personUtils.setUserid(jsonQuiz.getString("user_id"));
                                personUtils.setMessage_id(jsonQuiz.getString("message_id"));

                                personUtilsList2.add(personUtils);


                            }


                            mAdapter2 = new CommentAdapter(getApplicationContext(), personUtilsList2);
                            recyclerView2.setAdapter(mAdapter2);
                            mAdapter2.notifyDataSetChanged();




                        }



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
                    params.put("message_id", post_id);

                    return params;


                }


            };


            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }


    }


    public void back(View v) {


        finish();

    }


}
