package com.aafilogic.fc6;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Rankings extends AppCompatActivity {

    private static final String TAG_T = "Ranking Activity";


    String user_id, token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rankings);


    }


    public void ranking_team(View v) {

        Intent im = new Intent(getApplicationContext(), Ranking_Teams.class);
        startActivity(im);


    }


    public void challengers(View v) {

        Intent im = new Intent(getApplicationContext(), Challengers.class);
        startActivity(im);


    }


    public void back_act(View v) {

        finish();

    }

}
