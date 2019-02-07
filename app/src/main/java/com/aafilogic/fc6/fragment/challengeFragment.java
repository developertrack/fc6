package com.aafilogic.fc6.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.Rankings;
import com.aafilogic.fc6.activity.messageboardActivty;
import com.aafilogic.fc6.activity.teamListActivty;


/**
 * Created by Belal on 1/23/2018.
 */

public class challengeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.challenge, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        getActivity().findViewById(R.id.ranking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), Rankings.class);
                startActivity(teamlistActivity);

            }
        });


        getActivity().findViewById(R.id.teamlist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), teamListActivty.class);
                startActivity(teamlistActivity);

            }
        });


        getActivity().findViewById(R.id.messageboard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamlistActivity = new Intent(getActivity(), messageboardActivty.class);
                startActivity(teamlistActivity);

            }
        });


    }


}