package com.aafilogic.fc6.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aafilogic.fc6.R;


/**
 * Created by Belal on 1/23/2018.
 */

public class mealPlanDetailFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.menu.home_fragment
        //if it is DashboardFragment it should have R.menu.fragment_dashboard

        Toast.makeText(getContext(), "profile", Toast.LENGTH_LONG).show();

        return inflater.inflate(R.layout.weakdetail, container, false);


    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "mea", Toast.LENGTH_SHORT).show();
                Fragment fragment = new menuFragment();
                //replacing the fragment
                if (fragment != null) {
                    FragmentTransaction ft = ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.layout.enter_from_left, R.layout.exit_to_left);

                    ft.replace(R.id.fragment_container, fragment);
                    ft.addToBackStack("SignupFragment");
                    ft.commit();
                }

            }
        });

    }


}