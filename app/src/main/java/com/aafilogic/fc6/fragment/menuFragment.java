package com.aafilogic.fc6.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.BadgesList;
import com.aafilogic.fc6.activity.Config;
import com.aafilogic.fc6.activity.Detox_food_No_listActivty;
import com.aafilogic.fc6.activity.Detox_food_yes_listActivty;
import com.aafilogic.fc6.activity.FoodExchangeListActivty;
import com.aafilogic.fc6.activity.MealPlanActivity;
import com.aafilogic.fc6.activity.Rules;
import com.aafilogic.fc6.activity.Support;
import com.aafilogic.fc6.activity.login;
import com.aafilogic.fc6.activity.shoppingListActivty;
import com.aafilogic.fc6.adapter.badges.badgesAdapterMenu;
import com.aafilogic.fc6.dto.badgesDto;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Belal on 1/23/2018.
 */

public class menuFragment extends Fragment {

    String[] badges, badges_type;

    ImageView profile_img2;
    TextView tv_username;
    public badgesAdapterMenu badgesAdapter;
     RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.menu.home_fragment
        //if it is DashboardFragment it should have R.menu.fragment_dashboard
        //  Toast.makeText(getContext(), "profile", Toast.LENGTH_LONG).show();

        return inflater.inflate(R.layout.menu, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {


            profile_img2 = (ImageView) view.findViewById(R.id.profile_image2);


            tv_username = (TextView) view.findViewById(R.id.user_name);


            SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

            String user_image = pref.getString("user_image", "");
            final String cover_img = pref.getString("cover_image", "");
            final String token = pref.getString("token", "");


            final String username = pref.getString("user_name", "");
            final String badges1 = pref.getString("badges1", "");
            Log.e("badges",badges1);
            List<badgesDto> badgesDtoList = new ArrayList<badgesDto>();
            JSONArray arr_badge=new JSONArray(badges1.toString());
            badges_type=new String[arr_badge.length()];
            badges=new String[arr_badge.length()];
            int badge_length=0;
            if(arr_badge.length()>3){
                badge_length =3;
            }else{
                badge_length=arr_badge.length();
            }
            for (int i = 0; i < badge_length; i++) {

                JSONObject jsonQuiz = arr_badge.getJSONObject(i);

                badges_type[i] = jsonQuiz.getString("type");
                Log.e("badges",badges_type[i]);
                badges[i] = jsonQuiz.getString("badge");
                Log.e("badges",badges[i]);

                if( badges_type[i].equals("W")){
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "WORKOUT"+"_"+ badges[i]));
                }else if( badges_type[i].equals("M")){
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "MEALS"+"_"+ badges[i]));

                }
            }



            if(arr_badge.length()<3){
                for (int i = 0; i < 3-arr_badge.length(); i++) {
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "."+"_"+ "..."));

                }
            }

            Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).placeholder( R.drawable.progress_animation ).into(profile_img2);

            recyclerView = (RecyclerView)view.findViewById(R.id.badgesDetails);

            badgesAdapter = new badgesAdapterMenu(badgesDtoList, getContext());
            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(badgesAdapter);



            tv_username.setText(username);

            final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 800 / displayMetrics.densityDpi;
                }
            };
        } catch (Exception e) {


            e.printStackTrace();
        }



        getActivity().findViewById(R.id.badgestext2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), shoppingListActivty.class);
                startActivity(teamlistActivity);

            }
        });

        getActivity().findViewById(R.id.badgestext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teamlistActivity = new Intent(getActivity(), MealPlanActivity.class);
                startActivity(teamlistActivity);

            }
        });


        getActivity().findViewById(R.id.badgestext3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), FoodExchangeListActivty.class);
                startActivity(teamlistActivity);

            }
        });


        getActivity().findViewById(R.id.badgestext4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), Detox_food_yes_listActivty.class);
                startActivity(teamlistActivity);

            }
        });

        getActivity().findViewById(R.id.badgestext5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);

            }
        });




        getActivity().findViewById(R.id.badgestext6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), Detox_food_No_listActivty.class);
                startActivity(teamlistActivity);

            }
        });


        getActivity().findViewById(R.id.badgestext8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), BadgesList.class);
                startActivity(teamlistActivity);

            }
        });

        getActivity().findViewById(R.id.badgestext7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logout(v);
            }
        });

        getActivity().findViewById(R.id.badgestext11).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), Support.class);
                startActivity(teamlistActivity);
            }
        });

        getActivity().findViewById(R.id.badgestext12).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), Rules.class);
                startActivity(teamlistActivity);
            }
        });



        onResume();


    }


    @SuppressLint("ResourceType")
    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.layout.enter_from_left, R.layout.exit_to_left)

                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    public void logout(View vi) {
        vi = LayoutInflater.from(getContext()).inflate(R.layout.logout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        vi.setClipToOutline(true);


        builder.setView(vi);


        final AlertDialog alert = builder.create();

        vi.findViewById(R.id.lgout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(getActivity(), login.class);
                startActivity(logout);
                getActivity().finish();
            }
        });

        vi.findViewById(R.id.cencel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.dismiss();
            }
        });

        alert.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alert.setCancelable(false);
        alert.show();


    }


    @Override
    public void onResume(){
        super.onResume();

        try {

            SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF, 0);

            String user_image = pref.getString("user_image", "");
            final String cover_img = pref.getString("cover_image", "");
            final String token = pref.getString("token", "");


            // tv_meals.setText(meals);
            List<badgesDto> badgesDtoList = new ArrayList<badgesDto>();
            final String username = pref.getString("user_name", "");

            final String badges1 = pref.getString("badges1", "");
//            badges_type = pref.getString("badges_type", "");
            Log.e("badges",badges1);

            Picasso.with(getContext()).load("https://www.aafilogicinfotech.com/demo/FC6/api/" + user_image).into(profile_img2);

            JSONArray arr_badge=new JSONArray(badges1.toString());
            badges_type=new String[arr_badge.length()];
            badges=new String[arr_badge.length()];
            int badge_length=0;
            if(arr_badge.length()>3){
                badge_length =3;
            }else{
                badge_length=arr_badge.length();
            }
            for (int i = 0; i < badge_length; i++) {

                JSONObject jsonQuiz = arr_badge.getJSONObject(i);

                badges_type[i] = jsonQuiz.getString("type");
                Log.e("badges",badges_type[i]);
                badges[i] = jsonQuiz.getString("badge");
                Log.e("badges",badges[i]);

                if( badges_type[i].equals("W")){
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "WORKOUT"+"_"+ badges[i]));
                }else if( badges_type[i].equals("M")){
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "MEALS"+"_"+ badges[i]));

                }
//                else{
//                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "."+","+ "..."));
//
//                }

            }



            if(arr_badge.length()<3){
                for (int i = 0; i < 3-arr_badge.length(); i++) {
                    badgesDtoList.add(new badgesDto("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240", "."+"_"+ "..."));

                }
            }


            recyclerView = (RecyclerView) getActivity().findViewById(R.id.badgesDetails);

            badgesAdapter = new badgesAdapterMenu(badgesDtoList, getContext());
            final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setAdapter(badgesAdapter);
                }
            });

            tv_username.setText(username);

            final LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 800 / displayMetrics.densityDpi;
                }
            };
        } catch (Exception e) {


            e.printStackTrace();
        }
    }


}