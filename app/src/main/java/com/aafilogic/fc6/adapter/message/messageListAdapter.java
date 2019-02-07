package com.aafilogic.fc6.adapter.message;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.messageboardDto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class messageListAdapter extends RecyclerView.Adapter<messageListAdapter.MyViewHolder> {


    private ArrayList<messageboardDto> messagelist;
    private Context context;

    public messageListAdapter(ArrayList<messageboardDto> messagelist, Context context) {

        this.context = context;
        this.messagelist = messagelist;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.messagelist, viewGroup, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        Picasso.with(context).load(messagelist.get(i).getMessageimage()).placeholder( R.drawable.progress_animation ).into(myViewHolder.messageimage);
        myViewHolder.messagename.setText(messagelist.get(i).getMessagename());
        myViewHolder.messagedetail.setText(messagelist.get(i).getMessagetext());

    }

    @Override
    public int getItemCount() {

        return messagelist.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView messageimage;
        private TextView messagename;
        private TextView messagedetail;

        public MyViewHolder(View view) {
            super(view);
            messageimage = view.findViewById(R.id.notificationimage);
            messagename = view.findViewById(R.id.messagename);
            messagedetail = view.findViewById(R.id.messsagedetail);


        }


    }


}
