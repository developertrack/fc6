package com.aafilogic.fc6.adapter.foodexchangeList;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aafilogic.fc6.R;

import java.util.ArrayList;

public class foodlistAdapter extends RecyclerView.Adapter<foodlistAdapter.MyViewHolder> {


    private ArrayList<String> foodlistDetail;
    private Context context;

    public foodlistAdapter(ArrayList<String> foodlistDetail, Context context) {

        this.context = context;
        this.foodlistDetail = foodlistDetail;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.foodlisttext, viewGroup, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.foodtext.setText(foodlistDetail.get(i).toString());


//        ArrayAdapter<String> adapter = new ArrayAdapter(myViewHolder.context, android.R.logout.simple_spinner_item, foodexchangeListItemDetail.get(i).getFoddlist());
//
//        myViewHolder.fooddetail.setAdapter(adapter);


    }

    @Override
    public int getItemCount() {


        return foodlistDetail.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView foodtext;


        public MyViewHolder(View view) {
            super(view);

            foodtext = view.findViewById(R.id.listitemtext);


        }
    }


}
