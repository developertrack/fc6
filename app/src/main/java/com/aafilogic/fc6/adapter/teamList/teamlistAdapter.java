package com.aafilogic.fc6.adapter.teamList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.activity.teamdetailActivty;
import com.aafilogic.fc6.dto.teamlist;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.List;


public class teamlistAdapter extends RecyclerView.Adapter<teamlistAdapter.MyViewHolder> {


    private List<teamlist> teamDetail;
    private Context context;

    public teamlistAdapter(List<teamlist> teamDetail, Context context) {

        this.context = context;
        this.teamDetail = teamDetail;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.teamlistdetail, viewGroup, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.teamnametext.setText(teamDetail.get(i).getTeamname());
        Picasso.with(context).load(teamDetail.get(i).getTeamimage()).placeholder( R.drawable.progress_animation ).into(myViewHolder.teamimageimageview);

        myViewHolder.teamlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent teamdedetail = new Intent(myViewHolder.context, teamdetailActivty.class);

                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST", (Serializable) teamDetail.get(i).getTeamdetails());
                args.putString("teamname", teamDetail.get(i).getTeamname());
                teamdedetail.putExtra("BUNDLE", args);
                myViewHolder.context.startActivity(teamdedetail);


            }
        });


    }

    @Override
    public int getItemCount() {

        return teamDetail.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView teamnametext;
        private ImageView teamimageimageview;
        private RelativeLayout teamlist;
        private Context context;


        public MyViewHolder(View view) {
            super(view);
            teamnametext = view.findViewById(R.id.teamname);
            teamimageimageview = view.findViewById(R.id.teamlistimage);
            teamlist = view.findViewById(R.id.teamlist);
            context = view.getContext();

        }


    }


}
