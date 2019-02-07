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

public class Detox_food_yes_listActivty extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detoxfoodyeslist);

        ArrayList<String> foodlist;


        List<foodexchangeListItem> foodexchangeListItemlist = new ArrayList<foodexchangeListItem>();


        foodlist = new ArrayList<>();
        foodlist.add("apples");
        foodlist.add("apricots");
        foodlist.add("bananas");
        foodlist.add("blackberries");
        foodlist.add("blueberries");
        foodlist.add("boysenberries");
        foodlist.add("cantaloupe");
        foodlist.add("cherries");
        foodlist.add("cranberries");
        foodlist.add("figs");
        foodlist.add("grapefruit");
        foodlist.add("grapes");
        foodlist.add("guava");
        foodlist.add("honeydew melon");
        foodlist.add("kiwi");
        foodlist.add("lemons");
        foodlist.add("limes ");
        foodlist.add("mangoes");
        foodlist.add("nectarines");
        foodlist.add("oranges");
        foodlist.add("papayas");
        foodlist.add("peaches ");
        foodlist.add("pears");
        foodlist.add("pineapples");
        foodlist.add("plantains ");
        foodlist.add("plums ");
        foodlist.add("prunes");
        foodlist.add("raisins");
        foodlist.add("raspberries ");
        foodlist.add("strawberries ");
        foodlist.add("tangelos");
        foodlist.add("tangerines");
        foodlist.add("watermelon");
        foodexchangeListItemlist.add(new foodexchangeListItem("6", "All Fruts title (Fresh/ Frozen/ Dried/ Canned)", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("artichokes");
        foodlist.add("asparagus");
        foodlist.add("beets");
        foodlist.add("broccoli");
        foodlist.add("Brussels sprouts");
        foodlist.add("cabbage");
        foodlist.add("carrots");
        foodlist.add("cauliflower");
        foodlist.add("celery");
        foodlist.add("chili peppers");
        foodlist.add("collard greens");
        foodlist.add("corn");
        foodlist.add("cucumbers");
        foodlist.add("eggplant");
        foodlist.add("garlic");
        foodlist.add("ginger root");
        foodlist.add("kale");
        foodlist.add("leeks");
        foodlist.add("lettuce");
        foodlist.add("mushrooms");
        foodlist.add("mustard greens");
        foodlist.add("okra");
        foodlist.add("onions");
        foodlist.add("parsley");
        foodlist.add("potatoes");
        foodlist.add("radishes");
        foodlist.add("rutabagas");
        foodlist.add("scallions");
        foodlist.add("spinach");
        foodlist.add("sprouts");
        foodlist.add("squashes");
        foodlist.add("sweet potatoes");
        foodlist.add("tomatoes");
        foodlist.add("turnips");
        foodlist.add("watercress");
        foodlist.add("yams");
        foodlist.add("zucchini");
        foodlist.add("veggie burgers");
        foodexchangeListItemlist.add(new foodexchangeListItem("7", "All Vegetables title (Fresh/ Frozen/ Dried/ Juiced/ Canned)", foodlist));
        foodlist = new ArrayList<>();


        foodlist = new ArrayList<>();
        foodlist.add("sunflower seeds");
        foodlist.add("cashews");
        foodlist.add("peanuts");
        foodlist.add("sesame");
        foodlist.add("peanut butter");
        foodexchangeListItemlist.add(new foodexchangeListItem("5", "All Nuts and Seeds title", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("Dried Beans");
        foodlist.add("Pinto Beans");
        foodlist.add("Split Peas");
        foodlist.add("Lentils");
        foodlist.add("Black eyed Peas");
        foodlist.add("Kidney beans");
        foodlist.add("Black Beans");
        foodlist.add("cannellini beans");
        foodlist.add("white Beans");
        foodexchangeListItemlist.add(new foodexchangeListItem("5", "All legumes", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("olive");
        foodlist.add("canola");
        foodlist.add("grape seed");
        foodlist.add("peanut");
        foodlist.add("sesame");
        foodexchangeListItemlist.add(new foodexchangeListItem("8", "All quality oils", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("vinegar");
        foodlist.add("seasonings");
        foodlist.add("salt");
        foodlist.add("herbs");
        foodlist.add("spices");
        foodexchangeListItemlist.add(new foodexchangeListItem("9", "Other", foodlist));


        foodlist = new ArrayList<>();
        foodlist.add("spring water");
        foodlist.add("distilled water");
        foodlist.add("pure waters");
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
