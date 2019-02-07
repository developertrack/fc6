package com.aafilogic.fc6.adapter.notification;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.notificationDto;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class notificationAdapter extends RecyclerView.Adapter<notificationAdapter.MyViewHolder> {


    private List<notificationDto> notificationDetail;
    private Context context;

    public notificationAdapter(List<notificationDto> notificationDetail, Context context) {

        this.context = context;
        this.notificationDetail = notificationDetail;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notifacationdetail, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        Picasso.with(context).load(notificationDetail.get(i).getNotificationImage()).placeholder( R.drawable.progress_animation ).into(myViewHolder.notificationImage);
        myViewHolder.notificationText.setText(notificationDetail.get(i).getNotificationText());

        String notifacationdate = notificationDetail.get(i).getNotificationDate();
        if (!(notifacationdate.trim().equals(""))) {
            myViewHolder.notifcationDatelayout.setVisibility(View.VISIBLE);
            myViewHolder.notificationDate.setText(notifacationdate);
        } else {
            myViewHolder.notifcationDatelayout.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {


        return notificationDetail.size();


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView notificationText;

        private CircleImageView notificationImage;
        private TextView notificationDate;
        private RelativeLayout notifcationDatelayout;

        public MyViewHolder(View view) {
            super(view);
            notifcationDatelayout = view.findViewById(R.id.notificationdatelayut);
            notificationText = view.findViewById(R.id.notificationText);
            notificationImage = view.findViewById(R.id.notificationimage);
            notificationDate = view.findViewById(R.id.notificationDate);
        }
    }


}
