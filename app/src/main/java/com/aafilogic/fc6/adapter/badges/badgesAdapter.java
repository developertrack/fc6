package com.aafilogic.fc6.adapter.badges;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.badgesDto;
import com.squareup.picasso.Picasso;

import java.util.List;

public class badgesAdapter extends RecyclerView.Adapter<badgesAdapter.MyViewHolder> {


    private List<badgesDto> badgesDetails;
    private Context context;

    public badgesAdapter(List<badgesDto> badgesDetails, Context context) {

        this.context = context;
        this.badgesDetails = badgesDetails;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.badgesdetails, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

//        Picasso.with(context).load(badgesDetails.get(i).getImage()).into(myViewHolder.badgesImages);
//        myViewHolder.badgestext.setText(badgesDetails.get(i).getName());

        String[] imageArray = badgesDetails.get(i).getName().split("_");
        for(String name : imageArray){
            Log.e("badgechange",name);
        }

        String[] badge_week = imageArray[1].split(",");

        for(String name : badge_week){
            Log.e("weekormeal",name);
//            System.out.println(name);
        }

        if(badge_week.length>1) {
            myViewHolder.layout_badge_week.setVisibility(View.VISIBLE);
            myViewHolder.layout_badge2.setVisibility(View.GONE);
            myViewHolder.layout_badge1.setVisibility(View.GONE);
            if (imageArray[0].equals("WORKOUT")) {
                Picasso.with(context).load(R.drawable.heart_dumble).placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon1);
            } else if (imageArray[0].equals("MEALS")) {
                Picasso.with(context).load(R.drawable.fork).placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon1);
            } else {
                myViewHolder.layout_badge1.setBackground(context.getDrawable(R.drawable.blue_back));
                Picasso.with(context).load("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240").placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon);
            }

            if (imageArray.length < 2) {
                myViewHolder.badge_data1.setVisibility(View.GONE);
                myViewHolder.badge_type1.setText("...");
            } else {

//                if (myString.contains("x") {
//                    // Do something.
//                }

                myViewHolder.badge_data1.setVisibility(View.VISIBLE);
                myViewHolder.badge_data1.setText(badge_week[0]);
                myViewHolder.badge_type1.setText(imageArray[0]);
            }

        }else{

            if (badge_week[0].contains("X")) {
                // Do something.
                myViewHolder.layout_badge1.setVisibility(View.GONE);
                myViewHolder.layout_badge_week.setVisibility(View.GONE);
                myViewHolder.layout_badge2.setVisibility(View.VISIBLE);
                if (imageArray[0].equals("WORKOUT")) {
                    Picasso.with(context).load(R.drawable.heart_dumble).placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon2);
                } else if (imageArray[0].equals("MEALS")) {
                    Picasso.with(context).load(R.drawable.fork).placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon2);
                } else {
                    myViewHolder.layout_badge2.setBackground(context.getDrawable(R.drawable.blue_back));
                    Picasso.with(context).load("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240").placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon);
                }

                if (imageArray.length < 2) {
                    myViewHolder.badge_data2.setVisibility(View.GONE);
                    myViewHolder.badge_type2.setText("...");
                } else {
                    myViewHolder.badge_data2.setVisibility(View.VISIBLE);
                    myViewHolder.badge_data2.setText(imageArray[1]);
                    myViewHolder.badge_type2.setText(imageArray[0]);
                }
            }else{
                myViewHolder.layout_badge1.setVisibility(View.VISIBLE);
                myViewHolder.layout_badge_week.setVisibility(View.GONE);
                myViewHolder.layout_badge2.setVisibility(View.GONE);
                if (imageArray[0].equals("WORKOUT")) {
                    Picasso.with(context).load(R.drawable.heart_dumble).placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon);
                } else if (imageArray[0].equals("MEALS")) {
                    Picasso.with(context).load(R.drawable.fork).placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon);
                } else {
                    myViewHolder.layout_badge.setBackground(context.getDrawable(R.drawable.blue_back));
                    Picasso.with(context).load("https://cdn6.aptoide.com/imgs/e/6/f/e6fbcf2c74b0e34d5092e424c9d34190_icon.png?w=240").placeholder(R.drawable.progress_animation).into(myViewHolder.badge_icon);
                }

                if (imageArray.length < 2) {
                    myViewHolder.badge_data.setVisibility(View.GONE);
                    myViewHolder.badge_type.setText("...");
                } else {
                    myViewHolder.badge_data.setVisibility(View.VISIBLE);
                    myViewHolder.badge_data.setText(imageArray[1]);
                    myViewHolder.badge_type.setText(imageArray[0]);
                }
            }


        }



    }

    @Override
    public int getItemCount() {
        return badgesDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView badge_data,badge_type ,  badge_data1,badge_type1,badge_data2,badge_type2 ;
        ImageView badge_icon,badge_icon1,badge_icon2;
        LinearLayout layout_badge,layout_badge_week,layout_badge1,layout_badge2;

        public MyViewHolder(View view) {
            super(view);
            badge_icon = view.findViewById(R.id.badge_icon);
            badge_data = view.findViewById(R.id.badge_data);
            badge_type = view.findViewById(R.id.badge_type);

            badge_icon1 = view.findViewById(R.id.badge_icon1);
            badge_data1 = view.findViewById(R.id.badge_data1);
            badge_type1 = view.findViewById(R.id.badge_type1);


            badge_icon2 = view.findViewById(R.id.badge_icon2);
            badge_data2 = view.findViewById(R.id.badge_data2);
            badge_type2 = view.findViewById(R.id.badge_type2);

            layout_badge = view.findViewById(R.id.layout_badge);
            layout_badge_week = view.findViewById(R.id.layout_badge_week);
            layout_badge1 = view.findViewById(R.id.layout_badge1);
            layout_badge2 = view.findViewById(R.id.layout_badge2);
        }
    }


}
