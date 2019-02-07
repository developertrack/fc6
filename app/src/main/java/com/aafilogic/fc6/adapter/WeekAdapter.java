package com.aafilogic.fc6.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aafilogic.fc6.MealPlanDetailActivity;
import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.MealWeekDetail;

import java.util.List;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.MyViewHolder> {


    private List<MealWeekDetail> mealWeekDetail;
    private Context context;
    int pos=0;
    WeekAdapter.MyViewHolder vie;
    private int firstVisible = 0;

    public WeekAdapter(Context context, List<MealWeekDetail> badgesDetails, int pos) {

        this.context = context;
        this.mealWeekDetail = badgesDetails;
        this.firstVisible=pos;

    }

    @NonNull
    @Override
    public WeekAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.weekplandetail, viewGroup, false);

        return new WeekAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekAdapter.MyViewHolder myViewHolder, final int i) {

        MealWeekDetail data=mealWeekDetail.get(i);

        vie=myViewHolder;

        myViewHolder.week1.setTag(mealWeekDetail.get(i));
        myViewHolder.week1.setBackgroundResource(R.color.week_color);
        myViewHolder.day_name1.setText(data.getDay_name());
        myViewHolder.day_date1.setText(data.getDay_date());



        myViewHolder.week1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("position",String.valueOf(i));

                firstVisible=i;
                changeItem(i);
                ((MealPlanDetailActivity)context).UpdateData(i);
            }
        });

        if (i == firstVisible) {
            myViewHolder.week1.setBackgroundResource(R.color.colorPrimaryDark);
        }

    }


    public void changeItem(int position){
        firstVisible = position;
        notifyItemChanged(firstVisible);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mealWeekDetail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout week1;
        TextView day_name1;
        TextView day_date1;

        public MyViewHolder(View view) {
            super(view);
            week1 = view.findViewById(R.id.week_back);
            day_name1 = view.findViewById(R.id.day_name1);
            day_date1 = view.findViewById(R.id.day_date1);
        }
    }




}
