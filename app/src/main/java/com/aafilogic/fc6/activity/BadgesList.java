package com.aafilogic.fc6.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.adapter.mealworkoutadpter.mealPlan.mealworkoutAdapter;
import com.aafilogic.fc6.dto.mealworkoutdot;

import java.util.ArrayList;

public class BadgesList extends AppCompatActivity {

    LinearLayout custom_meal, custom_workout;

    Button btn_meal, btn_workout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.badgeslist);

        // final RecyclerView recyclerViewmeal = (RecyclerView) findViewById(R.id.badgesMealWorkout);
        //   meal(recyclerViewmeal);
        //  final RecyclerView recyclerViewworkout = (RecyclerView) findViewById(R.id.badgesMealWorkout1);
        //  workout(recyclerViewworkout);


        btn_meal = (Button) findViewById(R.id.mealbutton);
        btn_meal.setSelected(true);
        btn_workout = (Button) findViewById(R.id.workoutbutton);


        final ImageView divider1 = (ImageView) findViewById(R.id.dividerfirst);
        final ImageView divider2 = (ImageView) findViewById(R.id.dividerfirst2);


        custom_meal = (LinearLayout) findViewById(R.id.custom_meals);
        custom_workout = (LinearLayout) findViewById(R.id.custom_workout);

        custom_workout.setVisibility(View.GONE);


        findViewById(R.id.mealbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_workout.setSelected(false);
                btn_meal.setSelected(true);
                custom_workout.setVisibility(View.GONE);
                custom_meal.setVisibility(View.VISIBLE);
                divider1.setVisibility(View.VISIBLE);
                divider2.setVisibility(View.GONE);


            }
        });


        findViewById(R.id.workoutbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_workout.setSelected(true);
                btn_meal.setSelected(false);
                custom_workout.setVisibility(View.VISIBLE);
                custom_meal.setVisibility(View.GONE);
                divider1.setVisibility(View.GONE);
                divider2.setVisibility(View.VISIBLE);
            }
        });


        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    public void meal(RecyclerView recyclerView) {

        ArrayList<mealworkoutdot> mealworkoutList = new ArrayList<mealworkoutdot>();
        mealworkoutList.add(new mealworkoutdot(R.layout.custom_badge, "3 Meals in a day"));
        mealworkoutList.add(new mealworkoutdot(R.layout.custom_badge, "2 Meals  a day for 3 consectutive"));
        mealworkoutList.add(new mealworkoutdot(R.layout.custom_badge, "3 Meals a day for 5 consectutive"));
        mealworkoutList.add(new mealworkoutdot(R.drawable.week4, "4 Meals  a day 7 consectutive"));
        mealworkoutList.add(new mealworkoutdot(R.drawable.week5, "5 Meals in a day"));


        mealworkoutAdapter mealworkoutAdapter = new mealworkoutAdapter(mealworkoutList, this);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mealworkoutAdapter);

    }

    public void workout(RecyclerView recyclerView) {


        ArrayList<mealworkoutdot> mealworkoutList = new ArrayList<mealworkoutdot>();
        mealworkoutList.add(new mealworkoutdot(R.drawable.week1, "3 workout in a day"));
        mealworkoutList.add(new mealworkoutdot(R.drawable.week2, "2 workout  a day for 3 consectutive"));
        mealworkoutList.add(new mealworkoutdot(R.drawable.week3, "3 workout a day for 5 consectutive"));
        mealworkoutList.add(new mealworkoutdot(R.drawable.week4, "4 workout  a day 7 consectutive"));
        mealworkoutList.add(new mealworkoutdot(R.drawable.week5, "5 workout in a day"));

        mealworkoutAdapter mealworkoutAdapter = new mealworkoutAdapter(mealworkoutList, this);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mealworkoutAdapter);
        recyclerView.setVisibility(View.GONE);

    }
}
