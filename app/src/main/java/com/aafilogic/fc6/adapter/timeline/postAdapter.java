package com.aafilogic.fc6.adapter.timeline;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aafilogic.fc6.R;
import com.aafilogic.fc6.dto.userpost;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class postAdapter extends RecyclerView.Adapter<postAdapter.MyViewHolder> {


    private List<userpost> userpostDetails;
    private Context context;

    public postAdapter(List<userpost> userpostDetails, Context context) {

        this.context = context;
        this.userpostDetails = userpostDetails;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.userpostdetails, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Picasso.with(context).load(userpostDetails.get(i).getPostimage()).placeholder( R.drawable.progress_animation ).into(myViewHolder.postImage);
        Picasso.with(context).load(userpostDetails.get(i).getUserimage()).placeholder( R.drawable.progress_animation ).into(myViewHolder.userImages);

        myViewHolder.posttext.setText(userpostDetails.get(i).getUserPostText());

    }

    @Override
    public int getItemCount() {
        return userpostDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView posttext;
        CircleImageView userImages;
        private ImageView postImage;

        public MyViewHolder(View view) {
            super(view);
            posttext = view.findViewById(R.id.post_user_name);
            postImage = view.findViewById(R.id.postimage);
            userImages = view.findViewById(R.id.postuserimage);
        }

    }


}
