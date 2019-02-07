package com.aafilogic.fc6.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.adapter.foodexchangeList.foodexchangeAdapter;
import com.aafilogic.fc6.dto.foodexchangeListItem;

import java.util.ArrayList;
import java.util.List;

public class FoodExchangeListActivty extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodexchangelist);

        ArrayList<String> foodlist;


        List<foodexchangeListItem> foodexchangeListItemlist = new ArrayList<foodexchangeListItem>();

        foodlist = new ArrayList<>();
        foodlist.add("Peanut");
        foodlist.add("Almond");
        foodlist.add("Cashew");
        foodlist.add("Hazelnut");
        foodlist.add("Sunbutter");
        foodexchangeListItemlist.add(new foodexchangeListItem("1", "Nut Butters", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("Cow’s Milk");
        foodlist.add("Goat’s Milk");
        foodlist.add("Almond");
        foodlist.add("Coconut");
        foodlist.add("Rice");
        foodlist.add("Cashew or Soy Milk");
        foodexchangeListItemlist.add(new foodexchangeListItem("2", "Milk", foodlist));
        foodlist = new ArrayList<>();


        foodlist = new ArrayList<>();
        foodlist.add("Turkey");
        foodlist.add("Ham");
        foodlist.add("Chicken");
        foodlist.add("Fish");
        foodlist.add("Beans");
        foodlist.add("Cheese");
        foodexchangeListItemlist.add(new foodexchangeListItem("3", "PRO for Salads", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("Carrots");
        foodlist.add("Zucchini");
        foodlist.add("Yellow Squash (cooked or raw)");
        foodlist.add("Turnips or Parsnips (baked) - slice into fry shaped pieces");
        foodexchangeListItemlist.add(new foodexchangeListItem("4", "Veggies Fries", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("Almonds");
        foodlist.add("Cashews");
        foodlist.add("Peanuts");
        foodlist.add("Hazelnuts");
        foodlist.add("Pistachios");
        foodlist.add("Pecans");
        foodlist.add("Walnuts");
        foodlist.add("Dried Fruits (raisins, cranberries, apricots, banana chips)");
        foodlist.add("chocolate chips");
        foodexchangeListItemlist.add(new foodexchangeListItem("5", "Trail Mix", foodlist));


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.foodexchangerecyclerview);


        foodexchangeAdapter foodexchangeAdapter = new foodexchangeAdapter(foodexchangeListItemlist, this);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(foodexchangeAdapter);


        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    public void back_act(View v) {

        finish();

    }


}
