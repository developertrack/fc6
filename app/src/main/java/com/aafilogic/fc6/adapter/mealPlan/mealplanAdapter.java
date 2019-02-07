package com.aafilogic.fc6.adapter.mealPlan;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.MealPlanDetailActivity;
import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.mealplan;
import com.squareup.picasso.Picasso;

import java.util.List;


public class mealplanAdapter extends RecyclerView.Adapter<mealplanAdapter.MyViewHolder> {


    private List<mealplan> mealplansDetail;
    private Context context;
    int week_count;

    public mealplanAdapter(List<mealplan> mealplansDetail, Context context) {

        this.context = context;
        this.mealplansDetail = mealplansDetail;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mealplandetail, viewGroup, false);
//        itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }
//        });

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.weektext.setText(mealplansDetail.get(i).getWeakNumber());
        Picasso.with(context).load(mealplansDetail.get(i).getImage()).into(myViewHolder.imageview);
        week_count=i;
        myViewHolder.weektext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(context, MealPlanDetailActivity.class);
                logout.putExtra("Week",mealplansDetail.get(i).getWeakNumber());
                logout.putExtra("position",String.valueOf(i));
//                logout.putExtra("Week",mealplansDetail.get(i).getMeal_week());
                logout.putExtra("meal_plan_id",mealplansDetail.get(i).getMeal_plan_id());
                context.startActivity(logout);
            }
        });

    }

    @Override
    public int getItemCount() {

        return mealplansDetail.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView weektext;
        private ImageView imageview;


        public MyViewHolder(View view) {
            super(view);
            weektext = view.findViewById(R.id.weekday);
            imageview = view.findViewById(R.id.mealplanimage);

        }


    }


}
