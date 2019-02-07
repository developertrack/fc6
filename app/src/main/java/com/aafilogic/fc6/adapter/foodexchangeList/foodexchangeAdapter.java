package com.aafilogic.fc6.adapter.foodexchangeList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.foodexchangeListItem;

import java.util.List;

public class foodexchangeAdapter extends RecyclerView.Adapter<foodexchangeAdapter.MyViewHolder> {


    private List<foodexchangeListItem> foodexchangeListItemDetail;
    private Context context;

    public foodexchangeAdapter(List<foodexchangeListItem> foodexchangeListItemDetail, Context context) {

        this.context = context;
        this.foodexchangeListItemDetail = foodexchangeListItemDetail;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.foodexitemdetail, viewGroup, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {


        myViewHolder.foodtext.setText(foodexchangeListItemDetail.get(i).getFood());

        if (foodexchangeListItemDetail.get(i).getImg_num().equals("1")) {

            myViewHolder.food_icon.setImageResource(R.drawable.nut_butters);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("2")) {

            myViewHolder.food_icon.setImageResource(R.drawable.milk);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("3")) {

            myViewHolder.food_icon.setImageResource(R.drawable.pro_for_salads);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("4")) {

            myViewHolder.food_icon.setImageResource(R.drawable.veggies_fries);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("5")) {

            myViewHolder.food_icon.setImageResource(R.drawable.trail_mix);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("6")) {

            myViewHolder.food_icon.setImageResource(R.drawable.all_fruits);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("7")) {

            myViewHolder.food_icon.setImageResource(R.drawable.salad_dressing);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("8")) {

            myViewHolder.food_icon.setImageResource(R.drawable.all_quality_oils);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("9")) {

            myViewHolder.food_icon.setImageResource(R.drawable.smoothies);


        } else if (foodexchangeListItemDetail.get(i).getImg_num().equals("10")) {

            myViewHolder.food_icon.setImageResource(R.drawable.beverages);


        }


        View.OnTouchListener Spinner_OnTouch = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {

                    foodexchangeListItemDetail.get(i);
                    final RecyclerView recyclerView = myViewHolder.foodetailrec;
                    LayoutAnimationController controller =
                            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    if (recyclerView.getVisibility() != View.VISIBLE) {

                        recyclerView.setVisibility(View.VISIBLE);

                        foodlistAdapter foodexchangeAdapter = new foodlistAdapter(foodexchangeListItemDetail.get(i).getFoddlist(), myViewHolder.context);
                        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(myViewHolder.context, LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(foodexchangeAdapter);
                        recyclerView.setLayoutAnimation(controller);


                    } else {
                        recyclerView.setVisibility(View.GONE);
                    }
                }

                return true;
            }
        };

        myViewHolder.fooddetail.setOnTouchListener(Spinner_OnTouch);
    }

    @Override
    public int getItemCount() {


        return foodexchangeListItemDetail.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView foodtext;
        private ImageView food_icon;

        private Spinner fooddetail;
        private Context context;
        private RecyclerView foodetailrec;

        public MyViewHolder(View view) {
            super(view);
            fooddetail = view.findViewById(R.id.spinner);
            foodtext = view.findViewById(R.id.listitemtext);
            food_icon = view.findViewById(R.id.food_icon);
            context = view.getContext();
            foodetailrec = view.findViewById(R.id.foodexlistecyclerview);


        }
    }


}
