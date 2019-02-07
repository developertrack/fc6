package com.aafilogic.fc6.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.aafilogic.fc6.Measurements;
import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.AppController;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.activityLogActivty;
import com.aafilogic.fc6.activity.createpostActivty;
import com.aafilogic.fc6.activity.editprofileActivty;
import com.aafilogic.fc6.adapter.badges.badgesAdapter;
import com.aafilogic.fc6.dto.badgesDto;
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

public class profileFragment extends Fragment {

    ImageView profile_img2, profile_img1, cover_image;
    String[] badges, badges_type;
    TextView tv_username, points_table;
    String token;
    String user_id;
    RecyclerView recyclerView;
    SharedPreferences pref;
    RecyclerView.LayoutManager mLayoutManager;
    LinearSmoothScroller linearSmoothScroller;
    double size_display=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.profile, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        try {

            profile_img2 = (ImageView) view.findViewById(R.id.profile_image2);
            profile_img1 = (ImageView) view.findViewById(R.id.profile_image);
            cover_image = (ImageView) view.findViewById(R.id.image1);
            points_table = (TextView) view.findViewById(R.id.points_table);
            tv_username = (TextView) view.findViewById(R.id.user_name);
            TextView tv_workout = (TextView) view.findViewById(R.id.workout);
            TextView tv_meals = (TextView) view.findViewById(R.id.meals);

            size_display=getScreenSizeInches(getActivity());
            Log.e("size screen",String.valueOf(size_display));

            if(size_display>=6){
                final float scale = getResources().getDisplayMetrics().density;
                int dpWidthInPx  = (int) (300 * scale);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, dpWidthInPx);
                cover_image.setLayoutParams(layoutParams);
                int dpWidth  = (int) (96 * scale);
                int dpHeight  = (int) (96 * scale);
                RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(dpHeight, dpWidth);
                layoutParams1.addRule(RelativeLayout.CENTER_HORIZONTAL);
                layoutParams1.setMargins(0,480,0,0);
                profile_img1.setLayoutParams(layoutParams1);
            }

            pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

            String user_image = pref.getString("user_image", "");
            final String cover_img = pref.getString("cover_image", "");
            token = pref.getString("token", "");
            final String username = pref.getString("user_name", "");
            final String workout = pref.getString("total_workout", "");
            final String meals = pref.getString("total_meals", "");
            final String badges1 = pref.getString("badges1", "");
            final String total_point = pref.getString("total_point", "");
            user_id = pref.getString("user_id", "");

            points_table.setText(total_point);

            Log.e("badges", badges1);

            List<badgesDto> badgesDtoList = new ArrayList<badgesDto>();

            JSONArray arr_badge = new JSONArray(badges1.toString());
            badges_type = new String[arr_badge.length()];
            badges = new String[arr_badge.length()];
            for (int i = 0; i < arr_badge.length(); i++) {

                JSONObject jsonQuiz = arr_badge.getJSONObject(i);

                badges_type[i] = jsonQuiz.getString("type");
                Log.e("badges", badges_type[i]);
                badges[i] = jsonQuiz.getString("badge");
                Log.e("badges", badges[i]);

                if (badges_type[i].equals("W")) {
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "WORKOUT" + "_" + badges[i]));
                } else if (badges_type[i].equals("M")) {
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "MEALS" + "_" + badges[i]));

                }
            }

            if (arr_badge.length() < 3) {
                for (int i = 0; i < 3 - arr_badge.length(); i++) {

                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "..." + "_" + "."));


                }
            }


            Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder(R.drawable.progress_animation).into(profile_img1);
            Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + cover_img).placeholder(R.drawable.progress_animation).into(cover_image);


            tv_username.setText(username);
            tv_workout.setText(workout);
            tv_meals.setText(meals);


            Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder(R.drawable.progress_animation).into(profile_img2);

            recyclerView = (RecyclerView) view.findViewById(R.id.badgesDetails);


            badgesAdapter badgesAdapter = new badgesAdapter(badgesDtoList, getContext());
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(badgesAdapter);
            recyclerView.setLayoutManager(mLayoutManager);


//            linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {
//
//                @Override
//                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                    return 800 / displayMetrics.densityDpi;
//                }
//            };


//
//            getActivity().findViewById(R.id.leftarrow).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getActivity(), "left arrow ", Toast.LENGTH_LONG);
//
//
//                    if (((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() > 0) {
//
////                        recyclerView.smoothScrollToPosition();
//                        linearSmoothScroller.setTargetPosition(((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition() - 1);
//                        mLayoutManager.startSmoothScroll(linearSmoothScroller);
//
//                    } else {
//                        // recyclerView.smoothScrollToPosition(0);
//                        linearSmoothScroller.setTargetPosition(0);
//                        mLayoutManager.startSmoothScroll(linearSmoothScroller);
//
//                    }
//
//
//                }
//            });
//
//            getActivity().findViewById(R.id.rigtarrow).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    //  recyclerView.smoothScrollToPosition(((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition() +1);
//                    linearSmoothScroller.setTargetPosition(((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition() + 1);
//
//                    mLayoutManager.startSmoothScroll(linearSmoothScroller);
//
//                }
//            });


        } catch (Exception e) {


            e.printStackTrace();
        }


        getActivity().findViewById(R.id.activtyloglayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activitylog = new Intent(getActivity(), activityLogActivty.class);
                startActivity(activitylog);
            }
        });


        getActivity().findViewById(R.id.editprofilelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activitylog = new Intent(getActivity(), editprofileActivty.class);
                startActivity(activitylog);
            }
        });


        getActivity().findViewById(R.id.measureicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activitylog = new Intent(getActivity(), Measurements.class);
                startActivity(activitylog);
            }
        });


        try {


            view.findViewById(R.id.thought_img).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activit = new Intent(getActivity(), createpostActivty.class);
                    startActivity(activit);
                }
            });


            view.findViewById(R.id.thoughtext).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent activity = new Intent(getActivity(), createpostActivty.class);
                    startActivity(activity);
                }
            });


        } catch (Exception e) {

            e.printStackTrace();
        }

        getProfile();
    }

    @Override
    public void onResume() {
        super.onResume();
        //OnResume Fragment
        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

        String user_image = pref.getString("user_image", "");
        final String cover_img = pref.getString("cover_image", "");
        final String token = pref.getString("token", "");
        final String username = pref.getString("user_name", "");
        final String workout = pref.getString("total_workout", "");
        final String meals = pref.getString("total_meals", "");

        tv_username.setText(username);
        Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).into(profile_img1);
        Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + cover_img).into(cover_image);
        Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).into(profile_img2);

    }

    public void getProfile() {

        String tag_string_req = "req_POST";

        //   String url = "https://go1shop.com/api/get_deshboard.php";
        String url_str = "https://www.aafilogicinfotech.com/demo/FC6/api/get_profile.php";


        StringRequest strReq = new StringRequest(Request.Method.POST, url_str, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = response.toString();
                //  loading.dismiss();


                Log.e("getProfile", result);


                try {


                    JSONObject jObj = new JSONObject(response);
                    String success = jObj.getString("Success");

//                    JSONArray jsonArray = jObj.getJSONArray("post");

                    if (success.equals("1")) {
                        JSONObject user = jObj.getJSONObject("post");

                        String user_id = user.getString("user_id");
                        String token = user.getString("token");
                        String device_id = user.getString("device_id");
                        String group_id = user.getString("group_id");
                        String company_name = user.getString("company_name");
                        String user_name = user.getString("user_name");
                        String user_email = user.getString("user_email");
                        String user_gender = user.getString("user_gender");
                        String user_mobile = user.getString("user_mobile");
                        String user_password = user.getString("user_password");
                        String user_image = user.getString("user_image");
                        String cover_image = user.getString("cover_image");
                        String total_workout = user.getString("total_workout");
                        String total_meals = user.getString("total_meals");
                        String total_point = user.getString("total_point");
                        //  String badges3=user.getString("badges3");

                        String user_status = user.getString("user_status");
                        String user_add_time = user.getString("user_add_time");


                        JSONArray jsonArray = user.getJSONArray("badges");

                        Log.e("badges array", jsonArray.toString());

                        badges_type = new String[jsonArray.length()];
                        badges = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonQuiz = jsonArray.getJSONObject(i);

                            badges_type[i] = jsonQuiz.getString("type");
                            Log.e("badges", badges_type[i]);
                            badges[i] = jsonQuiz.getString("badge");
                            Log.e("badges", badges[i]);
                        }


                        pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("user_id", user_id);
                        editor.putString("token", token);
                        editor.putString("device_id", device_id);
                        editor.putString("group_id", group_id);
                        editor.putString("company_name", company_name);
                        editor.putString("user_name", user_name);
                        editor.putString("user_email", user_email);
                        editor.putString("user_gender", user_gender);
                        editor.putString("user_mobile", user_mobile);
                        editor.putString("user_password", user_password);
                        editor.putString("user_image", user_image);
                        editor.putString("cover_image", cover_image);
                        editor.putString("total_workout", total_workout);
                        editor.putString("total_meals", total_meals);
                        editor.putString("badges1", jsonArray.toString());
                        editor.putString("total_point", total_point);
                        editor.putString("visible_position", "0");
                        editor.putString("badges_type", badges_type.toString());

                        //  editor.putString("badges3", badges3);
                        editor.putString("user_status", user_status);
                        editor.putString("user_add_time", user_add_time);


                        editor.commit();


                        final String cover_img = pref.getString("cover_image", "");
                        token = pref.getString("token", "");
                        final String username = pref.getString("user_name", "");
                        final String workout = pref.getString("total_workout", "");
                        final String meals = pref.getString("total_meals", "");
                        final String badges1 = pref.getString("badges1", "");
                        total_point = pref.getString("total_point", "");
                        user_id = pref.getString("user_id", "");

                        points_table.setText(total_point);

                        Log.e("badges", badges1);
//            Log.e("badges_type",badges_type);

                        List<badgesDto> badgesDtoList = new ArrayList<badgesDto>();

                        JSONArray arr_badge = new JSONArray(badges1.toString());
                        badges_type = new String[arr_badge.length()];
                        badges = new String[arr_badge.length()];
                        for (int i = 0; i < arr_badge.length(); i++) {

                            JSONObject jsonQuiz = arr_badge.getJSONObject(i);

                            badges_type[i] = jsonQuiz.getString("type");
                            Log.e("badges", badges_type[i]);
                            badges[i] = jsonQuiz.getString("badge");
                            Log.e("badges", badges[i]);

                            if (badges_type[i].equals("W")) {
                                badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "WORKOUT" + "_" + badges[i]));
                            } else if (badges_type[i].equals("M")) {
                                badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "MEALS" + "_" + badges[i]));

                            }
                        }

                        if (arr_badge.length() < 3) {
                            for (int i = 0; i < 3 - arr_badge.length(); i++) {

                                badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "..." + "_" + "."));

                            }
                        }

                        badgesAdapter badgesAdapter = new badgesAdapter(badgesDtoList, getContext());
                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(badgesAdapter);


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
    }

    public static double getScreenSizeInches(Activity activity){
        WindowManager windowManager = activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        // since SDK_INT = 1;
        int mWidthPixels = displayMetrics.widthPixels;
        int mHeightPixels = displayMetrics.heightPixels;

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 14 && Build.VERSION.SDK_INT < 17) {
            try{
                mWidthPixels = (Integer) Display.class.getMethod("getRawWidth").invoke(display);
                mHeightPixels = (Integer) Display.class.getMethod("getRawHeight").invoke(display);
            } catch (Exception ignored) {}
        }

        // includes window decorations (statusbar bar/menu bar)
        if (Build.VERSION.SDK_INT >= 17) {
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                mWidthPixels = realSize.x;
                mHeightPixels = realSize.y;
            } catch (Exception ignored) {}
        }

        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(mWidthPixels / dm.xdpi, 2);
        double y = Math.pow(mHeightPixels / dm.ydpi, 2);
        return Math.sqrt(x + y);
    }
}