package com.aafilogic.fc6.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.PersonUtils;
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


/**
 * Created by Belal on 1/23/2018.
 */

public class notificationFragment extends Fragment {


    private static final String TAG_T = "ActivityLog Activity";
    String user_id, token, group_id;
    List<notificationDto> notificationDotList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.menu.home_fragment
        //if it is DashboardFragment it should have R.menu.fragment_dashboard
        //  Toast.makeText(getContext(), "timeline", Toast.LENGTH_LONG).show();

        return inflater.inflate(R.layout.notification, container, false);
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notificationDotList = new ArrayList<notificationDto>();


        try {


            SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

            user_id = pref.getString("user_id", "");
            token = pref.getString("token", "");
            group_id = pref.getString("group_id", "");
            // final ProgressDialog loading = ProgressDialog.show(getApplicationContext(), "Please wait...", "Fetching measurements...", false, true);


            String tag_string_req = "req_POST";

            //   String url = "https://go1shop.com/api/get_deshboard.php";
            String url = "https://www.aafilogicinfotech.com/demo/FC6/api/get_notification_andy.php";


            StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    String result = response.toString();
                    // loading.dismiss();

                    //  Toast.makeText(getActivity(), result, Toast.LENGTH_LONG).show();

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

                        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.notifcation);

                        notificationAdapter badgesAdapter = new notificationAdapter(notificationDotList, getContext());
                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(badgesAdapter);


                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
//                        Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
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
                    params.put("user_id", user_id);
                    params.put("token", token);
                    params.put("group_id", group_id);
                    return params;

                }
            };

            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


        } catch (Exception e) {


            e.printStackTrace();
        }

    }

}