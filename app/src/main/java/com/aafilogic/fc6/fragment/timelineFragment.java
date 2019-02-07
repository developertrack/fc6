package com.aafilogic.fc6.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.aafilogic.fc6.CustomRecyclerAdapter;
import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
import com.aafilogic.fc6.activity.createpostActivty;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Belal on 1/23/2018.
 */

public class timelineFragment extends Fragment   {


    private static final String TAG_T = "Timeline Activity";

    RecyclerView recyclerView, recyclerView2, recyclerView3;


    List<PersonUtils> personUtilsList, personUtilsList2, personUtilsList3;

    RecyclerView.LayoutManager layoutManager;
    ImageView profile_img;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView.Adapter mAdapter, mAdapter2, mAdapter3;
    int request_code=2;
    SharedPreferences pref;
    comment_datastart datanew;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.menu.home_fragment
        //if it is DashboardFragment it should have R.menu.fragment_dashboard
        //   Toast.makeText(getContext(), "timeline", Toast.LENGTH_LONG).show();

        return inflater.inflate(R.layout.timeline, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {


            profile_img = (ImageView) view.findViewById(R.id.profile_image2);
            swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.simpleSwipeRefreshLayout);

            pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

            String user_image2 = pref.getString("user_image", "");
            final String user_id = pref.getString("user_id", "");
            final String token = pref.getString("token", "");


            Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image2).placeholder( R.drawable.progress_animation ).into(profile_img);

            recyclerView2 = (RecyclerView) getActivity().findViewById(R.id.userpost);
            recyclerView2.setHasFixedSize(true);


            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


            personUtilsList2 = new ArrayList<>();


            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // cancel the Visual indication of a refresh
                    swipeRefreshLayout.setRefreshing(false);
                    SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("visible_position", String.valueOf("0"));
                    Log.e("visible_position", String.valueOf("0"));
                    editor.apply();
                    userData();
                }
            });
             datanew= new comment_datastart() {
                @Override
                public void startCommentActivity(Intent i) {
                    startActivityForResult(i,100);
                }
            };

            //  final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching posts...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String post_url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_all_post.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, post_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    //  loading.dismiss();


                    Log.e("result", result);

                    try {


                        JSONObject jObj = new JSONObject(response);

                        String success = jObj.getString("Success");


                        JSONArray jsonArray = jObj.getJSONArray("post");
                        for (int i = 0; i < jsonArray.length(); i++) {

                            PersonUtils personUtils = new PersonUtils();


                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                            personUtils.setPost_id(jsonQuiz.getString("post_id"));
                            personUtils.setUserid(jsonQuiz.getString("user_id"));
                            personUtils.setPost_type(jsonQuiz.getString("post_type"));

                            personUtils.setUser_image(jsonQuiz.getString("user_image"));
                            personUtils.setUser_name(jsonQuiz.getString("user_name"));
                            personUtils.setPost_add_time(jsonQuiz.getString("post_add_time"));
                            personUtils.setPost_image(jsonQuiz.getString("post_image"));

//                            if(jsonQuiz.getString("post_image").length()>2){
//                                String[] imageArray = jsonQuiz.getString("post_image").split(",");
//                                ArrayList<String> namesList = new ArrayList<>(Arrays.asList(imageArray));
//                                personUtils.setPost_image(namesList);
//                            }

                            personUtils.setPost_text(jsonQuiz.getString("post_text"));
                            personUtils.setLike_count(jsonQuiz.getString("like_count"));
                            personUtils.setLike_status(jsonQuiz.getString("like_status"));
                            personUtils.setComment_count(jsonQuiz.getString("comment_count"));

                            personUtilsList2.add(personUtils);


                        }

                        mAdapter2 = new CustomRecyclerAdapter(getActivity(), personUtilsList2,datanew);
                        recyclerView2.setAdapter(mAdapter2);
                        mAdapter2.notifyDataSetChanged();


                        if (success.equals("1")) {


                        } else if (success.equals("0"))

                        {
                            Toast.makeText(getActivity(), "No post found.", Toast.LENGTH_LONG).show();


                        }


                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener()

            {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
//                    Toast.makeText(getActivity(),
//                            error.getMessage(), Toast.LENGTH_LONG).show();
                    //  hideDialog();
                }
            }

            ) {


                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    Log.e("user_id", user_id);
                    Log.e("token", token);
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


        try {


            view.findViewById(R.id.thought_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent activitylo = new Intent(getActivity(), createpostActivty.class);
//                    startActivity(activitylo);
                    Intent i = new Intent(getActivity(), createpostActivty.class);
                    i.putExtra("helloString", "createpost");
                    startActivityForResult(i, request_code);
                }
            });


            view.findViewById(R.id.thoughtext).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), createpostActivty.class);
                    i.putExtra("helloString", "createpost");
                    startActivityForResult(i, request_code);
                }
            });


        } catch (Exception e) {

            e.printStackTrace();
        }

    }


    public void create_post(View v) {

        Intent activityl = new Intent(getActivity(), createpostActivty.class);
        startActivity(activityl);


    }

    @Override
    public void onResume(){
        super.onResume();
//        mAdapter2.notifyDataSetChanged();
        //OnResume Fragment
}

public void userData() {


    pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

    String user_image2 = pref.getString("user_image", "");
    final String user_id = pref.getString("user_id", "");
    final String token = pref.getString("token", "");


    Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image2).placeholder(R.drawable.progress_animation).into(profile_img);


    recyclerView2 = (RecyclerView) getActivity().findViewById(R.id.userpost);
    recyclerView2.setHasFixedSize(true);


    layoutManager = new LinearLayoutManager(getActivity());
    recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


    personUtilsList2 = new ArrayList<>();


    //  final ProgressDialog loading = ProgressDialog.show(getActivity(), "Please wait...", "Fetching posts...", false, true);


    String tag_string_req = "req_POST";

    //   String url = "https://go1shop.com/api/get_deshboard.php";
    String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_all_post.php";


    StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            String result = response.toString();
            //  loading.dismiss();


            Log.e("result", result);

            //  Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();


            try {


                JSONObject jObj = new JSONObject(response);

                // boolean error = jObj.getBoolean("Success");

                // Check for error node in json
                // Now store the user in SQLite
                String success = jObj.getString("Success");


                // JSONObject user = jObj.getJSONObject("post");


                JSONArray jsonArray = jObj.getJSONArray("post");
                for (int i = 0; i < jsonArray.length(); i++) {

                    PersonUtils personUtils = new PersonUtils();


                    JSONObject jsonQuiz = jsonArray.getJSONObject(i);


                    personUtils.setPost_id(jsonQuiz.getString("post_id"));
                    personUtils.setUserid(jsonQuiz.getString("user_id"));
                    personUtils.setPost_type(jsonQuiz.getString("post_type"));

                    personUtils.setUser_image(jsonQuiz.getString("user_image"));
                    personUtils.setUser_name(jsonQuiz.getString("user_name"));
                    personUtils.setPost_add_time(jsonQuiz.getString("post_add_time"));
                    personUtils.setPost_image(jsonQuiz.getString("post_image"));

//                            if(jsonQuiz.getString("post_image").length()>2){
//                                String[] imageArray = jsonQuiz.getString("post_image").split(",");
//                                ArrayList<String> namesList = new ArrayList<>(Arrays.asList(imageArray));
//                                personUtils.setPost_image(namesList);
//                            }

                    personUtils.setPost_text(jsonQuiz.getString("post_text"));
                    personUtils.setLike_count(jsonQuiz.getString("like_count"));
                    personUtils.setLike_status(jsonQuiz.getString("like_status"));
                    personUtils.setComment_count(jsonQuiz.getString("comment_count"));

                    personUtilsList2.add(personUtils);


                }

                mAdapter2 = new CustomRecyclerAdapter(getActivity(), personUtilsList2,datanew);
                recyclerView2.setAdapter(mAdapter2);
                mAdapter2.notifyDataSetChanged();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SharedPreferences pref1 = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
                        String post_pos=pref1.getString("visible_position", "0");
                        Log.e("visible_position", String.valueOf(post_pos));

                        recyclerView2.smoothScrollToPosition(Integer.valueOf(post_pos));
                    }
                });


                if (success.equals("1")) {


                } else if (success.equals("0"))

                {
                    Toast.makeText(getActivity(), "No post found.", Toast.LENGTH_LONG).show();


                }


            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
    }, new Response.ErrorListener()

    {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG_T, "Post Loading Error: " + error.getMessage());
            Toast.makeText(getActivity(),
                    error.getMessage(), Toast.LENGTH_LONG).show();
            //  hideDialog();
        }
    }

    ) {


        @Override
        protected Map<String, String> getParams() {
            // Posting parameters to login url
            Map<String, String> params = new HashMap<String, String>();
            Log.e("user_id", user_id);
            Log.e("token", token);
            params.put("user_id", user_id);
            params.put("token", token);


            return params;


        }


    };


    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
}


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("request code",String.valueOf(requestCode));
        if(requestCode==request_code){
            userData();
            SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("visible_position", String.valueOf("0"));
            Log.e("visible_position", String.valueOf("0"));
            editor.apply();
        }if(requestCode==100){
            userData();
        }
    }



}