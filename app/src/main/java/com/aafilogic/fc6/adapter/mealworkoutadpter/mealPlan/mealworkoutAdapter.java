package com.aafilogic.fc6.adapter.mealworkoutadpter.mealPlan;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.mealworkoutdot;
import com.squareup.picasso.Picasso;

import java.util.List;


public class mealworkoutAdapter extends RecyclerView.Adapter<mealworkoutAdapter.MyViewHolder> {


    private List<mealworkoutdot> mealworkoutDetail;
    private Context context;

    public mealworkoutAdapter(List<mealworkoutdot> mealworkoutDetail, Context context) {

        this.context = context;
        this.mealworkoutDetail = mealworkoutDetail;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.mealworkoutdetail, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.mealworkouttext.setText(mealworkoutDetail.get(i).getMealtext());
        Picasso.with(context).load(mealworkoutDetail.get(i).getImage()).into(myViewHolder.mealworkoutimageview);

    }

    @Override
    public int getItemCount() {

        return mealworkoutDetail.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView mealworkouttext;
        private ImageView mealworkoutimageview;


        public MyViewHolder(View view) {
            super(view);
            mealworkouttext = view.findViewById(R.id.badgesimageText);
            mealworkoutimageview = view.findViewById(R.id.badgesimage);


        }


    }


}
