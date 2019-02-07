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

public class Detox_food_No_listActivty extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detoxfoodnolist);

        ArrayList<String> foodlist;


        List<foodexchangeListItem> foodexchangeListItemlist = new ArrayList<foodexchangeListItem>();


        foodlist = new ArrayList<>();
        foodlist.add("beef");
        foodlist.add("lamb");
        foodlist.add("pork");
        foodlist.add("poultry");
        foodlist.add("fish");
        foodexchangeListItemlist.add(new foodexchangeListItem("4", "All meat and animal products", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("milk");
        foodlist.add("cheese");
        foodlist.add("cream");
        foodlist.add("butter");
        foodlist.add("eggs");
        foodexchangeListItemlist.add(new foodexchangeListItem("2", "All dairy products", foodlist));
        foodlist = new ArrayList<>();


        foodlist = new ArrayList<>();
        foodlist.add("sugar");
        foodlist.add("raw sugar");
        foodlist.add("honey");
        foodlist.add("syrups");
        foodlist.add("molasses");
        foodlist.add("cane juice");
        foodexchangeListItemlist.add(new foodexchangeListItem("9", "All sweeteners", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("Ezekiel Bread");
        foodlist.add("baked goods");
        foodexchangeListItemlist.add(new foodexchangeListItem("7", "All leavened and unleavened bread", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("artificial flavorings");
        foodlist.add("food additives");
        foodlist.add("chemicals");
        foodlist.add("white rice");
        foodlist.add("white flour");
        foodexchangeListItemlist.add(new foodexchangeListItem("1", "All refined and processed food products", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("potato chips");
        foodlist.add("French fries");
        foodlist.add("corn chips");
        foodexchangeListItemlist.add(new foodexchangeListItem("7", "All deep fried foods", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("shortening");
        foodlist.add("margarine");
        foodlist.add("lard");
        foodexchangeListItemlist.add(new foodexchangeListItem("5", "All solid fats", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("coffee");
        foodlist.add("tea");
        foodlist.add("herbal teas");
        foodlist.add("carbonated beverages");
        foodlist.add("energy drinks");
        foodlist.add("alcohol");
        foodexchangeListItemlist.add(new foodexchangeListItem("10", "Beverages", foodlist));


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.foodexchangerecyclerview);

        foodexchangeAdapter DetoxFoodYesListAdapter = new foodexchangeAdapter(foodexchangeListItemlist, this);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(DetoxFoodYesListAdapter);


        findViewById(R.id.layoutfirstclosssign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


}
