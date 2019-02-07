package com.aafilogic.fc6.adapter.shoppinglist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.shoppingilstName;

import java.util.List;

public class shppinglistAdapter extends RecyclerView.Adapter<shppinglistAdapter.MyViewHolder> {


    private List<shoppingilstName> shoopinglistDetails;
    private Context context;

    public shppinglistAdapter(List<shoppingilstName> shoopinglistDetails, Context context) {

        this.context = context;
        this.shoopinglistDetails = shoopinglistDetails;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.shopitem, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.shppingtext.setText(shoopinglistDetails.get(i).getListname());

    }

    @Override
    public int getItemCount() {
        return shoopinglistDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView shppingtext;


        public MyViewHolder(View view) {
            super(view);

            shppingtext = view.findViewById(R.id.listitemtext);
        }
    }


}
