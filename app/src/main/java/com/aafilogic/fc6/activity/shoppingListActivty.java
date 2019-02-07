package com.aafilogic.fc6.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.adapter.shoppinglist.shppinglistAdapter;
import com.aafilogic.fc6.dto.shoppingilstName;

import java.util.ArrayList;
import java.util.List;

public class shoppingListActivty extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppinglist);


        List<shoppingilstName> shoppingilstNamelist = new ArrayList<shoppingilstName>();
        shoppingilstNamelist.add(new shoppingilstName("Apple Butter"));
        shoppingilstNamelist.add(new shoppingilstName("Boneless,Skinless Chicken Breast"));
        shoppingilstNamelist.add(new shoppingilstName("Bow Tie Pasta"));
        shoppingilstNamelist.add(new shoppingilstName("Breakfast meat (to your preference: pork, chicken or turkey sausage; pork or turkey bacon)"));
        shoppingilstNamelist.add(new shoppingilstName("Brown Rice and/or Quinoa"));
        shoppingilstNamelist.add(new shoppingilstName("Buckwheat Noodles"));
        shoppingilstNamelist.add(new shoppingilstName("Canned Beans (to your preference: kidney, navy, pinto, black, etc.)"));
        shoppingilstNamelist.add(new shoppingilstName("Canned Tuna and/or Chicken (in water)"));
        shoppingilstNamelist.add(new shoppingilstName("Cereal (whole grain cereal, NO: artificial flavors or colors ex: Raisin Bran, Special K, Kashi, and Cheerios)"));
        shoppingilstNamelist.add(new shoppingilstName("Cheese (to your preference: cubes, string cheese, Baby Bells, block cheese cut into eatable pieces or crumbled blue, feta or gorgonzola cheese) "));
        shoppingilstNamelist.add(new shoppingilstName("Corn Chips"));
        shoppingilstNamelist.add(new shoppingilstName("Cottage Cheese"));
        shoppingilstNamelist.add(new shoppingilstName("Eggs ,egg whites or egg substitute"));
        shoppingilstNamelist.add(new shoppingilstName("English muffins/ Bagels/ Bread for Toasting (Sandwiches)"));
        shoppingilstNamelist.add(new shoppingilstName("Fish/ Seafood (to your preference: Salmon, trout, grouper, snapper shrimp- try to avoid fish that is farmed-raised when possible)"));
        shoppingilstNamelist.add(new shoppingilstName("Flank Steak"));
        shoppingilstNamelist.add(new shoppingilstName("Fruit (to your preference: avocado, apple, berries, bananas, oranges, grapes, pineapple, pomegranate, pear FRESH OR FROZEN for smoothies)"));
        shoppingilstNamelist.add(new shoppingilstName("Fruit Preserves"));
        shoppingilstNamelist.add(new shoppingilstName("Granola"));
        shoppingilstNamelist.add(new shoppingilstName("Greek Yogurt"));
        shoppingilstNamelist.add(new shoppingilstName("Grits"));
        shoppingilstNamelist.add(new shoppingilstName("Ground meat (pork, beef, turkey and/or chicken)"));
        shoppingilstNamelist.add(new shoppingilstName("Hummus (premade or chickpeas, oil, tahini and spices to make your own)"));
        shoppingilstNamelist.add(new shoppingilstName("Kale Chips"));
        shoppingilstNamelist.add(new shoppingilstName("Sweet Potato Chips"));
        shoppingilstNamelist.add(new shoppingilstName("Milk (to your preference: cow’s milk, goat’s milk, sheep’s milk, almond, coconut, rice, cashew or soymilk)"));
        shoppingilstNamelist.add(new shoppingilstName("Nuts (to your preference:  cashews, almonds, peanuts, hazelnuts, pecans, walnuts, pistachios)"));
        shoppingilstNamelist.add(new shoppingilstName("Nut butter (refer to exchange list)"));
        shoppingilstNamelist.add(new shoppingilstName("Oats"));
        shoppingilstNamelist.add(new shoppingilstName("Orange Juice or Fruit Juice of Choice (buy 100% juice)"));
        shoppingilstNamelist.add(new shoppingilstName("Pancake Mix"));
        shoppingilstNamelist.add(new shoppingilstName("Nut butter (refer to exchange list)"));
        shoppingilstNamelist.add(new shoppingilstName("Popcorn"));
        shoppingilstNamelist.add(new shoppingilstName("Pork Chops"));
        shoppingilstNamelist.add(new shoppingilstName("Protein/Granola Bars"));
        shoppingilstNamelist.add(new shoppingilstName("Skirt Steak"));
        shoppingilstNamelist.add(new shoppingilstName("Vegetables (to your preference: cucumbers, tomatoes, salad greens, asparagus, bell peppers, yellow squash, zucchini, carrots, celery, onions, garlic, spinach, string beans, broccoli, mushrooms, cabbage, spaghetti squash)"));
        shoppingilstNamelist.add(new shoppingilstName("Whole grain crackers"));
        shoppingilstNamelist.add(new shoppingilstName("Whole wheat wraps"));


        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.shoppingrecyclerview);


        shppinglistAdapter shppinglistAdapterAdapter = new shppinglistAdapter(shoppingilstNamelist, this);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(shppinglistAdapterAdapter);


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
